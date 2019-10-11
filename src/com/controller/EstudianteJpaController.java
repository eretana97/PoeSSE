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
import com.entities.Usuario;
import com.entities.Carrera;
import com.entities.Estudiante;
import com.entities.InscripcionCiclo;
import java.util.ArrayList;
import java.util.List;
import com.entities.Solicitud;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Nataly
 */
public class EstudianteJpaController implements Serializable {

    public EstudianteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Estudiante estudiante) {
        if (estudiante.getInscripcionCicloList() == null) {
            estudiante.setInscripcionCicloList(new ArrayList<InscripcionCiclo>());
        }
        if (estudiante.getSolicitudList() == null) {
            estudiante.setSolicitudList(new ArrayList<Solicitud>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario fkEstUsu = estudiante.getFkEstUsu();
            if (fkEstUsu != null) {
                fkEstUsu = em.getReference(fkEstUsu.getClass(), fkEstUsu.getUsId());
                estudiante.setFkEstUsu(fkEstUsu);
            }
            Carrera fkEstCar = estudiante.getFkEstCar();
            if (fkEstCar != null) {
                fkEstCar = em.getReference(fkEstCar.getClass(), fkEstCar.getCaId());
                estudiante.setFkEstCar(fkEstCar);
            }
            List<InscripcionCiclo> attachedInscripcionCicloList = new ArrayList<InscripcionCiclo>();
            for (InscripcionCiclo inscripcionCicloListInscripcionCicloToAttach : estudiante.getInscripcionCicloList()) {
                inscripcionCicloListInscripcionCicloToAttach = em.getReference(inscripcionCicloListInscripcionCicloToAttach.getClass(), inscripcionCicloListInscripcionCicloToAttach.getIcId());
                attachedInscripcionCicloList.add(inscripcionCicloListInscripcionCicloToAttach);
            }
            estudiante.setInscripcionCicloList(attachedInscripcionCicloList);
            List<Solicitud> attachedSolicitudList = new ArrayList<Solicitud>();
            for (Solicitud solicitudListSolicitudToAttach : estudiante.getSolicitudList()) {
                solicitudListSolicitudToAttach = em.getReference(solicitudListSolicitudToAttach.getClass(), solicitudListSolicitudToAttach.getSoId());
                attachedSolicitudList.add(solicitudListSolicitudToAttach);
            }
            estudiante.setSolicitudList(attachedSolicitudList);
            em.persist(estudiante);
            if (fkEstUsu != null) {
                fkEstUsu.getEstudianteList().add(estudiante);
                fkEstUsu = em.merge(fkEstUsu);
            }
            if (fkEstCar != null) {
                fkEstCar.getEstudianteList().add(estudiante);
                fkEstCar = em.merge(fkEstCar);
            }
            for (InscripcionCiclo inscripcionCicloListInscripcionCiclo : estudiante.getInscripcionCicloList()) {
                Estudiante oldFkIncEstOfInscripcionCicloListInscripcionCiclo = inscripcionCicloListInscripcionCiclo.getFkIncEst();
                inscripcionCicloListInscripcionCiclo.setFkIncEst(estudiante);
                inscripcionCicloListInscripcionCiclo = em.merge(inscripcionCicloListInscripcionCiclo);
                if (oldFkIncEstOfInscripcionCicloListInscripcionCiclo != null) {
                    oldFkIncEstOfInscripcionCicloListInscripcionCiclo.getInscripcionCicloList().remove(inscripcionCicloListInscripcionCiclo);
                    oldFkIncEstOfInscripcionCicloListInscripcionCiclo = em.merge(oldFkIncEstOfInscripcionCicloListInscripcionCiclo);
                }
            }
            for (Solicitud solicitudListSolicitud : estudiante.getSolicitudList()) {
                Estudiante oldFkSolEstOfSolicitudListSolicitud = solicitudListSolicitud.getFkSolEst();
                solicitudListSolicitud.setFkSolEst(estudiante);
                solicitudListSolicitud = em.merge(solicitudListSolicitud);
                if (oldFkSolEstOfSolicitudListSolicitud != null) {
                    oldFkSolEstOfSolicitudListSolicitud.getSolicitudList().remove(solicitudListSolicitud);
                    oldFkSolEstOfSolicitudListSolicitud = em.merge(oldFkSolEstOfSolicitudListSolicitud);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Estudiante estudiante) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Estudiante persistentEstudiante = em.find(Estudiante.class, estudiante.getEsId());
            Usuario fkEstUsuOld = persistentEstudiante.getFkEstUsu();
            Usuario fkEstUsuNew = estudiante.getFkEstUsu();
            Carrera fkEstCarOld = persistentEstudiante.getFkEstCar();
            Carrera fkEstCarNew = estudiante.getFkEstCar();
            List<InscripcionCiclo> inscripcionCicloListOld = persistentEstudiante.getInscripcionCicloList();
            List<InscripcionCiclo> inscripcionCicloListNew = estudiante.getInscripcionCicloList();
            List<Solicitud> solicitudListOld = persistentEstudiante.getSolicitudList();
            List<Solicitud> solicitudListNew = estudiante.getSolicitudList();
            List<String> illegalOrphanMessages = null;
            for (InscripcionCiclo inscripcionCicloListOldInscripcionCiclo : inscripcionCicloListOld) {
                if (!inscripcionCicloListNew.contains(inscripcionCicloListOldInscripcionCiclo)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain InscripcionCiclo " + inscripcionCicloListOldInscripcionCiclo + " since its fkIncEst field is not nullable.");
                }
            }
            for (Solicitud solicitudListOldSolicitud : solicitudListOld) {
                if (!solicitudListNew.contains(solicitudListOldSolicitud)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Solicitud " + solicitudListOldSolicitud + " since its fkSolEst field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (fkEstUsuNew != null) {
                fkEstUsuNew = em.getReference(fkEstUsuNew.getClass(), fkEstUsuNew.getUsId());
                estudiante.setFkEstUsu(fkEstUsuNew);
            }
            if (fkEstCarNew != null) {
                fkEstCarNew = em.getReference(fkEstCarNew.getClass(), fkEstCarNew.getCaId());
                estudiante.setFkEstCar(fkEstCarNew);
            }
            List<InscripcionCiclo> attachedInscripcionCicloListNew = new ArrayList<InscripcionCiclo>();
            for (InscripcionCiclo inscripcionCicloListNewInscripcionCicloToAttach : inscripcionCicloListNew) {
                inscripcionCicloListNewInscripcionCicloToAttach = em.getReference(inscripcionCicloListNewInscripcionCicloToAttach.getClass(), inscripcionCicloListNewInscripcionCicloToAttach.getIcId());
                attachedInscripcionCicloListNew.add(inscripcionCicloListNewInscripcionCicloToAttach);
            }
            inscripcionCicloListNew = attachedInscripcionCicloListNew;
            estudiante.setInscripcionCicloList(inscripcionCicloListNew);
            List<Solicitud> attachedSolicitudListNew = new ArrayList<Solicitud>();
            for (Solicitud solicitudListNewSolicitudToAttach : solicitudListNew) {
                solicitudListNewSolicitudToAttach = em.getReference(solicitudListNewSolicitudToAttach.getClass(), solicitudListNewSolicitudToAttach.getSoId());
                attachedSolicitudListNew.add(solicitudListNewSolicitudToAttach);
            }
            solicitudListNew = attachedSolicitudListNew;
            estudiante.setSolicitudList(solicitudListNew);
            estudiante = em.merge(estudiante);
            if (fkEstUsuOld != null && !fkEstUsuOld.equals(fkEstUsuNew)) {
                fkEstUsuOld.getEstudianteList().remove(estudiante);
                fkEstUsuOld = em.merge(fkEstUsuOld);
            }
            if (fkEstUsuNew != null && !fkEstUsuNew.equals(fkEstUsuOld)) {
                fkEstUsuNew.getEstudianteList().add(estudiante);
                fkEstUsuNew = em.merge(fkEstUsuNew);
            }
            if (fkEstCarOld != null && !fkEstCarOld.equals(fkEstCarNew)) {
                fkEstCarOld.getEstudianteList().remove(estudiante);
                fkEstCarOld = em.merge(fkEstCarOld);
            }
            if (fkEstCarNew != null && !fkEstCarNew.equals(fkEstCarOld)) {
                fkEstCarNew.getEstudianteList().add(estudiante);
                fkEstCarNew = em.merge(fkEstCarNew);
            }
            for (InscripcionCiclo inscripcionCicloListNewInscripcionCiclo : inscripcionCicloListNew) {
                if (!inscripcionCicloListOld.contains(inscripcionCicloListNewInscripcionCiclo)) {
                    Estudiante oldFkIncEstOfInscripcionCicloListNewInscripcionCiclo = inscripcionCicloListNewInscripcionCiclo.getFkIncEst();
                    inscripcionCicloListNewInscripcionCiclo.setFkIncEst(estudiante);
                    inscripcionCicloListNewInscripcionCiclo = em.merge(inscripcionCicloListNewInscripcionCiclo);
                    if (oldFkIncEstOfInscripcionCicloListNewInscripcionCiclo != null && !oldFkIncEstOfInscripcionCicloListNewInscripcionCiclo.equals(estudiante)) {
                        oldFkIncEstOfInscripcionCicloListNewInscripcionCiclo.getInscripcionCicloList().remove(inscripcionCicloListNewInscripcionCiclo);
                        oldFkIncEstOfInscripcionCicloListNewInscripcionCiclo = em.merge(oldFkIncEstOfInscripcionCicloListNewInscripcionCiclo);
                    }
                }
            }
            for (Solicitud solicitudListNewSolicitud : solicitudListNew) {
                if (!solicitudListOld.contains(solicitudListNewSolicitud)) {
                    Estudiante oldFkSolEstOfSolicitudListNewSolicitud = solicitudListNewSolicitud.getFkSolEst();
                    solicitudListNewSolicitud.setFkSolEst(estudiante);
                    solicitudListNewSolicitud = em.merge(solicitudListNewSolicitud);
                    if (oldFkSolEstOfSolicitudListNewSolicitud != null && !oldFkSolEstOfSolicitudListNewSolicitud.equals(estudiante)) {
                        oldFkSolEstOfSolicitudListNewSolicitud.getSolicitudList().remove(solicitudListNewSolicitud);
                        oldFkSolEstOfSolicitudListNewSolicitud = em.merge(oldFkSolEstOfSolicitudListNewSolicitud);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = estudiante.getEsId();
                if (findEstudiante(id) == null) {
                    throw new NonexistentEntityException("The estudiante with id " + id + " no longer exists.");
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
            Estudiante estudiante;
            try {
                estudiante = em.getReference(Estudiante.class, id);
                estudiante.getEsId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The estudiante with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<InscripcionCiclo> inscripcionCicloListOrphanCheck = estudiante.getInscripcionCicloList();
            for (InscripcionCiclo inscripcionCicloListOrphanCheckInscripcionCiclo : inscripcionCicloListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Estudiante (" + estudiante + ") cannot be destroyed since the InscripcionCiclo " + inscripcionCicloListOrphanCheckInscripcionCiclo + " in its inscripcionCicloList field has a non-nullable fkIncEst field.");
            }
            List<Solicitud> solicitudListOrphanCheck = estudiante.getSolicitudList();
            for (Solicitud solicitudListOrphanCheckSolicitud : solicitudListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Estudiante (" + estudiante + ") cannot be destroyed since the Solicitud " + solicitudListOrphanCheckSolicitud + " in its solicitudList field has a non-nullable fkSolEst field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Usuario fkEstUsu = estudiante.getFkEstUsu();
            if (fkEstUsu != null) {
                fkEstUsu.getEstudianteList().remove(estudiante);
                fkEstUsu = em.merge(fkEstUsu);
            }
            Carrera fkEstCar = estudiante.getFkEstCar();
            if (fkEstCar != null) {
                fkEstCar.getEstudianteList().remove(estudiante);
                fkEstCar = em.merge(fkEstCar);
            }
            em.remove(estudiante);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Estudiante> findEstudianteEntities() {
        return findEstudianteEntities(true, -1, -1);
    }

    public List<Estudiante> findEstudianteEntities(int maxResults, int firstResult) {
        return findEstudianteEntities(false, maxResults, firstResult);
    }

    private List<Estudiante> findEstudianteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Estudiante.class));
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

    public Estudiante findEstudiante(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Estudiante.class, id);
        } finally {
            em.close();
        }
    }

    public int getEstudianteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Estudiante> rt = cq.from(Estudiante.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
