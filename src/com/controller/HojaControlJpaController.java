/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller;

import com.controller.exceptions.NonexistentEntityException;
import com.entities.HojaControl;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.entities.Solicitud;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Nataly
 */
public class HojaControlJpaController implements Serializable {

    public HojaControlJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(HojaControl hojaControl) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Solicitud fkHcoSol = hojaControl.getFkHcoSol();
            if (fkHcoSol != null) {
                fkHcoSol = em.getReference(fkHcoSol.getClass(), fkHcoSol.getSoId());
                hojaControl.setFkHcoSol(fkHcoSol);
            }
            em.persist(hojaControl);
            if (fkHcoSol != null) {
                fkHcoSol.getHojaControlList().add(hojaControl);
                fkHcoSol = em.merge(fkHcoSol);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(HojaControl hojaControl) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            HojaControl persistentHojaControl = em.find(HojaControl.class, hojaControl.getHcId());
            Solicitud fkHcoSolOld = persistentHojaControl.getFkHcoSol();
            Solicitud fkHcoSolNew = hojaControl.getFkHcoSol();
            if (fkHcoSolNew != null) {
                fkHcoSolNew = em.getReference(fkHcoSolNew.getClass(), fkHcoSolNew.getSoId());
                hojaControl.setFkHcoSol(fkHcoSolNew);
            }
            hojaControl = em.merge(hojaControl);
            if (fkHcoSolOld != null && !fkHcoSolOld.equals(fkHcoSolNew)) {
                fkHcoSolOld.getHojaControlList().remove(hojaControl);
                fkHcoSolOld = em.merge(fkHcoSolOld);
            }
            if (fkHcoSolNew != null && !fkHcoSolNew.equals(fkHcoSolOld)) {
                fkHcoSolNew.getHojaControlList().add(hojaControl);
                fkHcoSolNew = em.merge(fkHcoSolNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = hojaControl.getHcId();
                if (findHojaControl(id) == null) {
                    throw new NonexistentEntityException("The hojaControl with id " + id + " no longer exists.");
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
            HojaControl hojaControl;
            try {
                hojaControl = em.getReference(HojaControl.class, id);
                hojaControl.getHcId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The hojaControl with id " + id + " no longer exists.", enfe);
            }
            Solicitud fkHcoSol = hojaControl.getFkHcoSol();
            if (fkHcoSol != null) {
                fkHcoSol.getHojaControlList().remove(hojaControl);
                fkHcoSol = em.merge(fkHcoSol);
            }
            em.remove(hojaControl);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<HojaControl> findHojaControlEntities() {
        return findHojaControlEntities(true, -1, -1);
    }

    public List<HojaControl> findHojaControlEntities(int maxResults, int firstResult) {
        return findHojaControlEntities(false, maxResults, firstResult);
    }

    private List<HojaControl> findHojaControlEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(HojaControl.class));
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

    public HojaControl findHojaControl(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(HojaControl.class, id);
        } finally {
            em.close();
        }
    }

    public int getHojaControlCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<HojaControl> rt = cq.from(HojaControl.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
