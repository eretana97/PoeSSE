/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller;

import com.controller.exceptions.NonexistentEntityException;
import com.entities.FormularioFinalizacion;
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
public class FormularioFinalizacionJpaController implements Serializable {

    public FormularioFinalizacionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(FormularioFinalizacion formularioFinalizacion) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Solicitud fkFfiSol = formularioFinalizacion.getFkFfiSol();
            if (fkFfiSol != null) {
                fkFfiSol = em.getReference(fkFfiSol.getClass(), fkFfiSol.getSoId());
                formularioFinalizacion.setFkFfiSol(fkFfiSol);
            }
            em.persist(formularioFinalizacion);
            if (fkFfiSol != null) {
                fkFfiSol.getFormularioFinalizacionList().add(formularioFinalizacion);
                fkFfiSol = em.merge(fkFfiSol);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(FormularioFinalizacion formularioFinalizacion) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            FormularioFinalizacion persistentFormularioFinalizacion = em.find(FormularioFinalizacion.class, formularioFinalizacion.getFfId());
            Solicitud fkFfiSolOld = persistentFormularioFinalizacion.getFkFfiSol();
            Solicitud fkFfiSolNew = formularioFinalizacion.getFkFfiSol();
            if (fkFfiSolNew != null) {
                fkFfiSolNew = em.getReference(fkFfiSolNew.getClass(), fkFfiSolNew.getSoId());
                formularioFinalizacion.setFkFfiSol(fkFfiSolNew);
            }
            formularioFinalizacion = em.merge(formularioFinalizacion);
            if (fkFfiSolOld != null && !fkFfiSolOld.equals(fkFfiSolNew)) {
                fkFfiSolOld.getFormularioFinalizacionList().remove(formularioFinalizacion);
                fkFfiSolOld = em.merge(fkFfiSolOld);
            }
            if (fkFfiSolNew != null && !fkFfiSolNew.equals(fkFfiSolOld)) {
                fkFfiSolNew.getFormularioFinalizacionList().add(formularioFinalizacion);
                fkFfiSolNew = em.merge(fkFfiSolNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = formularioFinalizacion.getFfId();
                if (findFormularioFinalizacion(id) == null) {
                    throw new NonexistentEntityException("The formularioFinalizacion with id " + id + " no longer exists.");
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
            FormularioFinalizacion formularioFinalizacion;
            try {
                formularioFinalizacion = em.getReference(FormularioFinalizacion.class, id);
                formularioFinalizacion.getFfId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The formularioFinalizacion with id " + id + " no longer exists.", enfe);
            }
            Solicitud fkFfiSol = formularioFinalizacion.getFkFfiSol();
            if (fkFfiSol != null) {
                fkFfiSol.getFormularioFinalizacionList().remove(formularioFinalizacion);
                fkFfiSol = em.merge(fkFfiSol);
            }
            em.remove(formularioFinalizacion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<FormularioFinalizacion> findFormularioFinalizacionEntities() {
        return findFormularioFinalizacionEntities(true, -1, -1);
    }

    public List<FormularioFinalizacion> findFormularioFinalizacionEntities(int maxResults, int firstResult) {
        return findFormularioFinalizacionEntities(false, maxResults, firstResult);
    }

    private List<FormularioFinalizacion> findFormularioFinalizacionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(FormularioFinalizacion.class));
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

    public FormularioFinalizacion findFormularioFinalizacion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(FormularioFinalizacion.class, id);
        } finally {
            em.close();
        }
    }

    public int getFormularioFinalizacionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<FormularioFinalizacion> rt = cq.from(FormularioFinalizacion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
