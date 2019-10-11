/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller;

import com.controller.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.entities.Solicitud;
import com.entities.Dia;
import com.entities.HorariosSse;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Nataly
 */
public class HorariosSseJpaController implements Serializable {

    public HorariosSseJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(HorariosSse horariosSse) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Solicitud fkHssSol = horariosSse.getFkHssSol();
            if (fkHssSol != null) {
                fkHssSol = em.getReference(fkHssSol.getClass(), fkHssSol.getSoId());
                horariosSse.setFkHssSol(fkHssSol);
            }
            Dia fkHssDia = horariosSse.getFkHssDia();
            if (fkHssDia != null) {
                fkHssDia = em.getReference(fkHssDia.getClass(), fkHssDia.getDiId());
                horariosSse.setFkHssDia(fkHssDia);
            }
            em.persist(horariosSse);
            if (fkHssSol != null) {
                fkHssSol.getHorariosSseList().add(horariosSse);
                fkHssSol = em.merge(fkHssSol);
            }
            if (fkHssDia != null) {
                fkHssDia.getHorariosSseList().add(horariosSse);
                fkHssDia = em.merge(fkHssDia);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(HorariosSse horariosSse) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            HorariosSse persistentHorariosSse = em.find(HorariosSse.class, horariosSse.getHsId());
            Solicitud fkHssSolOld = persistentHorariosSse.getFkHssSol();
            Solicitud fkHssSolNew = horariosSse.getFkHssSol();
            Dia fkHssDiaOld = persistentHorariosSse.getFkHssDia();
            Dia fkHssDiaNew = horariosSse.getFkHssDia();
            if (fkHssSolNew != null) {
                fkHssSolNew = em.getReference(fkHssSolNew.getClass(), fkHssSolNew.getSoId());
                horariosSse.setFkHssSol(fkHssSolNew);
            }
            if (fkHssDiaNew != null) {
                fkHssDiaNew = em.getReference(fkHssDiaNew.getClass(), fkHssDiaNew.getDiId());
                horariosSse.setFkHssDia(fkHssDiaNew);
            }
            horariosSse = em.merge(horariosSse);
            if (fkHssSolOld != null && !fkHssSolOld.equals(fkHssSolNew)) {
                fkHssSolOld.getHorariosSseList().remove(horariosSse);
                fkHssSolOld = em.merge(fkHssSolOld);
            }
            if (fkHssSolNew != null && !fkHssSolNew.equals(fkHssSolOld)) {
                fkHssSolNew.getHorariosSseList().add(horariosSse);
                fkHssSolNew = em.merge(fkHssSolNew);
            }
            if (fkHssDiaOld != null && !fkHssDiaOld.equals(fkHssDiaNew)) {
                fkHssDiaOld.getHorariosSseList().remove(horariosSse);
                fkHssDiaOld = em.merge(fkHssDiaOld);
            }
            if (fkHssDiaNew != null && !fkHssDiaNew.equals(fkHssDiaOld)) {
                fkHssDiaNew.getHorariosSseList().add(horariosSse);
                fkHssDiaNew = em.merge(fkHssDiaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = horariosSse.getHsId();
                if (findHorariosSse(id) == null) {
                    throw new NonexistentEntityException("The horariosSse with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            HorariosSse horariosSse;
            try {
                horariosSse = em.getReference(HorariosSse.class, id);
                horariosSse.getHsId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The horariosSse with id " + id + " no longer exists.", enfe);
            }
            Solicitud fkHssSol = horariosSse.getFkHssSol();
            if (fkHssSol != null) {
                fkHssSol.getHorariosSseList().remove(horariosSse);
                fkHssSol = em.merge(fkHssSol);
            }
            Dia fkHssDia = horariosSse.getFkHssDia();
            if (fkHssDia != null) {
                fkHssDia.getHorariosSseList().remove(horariosSse);
                fkHssDia = em.merge(fkHssDia);
            }
            em.remove(horariosSse);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<HorariosSse> findHorariosSseEntities() {
        return findHorariosSseEntities(true, -1, -1);
    }

    public List<HorariosSse> findHorariosSseEntities(int maxResults, int firstResult) {
        return findHorariosSseEntities(false, maxResults, firstResult);
    }

    private List<HorariosSse> findHorariosSseEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(HorariosSse.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public HorariosSse findHorariosSse(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(HorariosSse.class, id);
        } finally {
            em.close();
        }
    }

    public int getHorariosSseCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<HorariosSse> rt = cq.from(HorariosSse.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
