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
import com.entities.Actividad;
import java.util.ArrayList;
import java.util.List;
import com.entities.HojaControl;
import com.entities.Institucion;
import com.entities.HorariosSse;
import com.entities.FormularioFinalizacion;
import com.entities.Solicitud;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Nataly
 */
public class SolicitudJpaController implements Serializable {

    public SolicitudJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Solicitud solicitud) {
        if (solicitud.getActividadList() == null) {
            solicitud.setActividadList(new ArrayList<Actividad>());
        }
        if (solicitud.getHojaControlList() == null) {
            solicitud.setHojaControlList(new ArrayList<HojaControl>());
        }
        if (solicitud.getInstitucionList() == null) {
            solicitud.setInstitucionList(new ArrayList<Institucion>());
        }
        if (solicitud.getHorariosSseList() == null) {
            solicitud.setHorariosSseList(new ArrayList<HorariosSse>());
        }
        if (solicitud.getFormularioFinalizacionList() == null) {
            solicitud.setFormularioFinalizacionList(new ArrayList<FormularioFinalizacion>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Estudiante fkSolEst = solicitud.getFkSolEst();
            if (fkSolEst != null) {
                fkSolEst = em.getReference(fkSolEst.getClass(), fkSolEst.getEsId());
                solicitud.setFkSolEst(fkSolEst);
            }
            List<Actividad> attachedActividadList = new ArrayList<Actividad>();
            for (Actividad actividadListActividadToAttach : solicitud.getActividadList()) {
                actividadListActividadToAttach = em.getReference(actividadListActividadToAttach.getClass(), actividadListActividadToAttach.getAcId());
                attachedActividadList.add(actividadListActividadToAttach);
            }
            solicitud.setActividadList(attachedActividadList);
            List<HojaControl> attachedHojaControlList = new ArrayList<HojaControl>();
            for (HojaControl hojaControlListHojaControlToAttach : solicitud.getHojaControlList()) {
                hojaControlListHojaControlToAttach = em.getReference(hojaControlListHojaControlToAttach.getClass(), hojaControlListHojaControlToAttach.getHcId());
                attachedHojaControlList.add(hojaControlListHojaControlToAttach);
            }
            solicitud.setHojaControlList(attachedHojaControlList);
            List<Institucion> attachedInstitucionList = new ArrayList<Institucion>();
            for (Institucion institucionListInstitucionToAttach : solicitud.getInstitucionList()) {
                institucionListInstitucionToAttach = em.getReference(institucionListInstitucionToAttach.getClass(), institucionListInstitucionToAttach.getInId());
                attachedInstitucionList.add(institucionListInstitucionToAttach);
            }
            solicitud.setInstitucionList(attachedInstitucionList);
            List<HorariosSse> attachedHorariosSseList = new ArrayList<HorariosSse>();
            for (HorariosSse horariosSseListHorariosSseToAttach : solicitud.getHorariosSseList()) {
                horariosSseListHorariosSseToAttach = em.getReference(horariosSseListHorariosSseToAttach.getClass(), horariosSseListHorariosSseToAttach.getHsId());
                attachedHorariosSseList.add(horariosSseListHorariosSseToAttach);
            }
            solicitud.setHorariosSseList(attachedHorariosSseList);
            List<FormularioFinalizacion> attachedFormularioFinalizacionList = new ArrayList<FormularioFinalizacion>();
            for (FormularioFinalizacion formularioFinalizacionListFormularioFinalizacionToAttach : solicitud.getFormularioFinalizacionList()) {
                formularioFinalizacionListFormularioFinalizacionToAttach = em.getReference(formularioFinalizacionListFormularioFinalizacionToAttach.getClass(), formularioFinalizacionListFormularioFinalizacionToAttach.getFfId());
                attachedFormularioFinalizacionList.add(formularioFinalizacionListFormularioFinalizacionToAttach);
            }
            solicitud.setFormularioFinalizacionList(attachedFormularioFinalizacionList);
            em.persist(solicitud);
            if (fkSolEst != null) {
                fkSolEst.getSolicitudList().add(solicitud);
                fkSolEst = em.merge(fkSolEst);
            }
            for (Actividad actividadListActividad : solicitud.getActividadList()) {
                Solicitud oldFkActSolOfActividadListActividad = actividadListActividad.getFkActSol();
                actividadListActividad.setFkActSol(solicitud);
                actividadListActividad = em.merge(actividadListActividad);
                if (oldFkActSolOfActividadListActividad != null) {
                    oldFkActSolOfActividadListActividad.getActividadList().remove(actividadListActividad);
                    oldFkActSolOfActividadListActividad = em.merge(oldFkActSolOfActividadListActividad);
                }
            }
            for (HojaControl hojaControlListHojaControl : solicitud.getHojaControlList()) {
                Solicitud oldFkHcoSolOfHojaControlListHojaControl = hojaControlListHojaControl.getFkHcoSol();
                hojaControlListHojaControl.setFkHcoSol(solicitud);
                hojaControlListHojaControl = em.merge(hojaControlListHojaControl);
                if (oldFkHcoSolOfHojaControlListHojaControl != null) {
                    oldFkHcoSolOfHojaControlListHojaControl.getHojaControlList().remove(hojaControlListHojaControl);
                    oldFkHcoSolOfHojaControlListHojaControl = em.merge(oldFkHcoSolOfHojaControlListHojaControl);
                }
            }
            for (Institucion institucionListInstitucion : solicitud.getInstitucionList()) {
                Solicitud oldFkInsSolOfInstitucionListInstitucion = institucionListInstitucion.getFkInsSol();
                institucionListInstitucion.setFkInsSol(solicitud);
                institucionListInstitucion = em.merge(institucionListInstitucion);
                if (oldFkInsSolOfInstitucionListInstitucion != null) {
                    oldFkInsSolOfInstitucionListInstitucion.getInstitucionList().remove(institucionListInstitucion);
                    oldFkInsSolOfInstitucionListInstitucion = em.merge(oldFkInsSolOfInstitucionListInstitucion);
                }
            }
            for (HorariosSse horariosSseListHorariosSse : solicitud.getHorariosSseList()) {
                Solicitud oldFkHssSolOfHorariosSseListHorariosSse = horariosSseListHorariosSse.getFkHssSol();
                horariosSseListHorariosSse.setFkHssSol(solicitud);
                horariosSseListHorariosSse = em.merge(horariosSseListHorariosSse);
                if (oldFkHssSolOfHorariosSseListHorariosSse != null) {
                    oldFkHssSolOfHorariosSseListHorariosSse.getHorariosSseList().remove(horariosSseListHorariosSse);
                    oldFkHssSolOfHorariosSseListHorariosSse = em.merge(oldFkHssSolOfHorariosSseListHorariosSse);
                }
            }
            for (FormularioFinalizacion formularioFinalizacionListFormularioFinalizacion : solicitud.getFormularioFinalizacionList()) {
                Solicitud oldFkFfiSolOfFormularioFinalizacionListFormularioFinalizacion = formularioFinalizacionListFormularioFinalizacion.getFkFfiSol();
                formularioFinalizacionListFormularioFinalizacion.setFkFfiSol(solicitud);
                formularioFinalizacionListFormularioFinalizacion = em.merge(formularioFinalizacionListFormularioFinalizacion);
                if (oldFkFfiSolOfFormularioFinalizacionListFormularioFinalizacion != null) {
                    oldFkFfiSolOfFormularioFinalizacionListFormularioFinalizacion.getFormularioFinalizacionList().remove(formularioFinalizacionListFormularioFinalizacion);
                    oldFkFfiSolOfFormularioFinalizacionListFormularioFinalizacion = em.merge(oldFkFfiSolOfFormularioFinalizacionListFormularioFinalizacion);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Solicitud solicitud) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Solicitud persistentSolicitud = em.find(Solicitud.class, solicitud.getSoId());
            Estudiante fkSolEstOld = persistentSolicitud.getFkSolEst();
            Estudiante fkSolEstNew = solicitud.getFkSolEst();
            List<Actividad> actividadListOld = persistentSolicitud.getActividadList();
            List<Actividad> actividadListNew = solicitud.getActividadList();
            List<HojaControl> hojaControlListOld = persistentSolicitud.getHojaControlList();
            List<HojaControl> hojaControlListNew = solicitud.getHojaControlList();
            List<Institucion> institucionListOld = persistentSolicitud.getInstitucionList();
            List<Institucion> institucionListNew = solicitud.getInstitucionList();
            List<HorariosSse> horariosSseListOld = persistentSolicitud.getHorariosSseList();
            List<HorariosSse> horariosSseListNew = solicitud.getHorariosSseList();
            List<FormularioFinalizacion> formularioFinalizacionListOld = persistentSolicitud.getFormularioFinalizacionList();
            List<FormularioFinalizacion> formularioFinalizacionListNew = solicitud.getFormularioFinalizacionList();
            List<String> illegalOrphanMessages = null;
            for (Actividad actividadListOldActividad : actividadListOld) {
                if (!actividadListNew.contains(actividadListOldActividad)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Actividad " + actividadListOldActividad + " since its fkActSol field is not nullable.");
                }
            }
            for (HojaControl hojaControlListOldHojaControl : hojaControlListOld) {
                if (!hojaControlListNew.contains(hojaControlListOldHojaControl)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain HojaControl " + hojaControlListOldHojaControl + " since its fkHcoSol field is not nullable.");
                }
            }
            for (Institucion institucionListOldInstitucion : institucionListOld) {
                if (!institucionListNew.contains(institucionListOldInstitucion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Institucion " + institucionListOldInstitucion + " since its fkInsSol field is not nullable.");
                }
            }
            for (HorariosSse horariosSseListOldHorariosSse : horariosSseListOld) {
                if (!horariosSseListNew.contains(horariosSseListOldHorariosSse)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain HorariosSse " + horariosSseListOldHorariosSse + " since its fkHssSol field is not nullable.");
                }
            }
            for (FormularioFinalizacion formularioFinalizacionListOldFormularioFinalizacion : formularioFinalizacionListOld) {
                if (!formularioFinalizacionListNew.contains(formularioFinalizacionListOldFormularioFinalizacion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain FormularioFinalizacion " + formularioFinalizacionListOldFormularioFinalizacion + " since its fkFfiSol field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (fkSolEstNew != null) {
                fkSolEstNew = em.getReference(fkSolEstNew.getClass(), fkSolEstNew.getEsId());
                solicitud.setFkSolEst(fkSolEstNew);
            }
            List<Actividad> attachedActividadListNew = new ArrayList<Actividad>();
            for (Actividad actividadListNewActividadToAttach : actividadListNew) {
                actividadListNewActividadToAttach = em.getReference(actividadListNewActividadToAttach.getClass(), actividadListNewActividadToAttach.getAcId());
                attachedActividadListNew.add(actividadListNewActividadToAttach);
            }
            actividadListNew = attachedActividadListNew;
            solicitud.setActividadList(actividadListNew);
            List<HojaControl> attachedHojaControlListNew = new ArrayList<HojaControl>();
            for (HojaControl hojaControlListNewHojaControlToAttach : hojaControlListNew) {
                hojaControlListNewHojaControlToAttach = em.getReference(hojaControlListNewHojaControlToAttach.getClass(), hojaControlListNewHojaControlToAttach.getHcId());
                attachedHojaControlListNew.add(hojaControlListNewHojaControlToAttach);
            }
            hojaControlListNew = attachedHojaControlListNew;
            solicitud.setHojaControlList(hojaControlListNew);
            List<Institucion> attachedInstitucionListNew = new ArrayList<Institucion>();
            for (Institucion institucionListNewInstitucionToAttach : institucionListNew) {
                institucionListNewInstitucionToAttach = em.getReference(institucionListNewInstitucionToAttach.getClass(), institucionListNewInstitucionToAttach.getInId());
                attachedInstitucionListNew.add(institucionListNewInstitucionToAttach);
            }
            institucionListNew = attachedInstitucionListNew;
            solicitud.setInstitucionList(institucionListNew);
            List<HorariosSse> attachedHorariosSseListNew = new ArrayList<HorariosSse>();
            for (HorariosSse horariosSseListNewHorariosSseToAttach : horariosSseListNew) {
                horariosSseListNewHorariosSseToAttach = em.getReference(horariosSseListNewHorariosSseToAttach.getClass(), horariosSseListNewHorariosSseToAttach.getHsId());
                attachedHorariosSseListNew.add(horariosSseListNewHorariosSseToAttach);
            }
            horariosSseListNew = attachedHorariosSseListNew;
            solicitud.setHorariosSseList(horariosSseListNew);
            List<FormularioFinalizacion> attachedFormularioFinalizacionListNew = new ArrayList<FormularioFinalizacion>();
            for (FormularioFinalizacion formularioFinalizacionListNewFormularioFinalizacionToAttach : formularioFinalizacionListNew) {
                formularioFinalizacionListNewFormularioFinalizacionToAttach = em.getReference(formularioFinalizacionListNewFormularioFinalizacionToAttach.getClass(), formularioFinalizacionListNewFormularioFinalizacionToAttach.getFfId());
                attachedFormularioFinalizacionListNew.add(formularioFinalizacionListNewFormularioFinalizacionToAttach);
            }
            formularioFinalizacionListNew = attachedFormularioFinalizacionListNew;
            solicitud.setFormularioFinalizacionList(formularioFinalizacionListNew);
            solicitud = em.merge(solicitud);
            if (fkSolEstOld != null && !fkSolEstOld.equals(fkSolEstNew)) {
                fkSolEstOld.getSolicitudList().remove(solicitud);
                fkSolEstOld = em.merge(fkSolEstOld);
            }
            if (fkSolEstNew != null && !fkSolEstNew.equals(fkSolEstOld)) {
                fkSolEstNew.getSolicitudList().add(solicitud);
                fkSolEstNew = em.merge(fkSolEstNew);
            }
            for (Actividad actividadListNewActividad : actividadListNew) {
                if (!actividadListOld.contains(actividadListNewActividad)) {
                    Solicitud oldFkActSolOfActividadListNewActividad = actividadListNewActividad.getFkActSol();
                    actividadListNewActividad.setFkActSol(solicitud);
                    actividadListNewActividad = em.merge(actividadListNewActividad);
                    if (oldFkActSolOfActividadListNewActividad != null && !oldFkActSolOfActividadListNewActividad.equals(solicitud)) {
                        oldFkActSolOfActividadListNewActividad.getActividadList().remove(actividadListNewActividad);
                        oldFkActSolOfActividadListNewActividad = em.merge(oldFkActSolOfActividadListNewActividad);
                    }
                }
            }
            for (HojaControl hojaControlListNewHojaControl : hojaControlListNew) {
                if (!hojaControlListOld.contains(hojaControlListNewHojaControl)) {
                    Solicitud oldFkHcoSolOfHojaControlListNewHojaControl = hojaControlListNewHojaControl.getFkHcoSol();
                    hojaControlListNewHojaControl.setFkHcoSol(solicitud);
                    hojaControlListNewHojaControl = em.merge(hojaControlListNewHojaControl);
                    if (oldFkHcoSolOfHojaControlListNewHojaControl != null && !oldFkHcoSolOfHojaControlListNewHojaControl.equals(solicitud)) {
                        oldFkHcoSolOfHojaControlListNewHojaControl.getHojaControlList().remove(hojaControlListNewHojaControl);
                        oldFkHcoSolOfHojaControlListNewHojaControl = em.merge(oldFkHcoSolOfHojaControlListNewHojaControl);
                    }
                }
            }
            for (Institucion institucionListNewInstitucion : institucionListNew) {
                if (!institucionListOld.contains(institucionListNewInstitucion)) {
                    Solicitud oldFkInsSolOfInstitucionListNewInstitucion = institucionListNewInstitucion.getFkInsSol();
                    institucionListNewInstitucion.setFkInsSol(solicitud);
                    institucionListNewInstitucion = em.merge(institucionListNewInstitucion);
                    if (oldFkInsSolOfInstitucionListNewInstitucion != null && !oldFkInsSolOfInstitucionListNewInstitucion.equals(solicitud)) {
                        oldFkInsSolOfInstitucionListNewInstitucion.getInstitucionList().remove(institucionListNewInstitucion);
                        oldFkInsSolOfInstitucionListNewInstitucion = em.merge(oldFkInsSolOfInstitucionListNewInstitucion);
                    }
                }
            }
            for (HorariosSse horariosSseListNewHorariosSse : horariosSseListNew) {
                if (!horariosSseListOld.contains(horariosSseListNewHorariosSse)) {
                    Solicitud oldFkHssSolOfHorariosSseListNewHorariosSse = horariosSseListNewHorariosSse.getFkHssSol();
                    horariosSseListNewHorariosSse.setFkHssSol(solicitud);
                    horariosSseListNewHorariosSse = em.merge(horariosSseListNewHorariosSse);
                    if (oldFkHssSolOfHorariosSseListNewHorariosSse != null && !oldFkHssSolOfHorariosSseListNewHorariosSse.equals(solicitud)) {
                        oldFkHssSolOfHorariosSseListNewHorariosSse.getHorariosSseList().remove(horariosSseListNewHorariosSse);
                        oldFkHssSolOfHorariosSseListNewHorariosSse = em.merge(oldFkHssSolOfHorariosSseListNewHorariosSse);
                    }
                }
            }
            for (FormularioFinalizacion formularioFinalizacionListNewFormularioFinalizacion : formularioFinalizacionListNew) {
                if (!formularioFinalizacionListOld.contains(formularioFinalizacionListNewFormularioFinalizacion)) {
                    Solicitud oldFkFfiSolOfFormularioFinalizacionListNewFormularioFinalizacion = formularioFinalizacionListNewFormularioFinalizacion.getFkFfiSol();
                    formularioFinalizacionListNewFormularioFinalizacion.setFkFfiSol(solicitud);
                    formularioFinalizacionListNewFormularioFinalizacion = em.merge(formularioFinalizacionListNewFormularioFinalizacion);
                    if (oldFkFfiSolOfFormularioFinalizacionListNewFormularioFinalizacion != null && !oldFkFfiSolOfFormularioFinalizacionListNewFormularioFinalizacion.equals(solicitud)) {
                        oldFkFfiSolOfFormularioFinalizacionListNewFormularioFinalizacion.getFormularioFinalizacionList().remove(formularioFinalizacionListNewFormularioFinalizacion);
                        oldFkFfiSolOfFormularioFinalizacionListNewFormularioFinalizacion = em.merge(oldFkFfiSolOfFormularioFinalizacionListNewFormularioFinalizacion);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = solicitud.getSoId();
                if (findSolicitud(id) == null) {
                    throw new NonexistentEntityException("The solicitud with id " + id + " no longer exists.");
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
            Solicitud solicitud;
            try {
                solicitud = em.getReference(Solicitud.class, id);
                solicitud.getSoId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The solicitud with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Actividad> actividadListOrphanCheck = solicitud.getActividadList();
            for (Actividad actividadListOrphanCheckActividad : actividadListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Solicitud (" + solicitud + ") cannot be destroyed since the Actividad " + actividadListOrphanCheckActividad + " in its actividadList field has a non-nullable fkActSol field.");
            }
            List<HojaControl> hojaControlListOrphanCheck = solicitud.getHojaControlList();
            for (HojaControl hojaControlListOrphanCheckHojaControl : hojaControlListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Solicitud (" + solicitud + ") cannot be destroyed since the HojaControl " + hojaControlListOrphanCheckHojaControl + " in its hojaControlList field has a non-nullable fkHcoSol field.");
            }
            List<Institucion> institucionListOrphanCheck = solicitud.getInstitucionList();
            for (Institucion institucionListOrphanCheckInstitucion : institucionListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Solicitud (" + solicitud + ") cannot be destroyed since the Institucion " + institucionListOrphanCheckInstitucion + " in its institucionList field has a non-nullable fkInsSol field.");
            }
            List<HorariosSse> horariosSseListOrphanCheck = solicitud.getHorariosSseList();
            for (HorariosSse horariosSseListOrphanCheckHorariosSse : horariosSseListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Solicitud (" + solicitud + ") cannot be destroyed since the HorariosSse " + horariosSseListOrphanCheckHorariosSse + " in its horariosSseList field has a non-nullable fkHssSol field.");
            }
            List<FormularioFinalizacion> formularioFinalizacionListOrphanCheck = solicitud.getFormularioFinalizacionList();
            for (FormularioFinalizacion formularioFinalizacionListOrphanCheckFormularioFinalizacion : formularioFinalizacionListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Solicitud (" + solicitud + ") cannot be destroyed since the FormularioFinalizacion " + formularioFinalizacionListOrphanCheckFormularioFinalizacion + " in its formularioFinalizacionList field has a non-nullable fkFfiSol field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Estudiante fkSolEst = solicitud.getFkSolEst();
            if (fkSolEst != null) {
                fkSolEst.getSolicitudList().remove(solicitud);
                fkSolEst = em.merge(fkSolEst);
            }
            em.remove(solicitud);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Solicitud> findSolicitudEntities() {
        return findSolicitudEntities(true, -1, -1);
    }

    public List<Solicitud> findSolicitudEntities(int maxResults, int firstResult) {
        return findSolicitudEntities(false, maxResults, firstResult);
    }

    private List<Solicitud> findSolicitudEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Solicitud.class));
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

    public Solicitud findSolicitud(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Solicitud.class, id);
        } finally {
            em.close();
        }
    }

    public int getSolicitudCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Solicitud> rt = cq.from(Solicitud.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
