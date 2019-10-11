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
import com.entities.Materia;
import com.entities.Nota;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Nataly
 */
public class NotaJpaController implements Serializable {

    public NotaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Nota nota) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Materia fkNotMat = nota.getFkNotMat();
            if (fkNotMat != null) {
                fkNotMat = em.getReference(fkNotMat.getClass(), fkNotMat.getMaId());
                nota.setFkNotMat(fkNotMat);
            }
            em.persist(nota);
            if (fkNotMat != null) {
                fkNotMat.getNotaList().add(nota);
                fkNotMat = em.merge(fkNotMat);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Nota nota) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Nota persistentNota = em.find(Nota.class, nota.getNoId());
            Materia fkNotMatOld = persistentNota.getFkNotMat();
            Materia fkNotMatNew = nota.getFkNotMat();
            if (fkNotMatNew != null) {
                fkNotMatNew = em.getReference(fkNotMatNew.getClass(), fkNotMatNew.getMaId());
                nota.setFkNotMat(fkNotMatNew);
            }
            nota = em.merge(nota);
            if (fkNotMatOld != null && !fkNotMatOld.equals(fkNotMatNew)) {
                fkNotMatOld.getNotaList().remove(nota);
                fkNotMatOld = em.merge(fkNotMatOld);
            }
            if (fkNotMatNew != null && !fkNotMatNew.equals(fkNotMatOld)) {
                fkNotMatNew.getNotaList().add(nota);
                fkNotMatNew = em.merge(fkNotMatNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = nota.getNoId();
                if (findNota(id) == null) {
                    throw new NonexistentEntityException("The nota with id " + id + " no longer exists.");
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
            Nota nota;
            try {
                nota = em.getReference(Nota.class, id);
                nota.getNoId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The nota with id " + id + " no longer exists.", enfe);
            }
            Materia fkNotMat = nota.getFkNotMat();
            if (fkNotMat != null) {
                fkNotMat.getNotaList().remove(nota);
                fkNotMat = em.merge(fkNotMat);
            }
            em.remove(nota);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Nota> findNotaEntities() {
        return findNotaEntities(true, -1, -1);
    }

    public List<Nota> findNotaEntities(int maxResults, int firstResult) {
        return findNotaEntities(false, maxResults, firstResult);
    }

    private List<Nota> findNotaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Nota.class));
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

    public Nota findNota(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Nota.class, id);
        } finally {
            em.close();
        }
    }

    public int getNotaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Nota> rt = cq.from(Nota.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
