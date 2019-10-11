/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller;

import com.controller.exceptions.IllegalOrphanException;
import com.controller.exceptions.NonexistentEntityException;
import com.entities.Empleado;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.entities.Usuario;
import com.entities.Escuela;
import com.entities.HorarioAtencion;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Nataly
 */
public class EmpleadoJpaController implements Serializable {

    public EmpleadoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Empleado empleado) {
        if (empleado.getHorarioAtencionList() == null) {
            empleado.setHorarioAtencionList(new ArrayList<HorarioAtencion>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario fkEmUsu = empleado.getFkEmUsu();
            if (fkEmUsu != null) {
                fkEmUsu = em.getReference(fkEmUsu.getClass(), fkEmUsu.getUsId());
                empleado.setFkEmUsu(fkEmUsu);
            }
            Escuela fkEmUsu1 = empleado.getFkEmUsu1();
            if (fkEmUsu1 != null) {
                fkEmUsu1 = em.getReference(fkEmUsu1.getClass(), fkEmUsu1.getEsId());
                empleado.setFkEmUsu1(fkEmUsu1);
            }
            List<HorarioAtencion> attachedHorarioAtencionList = new ArrayList<HorarioAtencion>();
            for (HorarioAtencion horarioAtencionListHorarioAtencionToAttach : empleado.getHorarioAtencionList()) {
                horarioAtencionListHorarioAtencionToAttach = em.getReference(horarioAtencionListHorarioAtencionToAttach.getClass(), horarioAtencionListHorarioAtencionToAttach.getHaId());
                attachedHorarioAtencionList.add(horarioAtencionListHorarioAtencionToAttach);
            }
            empleado.setHorarioAtencionList(attachedHorarioAtencionList);
            em.persist(empleado);
            if (fkEmUsu != null) {
                fkEmUsu.getEmpleadoList().add(empleado);
                fkEmUsu = em.merge(fkEmUsu);
            }
            if (fkEmUsu1 != null) {
                fkEmUsu1.getEmpleadoList().add(empleado);
                fkEmUsu1 = em.merge(fkEmUsu1);
            }
            for (HorarioAtencion horarioAtencionListHorarioAtencion : empleado.getHorarioAtencionList()) {
                Empleado oldFkHatEmOfHorarioAtencionListHorarioAtencion = horarioAtencionListHorarioAtencion.getFkHatEm();
                horarioAtencionListHorarioAtencion.setFkHatEm(empleado);
                horarioAtencionListHorarioAtencion = em.merge(horarioAtencionListHorarioAtencion);
                if (oldFkHatEmOfHorarioAtencionListHorarioAtencion != null) {
                    oldFkHatEmOfHorarioAtencionListHorarioAtencion.getHorarioAtencionList().remove(horarioAtencionListHorarioAtencion);
                    oldFkHatEmOfHorarioAtencionListHorarioAtencion = em.merge(oldFkHatEmOfHorarioAtencionListHorarioAtencion);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Empleado empleado) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Empleado persistentEmpleado = em.find(Empleado.class, empleado.getEmId());
            Usuario fkEmUsuOld = persistentEmpleado.getFkEmUsu();
            Usuario fkEmUsuNew = empleado.getFkEmUsu();
            Escuela fkEmUsu1Old = persistentEmpleado.getFkEmUsu1();
            Escuela fkEmUsu1New = empleado.getFkEmUsu1();
            List<HorarioAtencion> horarioAtencionListOld = persistentEmpleado.getHorarioAtencionList();
            List<HorarioAtencion> horarioAtencionListNew = empleado.getHorarioAtencionList();
            List<String> illegalOrphanMessages = null;
            for (HorarioAtencion horarioAtencionListOldHorarioAtencion : horarioAtencionListOld) {
                if (!horarioAtencionListNew.contains(horarioAtencionListOldHorarioAtencion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain HorarioAtencion " + horarioAtencionListOldHorarioAtencion + " since its fkHatEm field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (fkEmUsuNew != null) {
                fkEmUsuNew = em.getReference(fkEmUsuNew.getClass(), fkEmUsuNew.getUsId());
                empleado.setFkEmUsu(fkEmUsuNew);
            }
            if (fkEmUsu1New != null) {
                fkEmUsu1New = em.getReference(fkEmUsu1New.getClass(), fkEmUsu1New.getEsId());
                empleado.setFkEmUsu1(fkEmUsu1New);
            }
            List<HorarioAtencion> attachedHorarioAtencionListNew = new ArrayList<HorarioAtencion>();
            for (HorarioAtencion horarioAtencionListNewHorarioAtencionToAttach : horarioAtencionListNew) {
                horarioAtencionListNewHorarioAtencionToAttach = em.getReference(horarioAtencionListNewHorarioAtencionToAttach.getClass(), horarioAtencionListNewHorarioAtencionToAttach.getHaId());
                attachedHorarioAtencionListNew.add(horarioAtencionListNewHorarioAtencionToAttach);
            }
            horarioAtencionListNew = attachedHorarioAtencionListNew;
            empleado.setHorarioAtencionList(horarioAtencionListNew);
            empleado = em.merge(empleado);
            if (fkEmUsuOld != null && !fkEmUsuOld.equals(fkEmUsuNew)) {
                fkEmUsuOld.getEmpleadoList().remove(empleado);
                fkEmUsuOld = em.merge(fkEmUsuOld);
            }
            if (fkEmUsuNew != null && !fkEmUsuNew.equals(fkEmUsuOld)) {
                fkEmUsuNew.getEmpleadoList().add(empleado);
                fkEmUsuNew = em.merge(fkEmUsuNew);
            }
            if (fkEmUsu1Old != null && !fkEmUsu1Old.equals(fkEmUsu1New)) {
                fkEmUsu1Old.getEmpleadoList().remove(empleado);
                fkEmUsu1Old = em.merge(fkEmUsu1Old);
            }
            if (fkEmUsu1New != null && !fkEmUsu1New.equals(fkEmUsu1Old)) {
                fkEmUsu1New.getEmpleadoList().add(empleado);
                fkEmUsu1New = em.merge(fkEmUsu1New);
            }
            for (HorarioAtencion horarioAtencionListNewHorarioAtencion : horarioAtencionListNew) {
                if (!horarioAtencionListOld.contains(horarioAtencionListNewHorarioAtencion)) {
                    Empleado oldFkHatEmOfHorarioAtencionListNewHorarioAtencion = horarioAtencionListNewHorarioAtencion.getFkHatEm();
                    horarioAtencionListNewHorarioAtencion.setFkHatEm(empleado);
                    horarioAtencionListNewHorarioAtencion = em.merge(horarioAtencionListNewHorarioAtencion);
                    if (oldFkHatEmOfHorarioAtencionListNewHorarioAtencion != null && !oldFkHatEmOfHorarioAtencionListNewHorarioAtencion.equals(empleado)) {
                        oldFkHatEmOfHorarioAtencionListNewHorarioAtencion.getHorarioAtencionList().remove(horarioAtencionListNewHorarioAtencion);
                        oldFkHatEmOfHorarioAtencionListNewHorarioAtencion = em.merge(oldFkHatEmOfHorarioAtencionListNewHorarioAtencion);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = empleado.getEmId();
                if (findEmpleado(id) == null) {
                    throw new NonexistentEntityException("The empleado with id " + id + " no longer exists.");
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
            Empleado empleado;
            try {
                empleado = em.getReference(Empleado.class, id);
                empleado.getEmId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The empleado with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<HorarioAtencion> horarioAtencionListOrphanCheck = empleado.getHorarioAtencionList();
            for (HorarioAtencion horarioAtencionListOrphanCheckHorarioAtencion : horarioAtencionListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Empleado (" + empleado + ") cannot be destroyed since the HorarioAtencion " + horarioAtencionListOrphanCheckHorarioAtencion + " in its horarioAtencionList field has a non-nullable fkHatEm field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Usuario fkEmUsu = empleado.getFkEmUsu();
            if (fkEmUsu != null) {
                fkEmUsu.getEmpleadoList().remove(empleado);
                fkEmUsu = em.merge(fkEmUsu);
            }
            Escuela fkEmUsu1 = empleado.getFkEmUsu1();
            if (fkEmUsu1 != null) {
                fkEmUsu1.getEmpleadoList().remove(empleado);
                fkEmUsu1 = em.merge(fkEmUsu1);
            }
            em.remove(empleado);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Empleado> findEmpleadoEntities() {
        return findEmpleadoEntities(true, -1, -1);
    }

    public List<Empleado> findEmpleadoEntities(int maxResults, int firstResult) {
        return findEmpleadoEntities(false, maxResults, firstResult);
    }

    private List<Empleado> findEmpleadoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Empleado.class));
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

    public Empleado findEmpleado(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Empleado.class, id);
        } finally {
            em.close();
        }
    }

    public int getEmpleadoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Empleado> rt = cq.from(Empleado.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
