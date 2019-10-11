/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller;

import com.controller.exceptions.IllegalOrphanException;
import com.controller.exceptions.NonexistentEntityException;
import com.entities.Ciclo;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.entities.InscripcionCiclo;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Nataly
 */
public class CicloJpaController implements Serializable {

    public CicloJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Ciclo ciclo) {
        if (ciclo.getInscripcionCicloList() == null) {
            ciclo.setInscripcionCicloList(new ArrayList<InscripcionCiclo>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<InscripcionCiclo> attachedInscripcionCicloList = new ArrayList<InscripcionCiclo>();
            for (InscripcionCiclo inscripcionCicloListInscripcionCicloToAttach : ciclo.getInscripcionCicloList()) {
                inscripcionCicloListInscripcionCicloToAttach = em.getReference(inscripcionCicloListInscripcionCicloToAttach.getClass(), inscripcionCicloListInscripcionCicloToAttach.getIcId());
                attachedInscripcionCicloList.add(inscripcionCicloListInscripcionCicloToAttach);
            }
            ciclo.setInscripcionCicloList(attachedInscripcionCicloList);
            em.persist(ciclo);
            for (InscripcionCiclo inscripcionCicloListInscripcionCiclo : ciclo.getInscripcionCicloList()) {
                Ciclo oldFkIncCicOfInscripcionCicloListInscripcionCiclo = inscripcionCicloListInscripcionCiclo.getFkIncCic();
                inscripcionCicloListInscripcionCiclo.setFkIncCic(ciclo);
                inscripcionCicloListInscripcionCiclo = em.merge(inscripcionCicloListInscripcionCiclo);
                if (oldFkIncCicOfInscripcionCicloListInscripcionCiclo != null) {
                    oldFkIncCicOfInscripcionCicloListInscripcionCiclo.getInscripcionCicloList().remove(inscripcionCicloListInscripcionCiclo);
                    oldFkIncCicOfInscripcionCicloListInscripcionCiclo = em.merge(oldFkIncCicOfInscripcionCicloListInscripcionCiclo);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Ciclo ciclo) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ciclo persistentCiclo = em.find(Ciclo.class, ciclo.getCiId());
            List<InscripcionCiclo> inscripcionCicloListOld = persistentCiclo.getInscripcionCicloList();
            List<InscripcionCiclo> inscripcionCicloListNew = ciclo.getInscripcionCicloList();
            List<String> illegalOrphanMessages = null;
            for (InscripcionCiclo inscripcionCicloListOldInscripcionCiclo : inscripcionCicloListOld) {
                if (!inscripcionCicloListNew.contains(inscripcionCicloListOldInscripcionCiclo)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain InscripcionCiclo " + inscripcionCicloListOldInscripcionCiclo + " since its fkIncCic field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<InscripcionCiclo> attachedInscripcionCicloListNew = new ArrayList<InscripcionCiclo>();
            for (InscripcionCiclo inscripcionCicloListNewInscripcionCicloToAttach : inscripcionCicloListNew) {
                inscripcionCicloListNewInscripcionCicloToAttach = em.getReference(inscripcionCicloListNewInscripcionCicloToAttach.getClass(), inscripcionCicloListNewInscripcionCicloToAttach.getIcId());
                attachedInscripcionCicloListNew.add(inscripcionCicloListNewInscripcionCicloToAttach);
            }
            inscripcionCicloListNew = attachedInscripcionCicloListNew;
            ciclo.setInscripcionCicloList(inscripcionCicloListNew);
            ciclo = em.merge(ciclo);
            for (InscripcionCiclo inscripcionCicloListNewInscripcionCiclo : inscripcionCicloListNew) {
                if (!inscripcionCicloListOld.contains(inscripcionCicloListNewInscripcionCiclo)) {
                    Ciclo oldFkIncCicOfInscripcionCicloListNewInscripcionCiclo = inscripcionCicloListNewInscripcionCiclo.getFkIncCic();
                    inscripcionCicloListNewInscripcionCiclo.setFkIncCic(ciclo);
                    inscripcionCicloListNewInscripcionCiclo = em.merge(inscripcionCicloListNewInscripcionCiclo);
                    if (oldFkIncCicOfInscripcionCicloListNewInscripcionCiclo != null && !oldFkIncCicOfInscripcionCicloListNewInscripcionCiclo.equals(ciclo)) {
                        oldFkIncCicOfInscripcionCicloListNewInscripcionCiclo.getInscripcionCicloList().remove(inscripcionCicloListNewInscripcionCiclo);
                        oldFkIncCicOfInscripcionCicloListNewInscripcionCiclo = em.merge(oldFkIncCicOfInscripcionCicloListNewInscripcionCiclo);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = ciclo.getCiId();
                if (findCiclo(id) == null) {
                    throw new NonexistentEntityException("The ciclo with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ciclo ciclo;
            try {
                ciclo = em.getReference(Ciclo.class, id);
                ciclo.getCiId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ciclo with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<InscripcionCiclo> inscripcionCicloListOrphanCheck = ciclo.getInscripcionCicloList();
            for (InscripcionCiclo inscripcionCicloListOrphanCheckInscripcionCiclo : inscripcionCicloListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Ciclo (" + ciclo + ") cannot be destroyed since the InscripcionCiclo " + inscripcionCicloListOrphanCheckInscripcionCiclo + " in its inscripcionCicloList field has a non-nullable fkIncCic field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(ciclo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Ciclo> findCicloEntities() {
        return findCicloEntities(true, -1, -1);
    }

    public List<Ciclo> findCicloEntities(int maxResults, int firstResult) {
        return findCicloEntities(false, maxResults, firstResult);
    }

    private List<Ciclo> findCicloEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Ciclo.class));
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

    public Ciclo findCiclo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Ciclo.class, id);
        } finally {
            em.close();
        }
    }

    public int getCicloCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Ciclo> rt = cq.from(Ciclo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
