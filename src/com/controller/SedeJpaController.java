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
import com.entities.Escuela;
import com.entities.Sede;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Nataly
 */
public class SedeJpaController implements Serializable {

    public SedeJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Sede sede) {
        if (sede.getEscuelaList() == null) {
            sede.setEscuelaList(new ArrayList<Escuela>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Escuela> attachedEscuelaList = new ArrayList<Escuela>();
            for (Escuela escuelaListEscuelaToAttach : sede.getEscuelaList()) {
                escuelaListEscuelaToAttach = em.getReference(escuelaListEscuelaToAttach.getClass(), escuelaListEscuelaToAttach.getEsId());
                attachedEscuelaList.add(escuelaListEscuelaToAttach);
            }
            sede.setEscuelaList(attachedEscuelaList);
            em.persist(sede);
            for (Escuela escuelaListEscuela : sede.getEscuelaList()) {
                Sede oldFkEscSedOfEscuelaListEscuela = escuelaListEscuela.getFkEscSed();
                escuelaListEscuela.setFkEscSed(sede);
                escuelaListEscuela = em.merge(escuelaListEscuela);
                if (oldFkEscSedOfEscuelaListEscuela != null) {
                    oldFkEscSedOfEscuelaListEscuela.getEscuelaList().remove(escuelaListEscuela);
                    oldFkEscSedOfEscuelaListEscuela = em.merge(oldFkEscSedOfEscuelaListEscuela);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Sede sede) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Sede persistentSede = em.find(Sede.class, sede.getSeId());
            List<Escuela> escuelaListOld = persistentSede.getEscuelaList();
            List<Escuela> escuelaListNew = sede.getEscuelaList();
            List<Escuela> attachedEscuelaListNew = new ArrayList<Escuela>();
            for (Escuela escuelaListNewEscuelaToAttach : escuelaListNew) {
                escuelaListNewEscuelaToAttach = em.getReference(escuelaListNewEscuelaToAttach.getClass(), escuelaListNewEscuelaToAttach.getEsId());
                attachedEscuelaListNew.add(escuelaListNewEscuelaToAttach);
            }
            escuelaListNew = attachedEscuelaListNew;
            sede.setEscuelaList(escuelaListNew);
            sede = em.merge(sede);
            for (Escuela escuelaListOldEscuela : escuelaListOld) {
                if (!escuelaListNew.contains(escuelaListOldEscuela)) {
                    escuelaListOldEscuela.setFkEscSed(null);
                    escuelaListOldEscuela = em.merge(escuelaListOldEscuela);
                }
            }
            for (Escuela escuelaListNewEscuela : escuelaListNew) {
                if (!escuelaListOld.contains(escuelaListNewEscuela)) {
                    Sede oldFkEscSedOfEscuelaListNewEscuela = escuelaListNewEscuela.getFkEscSed();
                    escuelaListNewEscuela.setFkEscSed(sede);
                    escuelaListNewEscuela = em.merge(escuelaListNewEscuela);
                    if (oldFkEscSedOfEscuelaListNewEscuela != null && !oldFkEscSedOfEscuelaListNewEscuela.equals(sede)) {
                        oldFkEscSedOfEscuelaListNewEscuela.getEscuelaList().remove(escuelaListNewEscuela);
                        oldFkEscSedOfEscuelaListNewEscuela = em.merge(oldFkEscSedOfEscuelaListNewEscuela);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = sede.getSeId();
                if (findSede(id) == null) {
                    throw new NonexistentEntityException("The sede with id " + id + " no longer exists.");
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
            Sede sede;
            try {
                sede = em.getReference(Sede.class, id);
                sede.getSeId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The sede with id " + id + " no longer exists.", enfe);
            }
            List<Escuela> escuelaList = sede.getEscuelaList();
            for (Escuela escuelaListEscuela : escuelaList) {
                escuelaListEscuela.setFkEscSed(null);
                escuelaListEscuela = em.merge(escuelaListEscuela);
            }
            em.remove(sede);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Sede> findSedeEntities() {
        return findSedeEntities(true, -1, -1);
    }

    public List<Sede> findSedeEntities(int maxResults, int firstResult) {
        return findSedeEntities(false, maxResults, firstResult);
    }

    private List<Sede> findSedeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Sede.class));
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

    public Sede findSede(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Sede.class, id);
        } finally {
            em.close();
        }
    }

    public int getSedeCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Sede> rt = cq.from(Sede.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
