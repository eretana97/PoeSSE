/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller;

import com.controller.exceptions.IllegalOrphanException;
import com.controller.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.entities.InscripcionCiclo;
import com.entities.Materia;
import com.entities.Nota;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Nataly
 */
public class MateriaJpaController implements Serializable {

    public MateriaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Materia materia) {
        if (materia.getNotaList() == null) {
            materia.setNotaList(new ArrayList<Nota>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            InscripcionCiclo fkMatInc = materia.getFkMatInc();
            if (fkMatInc != null) {
                fkMatInc = em.getReference(fkMatInc.getClass(), fkMatInc.getIcId());
                materia.setFkMatInc(fkMatInc);
            }
            List<Nota> attachedNotaList = new ArrayList<Nota>();
            for (Nota notaListNotaToAttach : materia.getNotaList()) {
                notaListNotaToAttach = em.getReference(notaListNotaToAttach.getClass(), notaListNotaToAttach.getNoId());
                attachedNotaList.add(notaListNotaToAttach);
            }
            materia.setNotaList(attachedNotaList);
            em.persist(materia);
            if (fkMatInc != null) {
                fkMatInc.getMateriaList().add(materia);
                fkMatInc = em.merge(fkMatInc);
            }
            for (Nota notaListNota : materia.getNotaList()) {
                Materia oldFkNotMatOfNotaListNota = notaListNota.getFkNotMat();
                notaListNota.setFkNotMat(materia);
                notaListNota = em.merge(notaListNota);
                if (oldFkNotMatOfNotaListNota != null) {
                    oldFkNotMatOfNotaListNota.getNotaList().remove(notaListNota);
                    oldFkNotMatOfNotaListNota = em.merge(oldFkNotMatOfNotaListNota);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Materia materia) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Materia persistentMateria = em.find(Materia.class, materia.getMaId());
            InscripcionCiclo fkMatIncOld = persistentMateria.getFkMatInc();
            InscripcionCiclo fkMatIncNew = materia.getFkMatInc();
            List<Nota> notaListOld = persistentMateria.getNotaList();
            List<Nota> notaListNew = materia.getNotaList();
            List<String> illegalOrphanMessages = null;
            for (Nota notaListOldNota : notaListOld) {
                if (!notaListNew.contains(notaListOldNota)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Nota " + notaListOldNota + " since its fkNotMat field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (fkMatIncNew != null) {
                fkMatIncNew = em.getReference(fkMatIncNew.getClass(), fkMatIncNew.getIcId());
                materia.setFkMatInc(fkMatIncNew);
            }
            List<Nota> attachedNotaListNew = new ArrayList<Nota>();
            for (Nota notaListNewNotaToAttach : notaListNew) {
                notaListNewNotaToAttach = em.getReference(notaListNewNotaToAttach.getClass(), notaListNewNotaToAttach.getNoId());
                attachedNotaListNew.add(notaListNewNotaToAttach);
            }
            notaListNew = attachedNotaListNew;
            materia.setNotaList(notaListNew);
            materia = em.merge(materia);
            if (fkMatIncOld != null && !fkMatIncOld.equals(fkMatIncNew)) {
                fkMatIncOld.getMateriaList().remove(materia);
                fkMatIncOld = em.merge(fkMatIncOld);
            }
            if (fkMatIncNew != null && !fkMatIncNew.equals(fkMatIncOld)) {
                fkMatIncNew.getMateriaList().add(materia);
                fkMatIncNew = em.merge(fkMatIncNew);
            }
            for (Nota notaListNewNota : notaListNew) {
                if (!notaListOld.contains(notaListNewNota)) {
                    Materia oldFkNotMatOfNotaListNewNota = notaListNewNota.getFkNotMat();
                    notaListNewNota.setFkNotMat(materia);
                    notaListNewNota = em.merge(notaListNewNota);
                    if (oldFkNotMatOfNotaListNewNota != null && !oldFkNotMatOfNotaListNewNota.equals(materia)) {
                        oldFkNotMatOfNotaListNewNota.getNotaList().remove(notaListNewNota);
                        oldFkNotMatOfNotaListNewNota = em.merge(oldFkNotMatOfNotaListNewNota);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = materia.getMaId();
                if (findMateria(id) == null) {
                    throw new NonexistentEntityException("The materia with id " + id + " no longer exists.");
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
            Materia materia;
            try {
                materia = em.getReference(Materia.class, id);
                materia.getMaId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The materia with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Nota> notaListOrphanCheck = materia.getNotaList();
            for (Nota notaListOrphanCheckNota : notaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Materia (" + materia + ") cannot be destroyed since the Nota " + notaListOrphanCheckNota + " in its notaList field has a non-nullable fkNotMat field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            InscripcionCiclo fkMatInc = materia.getFkMatInc();
            if (fkMatInc != null) {
                fkMatInc.getMateriaList().remove(materia);
                fkMatInc = em.merge(fkMatInc);
            }
            em.remove(materia);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Materia> findMateriaEntities() {
        return findMateriaEntities(true, -1, -1);
    }

    public List<Materia> findMateriaEntities(int maxResults, int firstResult) {
        return findMateriaEntities(false, maxResults, firstResult);
    }

    private List<Materia> findMateriaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Materia.class));
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

    public Materia findMateria(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Materia.class, id);
        } finally {
            em.close();
        }
    }

    public int getMateriaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Materia> rt = cq.from(Materia.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
