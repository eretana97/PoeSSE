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
import com.entities.Empleado;
import com.entities.Dia;
import com.entities.HorarioAtencion;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Nataly
 */
public class HorarioAtencionJpaController implements Serializable {

    public HorarioAtencionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(HorarioAtencion horarioAtencion) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Empleado fkHatEm = horarioAtencion.getFkHatEm();
            if (fkHatEm != null) {
                fkHatEm = em.getReference(fkHatEm.getClass(), fkHatEm.getEmId());
                horarioAtencion.setFkHatEm(fkHatEm);
            }
            Dia fkHatDia = horarioAtencion.getFkHatDia();
            if (fkHatDia != null) {
                fkHatDia = em.getReference(fkHatDia.getClass(), fkHatDia.getDiId());
                horarioAtencion.setFkHatDia(fkHatDia);
            }
            em.persist(horarioAtencion);
            if (fkHatEm != null) {
                fkHatEm.getHorarioAtencionList().add(horarioAtencion);
                fkHatEm = em.merge(fkHatEm);
            }
            if (fkHatDia != null) {
                fkHatDia.getHorarioAtencionList().add(horarioAtencion);
                fkHatDia = em.merge(fkHatDia);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(HorarioAtencion horarioAtencion) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            HorarioAtencion persistentHorarioAtencion = em.find(HorarioAtencion.class, horarioAtencion.getHaId());
            Empleado fkHatEmOld = persistentHorarioAtencion.getFkHatEm();
            Empleado fkHatEmNew = horarioAtencion.getFkHatEm();
            Dia fkHatDiaOld = persistentHorarioAtencion.getFkHatDia();
            Dia fkHatDiaNew = horarioAtencion.getFkHatDia();
            if (fkHatEmNew != null) {
                fkHatEmNew = em.getReference(fkHatEmNew.getClass(), fkHatEmNew.getEmId());
                horarioAtencion.setFkHatEm(fkHatEmNew);
            }
            if (fkHatDiaNew != null) {
                fkHatDiaNew = em.getReference(fkHatDiaNew.getClass(), fkHatDiaNew.getDiId());
                horarioAtencion.setFkHatDia(fkHatDiaNew);
            }
            horarioAtencion = em.merge(horarioAtencion);
            if (fkHatEmOld != null && !fkHatEmOld.equals(fkHatEmNew)) {
                fkHatEmOld.getHorarioAtencionList().remove(horarioAtencion);
                fkHatEmOld = em.merge(fkHatEmOld);
            }
            if (fkHatEmNew != null && !fkHatEmNew.equals(fkHatEmOld)) {
                fkHatEmNew.getHorarioAtencionList().add(horarioAtencion);
                fkHatEmNew = em.merge(fkHatEmNew);
            }
            if (fkHatDiaOld != null && !fkHatDiaOld.equals(fkHatDiaNew)) {
                fkHatDiaOld.getHorarioAtencionList().remove(horarioAtencion);
                fkHatDiaOld = em.merge(fkHatDiaOld);
            }
            if (fkHatDiaNew != null && !fkHatDiaNew.equals(fkHatDiaOld)) {
                fkHatDiaNew.getHorarioAtencionList().add(horarioAtencion);
                fkHatDiaNew = em.merge(fkHatDiaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = horarioAtencion.getHaId();
                if (findHorarioAtencion(id) == null) {
                    throw new NonexistentEntityException("The horarioAtencion with id " + id + " no longer exists.");
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
            HorarioAtencion horarioAtencion;
            try {
                horarioAtencion = em.getReference(HorarioAtencion.class, id);
                horarioAtencion.getHaId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The horarioAtencion with id " + id + " no longer exists.", enfe);
            }
            Empleado fkHatEm = horarioAtencion.getFkHatEm();
            if (fkHatEm != null) {
                fkHatEm.getHorarioAtencionList().remove(horarioAtencion);
                fkHatEm = em.merge(fkHatEm);
            }
            Dia fkHatDia = horarioAtencion.getFkHatDia();
            if (fkHatDia != null) {
                fkHatDia.getHorarioAtencionList().remove(horarioAtencion);
                fkHatDia = em.merge(fkHatDia);
            }
            em.remove(horarioAtencion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<HorarioAtencion> findHorarioAtencionEntities() {
        return findHorarioAtencionEntities(true, -1, -1);
    }

    public List<HorarioAtencion> findHorarioAtencionEntities(int maxResults, int firstResult) {
        return findHorarioAtencionEntities(false, maxResults, firstResult);
    }

    private List<HorarioAtencion> findHorarioAtencionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(HorarioAtencion.class));
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

    public HorarioAtencion findHorarioAtencion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(HorarioAtencion.class, id);
        } finally {
            em.close();
        }
    }

    public int getHorarioAtencionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<HorarioAtencion> rt = cq.from(HorarioAtencion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
