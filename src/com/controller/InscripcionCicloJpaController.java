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
import com.entities.Estudiante;
import com.entities.Ciclo;
import com.entities.InscripcionCiclo;
import com.entities.Materia;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Nataly
 */
public class InscripcionCicloJpaController implements Serializable {

    public InscripcionCicloJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(InscripcionCiclo inscripcionCiclo) {
        if (inscripcionCiclo.getMateriaList() == null) {
            inscripcionCiclo.setMateriaList(new ArrayList<Materia>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Estudiante fkIncEst = inscripcionCiclo.getFkIncEst();
            if (fkIncEst != null) {
                fkIncEst = em.getReference(fkIncEst.getClass(), fkIncEst.getEsId());
                inscripcionCiclo.setFkIncEst(fkIncEst);
            }
            Ciclo fkIncCic = inscripcionCiclo.getFkIncCic();
            if (fkIncCic != null) {
                fkIncCic = em.getReference(fkIncCic.getClass(), fkIncCic.getCiId());
                inscripcionCiclo.setFkIncCic(fkIncCic);
            }
            List<Materia> attachedMateriaList = new ArrayList<Materia>();
            for (Materia materiaListMateriaToAttach : inscripcionCiclo.getMateriaList()) {
                materiaListMateriaToAttach = em.getReference(materiaListMateriaToAttach.getClass(), materiaListMateriaToAttach.getMaId());
                attachedMateriaList.add(materiaListMateriaToAttach);
            }
            inscripcionCiclo.setMateriaList(attachedMateriaList);
            em.persist(inscripcionCiclo);
            if (fkIncEst != null) {
                fkIncEst.getInscripcionCicloList().add(inscripcionCiclo);
                fkIncEst = em.merge(fkIncEst);
            }
            if (fkIncCic != null) {
                fkIncCic.getInscripcionCicloList().add(inscripcionCiclo);
                fkIncCic = em.merge(fkIncCic);
            }
            for (Materia materiaListMateria : inscripcionCiclo.getMateriaList()) {
                InscripcionCiclo oldFkMatIncOfMateriaListMateria = materiaListMateria.getFkMatInc();
                materiaListMateria.setFkMatInc(inscripcionCiclo);
                materiaListMateria = em.merge(materiaListMateria);
                if (oldFkMatIncOfMateriaListMateria != null) {
                    oldFkMatIncOfMateriaListMateria.getMateriaList().remove(materiaListMateria);
                    oldFkMatIncOfMateriaListMateria = em.merge(oldFkMatIncOfMateriaListMateria);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(InscripcionCiclo inscripcionCiclo) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            InscripcionCiclo persistentInscripcionCiclo = em.find(InscripcionCiclo.class, inscripcionCiclo.getIcId());
            Estudiante fkIncEstOld = persistentInscripcionCiclo.getFkIncEst();
            Estudiante fkIncEstNew = inscripcionCiclo.getFkIncEst();
            Ciclo fkIncCicOld = persistentInscripcionCiclo.getFkIncCic();
            Ciclo fkIncCicNew = inscripcionCiclo.getFkIncCic();
            List<Materia> materiaListOld = persistentInscripcionCiclo.getMateriaList();
            List<Materia> materiaListNew = inscripcionCiclo.getMateriaList();
            List<String> illegalOrphanMessages = null;
            for (Materia materiaListOldMateria : materiaListOld) {
                if (!materiaListNew.contains(materiaListOldMateria)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Materia " + materiaListOldMateria + " since its fkMatInc field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (fkIncEstNew != null) {
                fkIncEstNew = em.getReference(fkIncEstNew.getClass(), fkIncEstNew.getEsId());
                inscripcionCiclo.setFkIncEst(fkIncEstNew);
            }
            if (fkIncCicNew != null) {
                fkIncCicNew = em.getReference(fkIncCicNew.getClass(), fkIncCicNew.getCiId());
                inscripcionCiclo.setFkIncCic(fkIncCicNew);
            }
            List<Materia> attachedMateriaListNew = new ArrayList<Materia>();
            for (Materia materiaListNewMateriaToAttach : materiaListNew) {
                materiaListNewMateriaToAttach = em.getReference(materiaListNewMateriaToAttach.getClass(), materiaListNewMateriaToAttach.getMaId());
                attachedMateriaListNew.add(materiaListNewMateriaToAttach);
            }
            materiaListNew = attachedMateriaListNew;
            inscripcionCiclo.setMateriaList(materiaListNew);
            inscripcionCiclo = em.merge(inscripcionCiclo);
            if (fkIncEstOld != null && !fkIncEstOld.equals(fkIncEstNew)) {
                fkIncEstOld.getInscripcionCicloList().remove(inscripcionCiclo);
                fkIncEstOld = em.merge(fkIncEstOld);
            }
            if (fkIncEstNew != null && !fkIncEstNew.equals(fkIncEstOld)) {
                fkIncEstNew.getInscripcionCicloList().add(inscripcionCiclo);
                fkIncEstNew = em.merge(fkIncEstNew);
            }
            if (fkIncCicOld != null && !fkIncCicOld.equals(fkIncCicNew)) {
                fkIncCicOld.getInscripcionCicloList().remove(inscripcionCiclo);
                fkIncCicOld = em.merge(fkIncCicOld);
            }
            if (fkIncCicNew != null && !fkIncCicNew.equals(fkIncCicOld)) {
                fkIncCicNew.getInscripcionCicloList().add(inscripcionCiclo);
                fkIncCicNew = em.merge(fkIncCicNew);
            }
            for (Materia materiaListNewMateria : materiaListNew) {
                if (!materiaListOld.contains(materiaListNewMateria)) {
                    InscripcionCiclo oldFkMatIncOfMateriaListNewMateria = materiaListNewMateria.getFkMatInc();
                    materiaListNewMateria.setFkMatInc(inscripcionCiclo);
                    materiaListNewMateria = em.merge(materiaListNewMateria);
                    if (oldFkMatIncOfMateriaListNewMateria != null && !oldFkMatIncOfMateriaListNewMateria.equals(inscripcionCiclo)) {
                        oldFkMatIncOfMateriaListNewMateria.getMateriaList().remove(materiaListNewMateria);
                        oldFkMatIncOfMateriaListNewMateria = em.merge(oldFkMatIncOfMateriaListNewMateria);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = inscripcionCiclo.getIcId();
                if (findInscripcionCiclo(id) == null) {
                    throw new NonexistentEntityException("The inscripcionCiclo with id " + id + " no longer exists.");
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
            InscripcionCiclo inscripcionCiclo;
            try {
                inscripcionCiclo = em.getReference(InscripcionCiclo.class, id);
                inscripcionCiclo.getIcId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The inscripcionCiclo with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Materia> materiaListOrphanCheck = inscripcionCiclo.getMateriaList();
            for (Materia materiaListOrphanCheckMateria : materiaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This InscripcionCiclo (" + inscripcionCiclo + ") cannot be destroyed since the Materia " + materiaListOrphanCheckMateria + " in its materiaList field has a non-nullable fkMatInc field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Estudiante fkIncEst = inscripcionCiclo.getFkIncEst();
            if (fkIncEst != null) {
                fkIncEst.getInscripcionCicloList().remove(inscripcionCiclo);
                fkIncEst = em.merge(fkIncEst);
            }
            Ciclo fkIncCic = inscripcionCiclo.getFkIncCic();
            if (fkIncCic != null) {
                fkIncCic.getInscripcionCicloList().remove(inscripcionCiclo);
                fkIncCic = em.merge(fkIncCic);
            }
            em.remove(inscripcionCiclo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<InscripcionCiclo> findInscripcionCicloEntities() {
        return findInscripcionCicloEntities(true, -1, -1);
    }

    public List<InscripcionCiclo> findInscripcionCicloEntities(int maxResults, int firstResult) {
        return findInscripcionCicloEntities(false, maxResults, firstResult);
    }

    private List<InscripcionCiclo> findInscripcionCicloEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(InscripcionCiclo.class));
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

    public InscripcionCiclo findInscripcionCiclo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(InscripcionCiclo.class, id);
        } finally {
            em.close();
        }
    }

    public int getInscripcionCicloCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<InscripcionCiclo> rt = cq.from(InscripcionCiclo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
