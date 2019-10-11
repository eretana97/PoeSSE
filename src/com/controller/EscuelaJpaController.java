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
import com.entities.Sede;
import com.entities.Empleado;
import java.util.ArrayList;
import java.util.List;
import com.entities.Carrera;
import com.entities.Escuela;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Nataly
 */
public class EscuelaJpaController implements Serializable {

    public EscuelaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Escuela escuela) {
        if (escuela.getEmpleadoList() == null) {
            escuela.setEmpleadoList(new ArrayList<Empleado>());
        }
        if (escuela.getCarreraList() == null) {
            escuela.setCarreraList(new ArrayList<Carrera>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Sede fkEscSed = escuela.getFkEscSed();
            if (fkEscSed != null) {
                fkEscSed = em.getReference(fkEscSed.getClass(), fkEscSed.getSeId());
                escuela.setFkEscSed(fkEscSed);
            }
            List<Empleado> attachedEmpleadoList = new ArrayList<Empleado>();
            for (Empleado empleadoListEmpleadoToAttach : escuela.getEmpleadoList()) {
                empleadoListEmpleadoToAttach = em.getReference(empleadoListEmpleadoToAttach.getClass(), empleadoListEmpleadoToAttach.getEmId());
                attachedEmpleadoList.add(empleadoListEmpleadoToAttach);
            }
            escuela.setEmpleadoList(attachedEmpleadoList);
            List<Carrera> attachedCarreraList = new ArrayList<Carrera>();
            for (Carrera carreraListCarreraToAttach : escuela.getCarreraList()) {
                carreraListCarreraToAttach = em.getReference(carreraListCarreraToAttach.getClass(), carreraListCarreraToAttach.getCaId());
                attachedCarreraList.add(carreraListCarreraToAttach);
            }
            escuela.setCarreraList(attachedCarreraList);
            em.persist(escuela);
            if (fkEscSed != null) {
                fkEscSed.getEscuelaList().add(escuela);
                fkEscSed = em.merge(fkEscSed);
            }
            for (Empleado empleadoListEmpleado : escuela.getEmpleadoList()) {
                Escuela oldFkEmUsu1OfEmpleadoListEmpleado = empleadoListEmpleado.getFkEmUsu1();
                empleadoListEmpleado.setFkEmUsu1(escuela);
                empleadoListEmpleado = em.merge(empleadoListEmpleado);
                if (oldFkEmUsu1OfEmpleadoListEmpleado != null) {
                    oldFkEmUsu1OfEmpleadoListEmpleado.getEmpleadoList().remove(empleadoListEmpleado);
                    oldFkEmUsu1OfEmpleadoListEmpleado = em.merge(oldFkEmUsu1OfEmpleadoListEmpleado);
                }
            }
            for (Carrera carreraListCarrera : escuela.getCarreraList()) {
                Escuela oldFkCarEscOfCarreraListCarrera = carreraListCarrera.getFkCarEsc();
                carreraListCarrera.setFkCarEsc(escuela);
                carreraListCarrera = em.merge(carreraListCarrera);
                if (oldFkCarEscOfCarreraListCarrera != null) {
                    oldFkCarEscOfCarreraListCarrera.getCarreraList().remove(carreraListCarrera);
                    oldFkCarEscOfCarreraListCarrera = em.merge(oldFkCarEscOfCarreraListCarrera);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Escuela escuela) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Escuela persistentEscuela = em.find(Escuela.class, escuela.getEsId());
            Sede fkEscSedOld = persistentEscuela.getFkEscSed();
            Sede fkEscSedNew = escuela.getFkEscSed();
            List<Empleado> empleadoListOld = persistentEscuela.getEmpleadoList();
            List<Empleado> empleadoListNew = escuela.getEmpleadoList();
            List<Carrera> carreraListOld = persistentEscuela.getCarreraList();
            List<Carrera> carreraListNew = escuela.getCarreraList();
            List<String> illegalOrphanMessages = null;
            for (Empleado empleadoListOldEmpleado : empleadoListOld) {
                if (!empleadoListNew.contains(empleadoListOldEmpleado)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Empleado " + empleadoListOldEmpleado + " since its fkEmUsu1 field is not nullable.");
                }
            }
            for (Carrera carreraListOldCarrera : carreraListOld) {
                if (!carreraListNew.contains(carreraListOldCarrera)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Carrera " + carreraListOldCarrera + " since its fkCarEsc field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (fkEscSedNew != null) {
                fkEscSedNew = em.getReference(fkEscSedNew.getClass(), fkEscSedNew.getSeId());
                escuela.setFkEscSed(fkEscSedNew);
            }
            List<Empleado> attachedEmpleadoListNew = new ArrayList<Empleado>();
            for (Empleado empleadoListNewEmpleadoToAttach : empleadoListNew) {
                empleadoListNewEmpleadoToAttach = em.getReference(empleadoListNewEmpleadoToAttach.getClass(), empleadoListNewEmpleadoToAttach.getEmId());
                attachedEmpleadoListNew.add(empleadoListNewEmpleadoToAttach);
            }
            empleadoListNew = attachedEmpleadoListNew;
            escuela.setEmpleadoList(empleadoListNew);
            List<Carrera> attachedCarreraListNew = new ArrayList<Carrera>();
            for (Carrera carreraListNewCarreraToAttach : carreraListNew) {
                carreraListNewCarreraToAttach = em.getReference(carreraListNewCarreraToAttach.getClass(), carreraListNewCarreraToAttach.getCaId());
                attachedCarreraListNew.add(carreraListNewCarreraToAttach);
            }
            carreraListNew = attachedCarreraListNew;
            escuela.setCarreraList(carreraListNew);
            escuela = em.merge(escuela);
            if (fkEscSedOld != null && !fkEscSedOld.equals(fkEscSedNew)) {
                fkEscSedOld.getEscuelaList().remove(escuela);
                fkEscSedOld = em.merge(fkEscSedOld);
            }
            if (fkEscSedNew != null && !fkEscSedNew.equals(fkEscSedOld)) {
                fkEscSedNew.getEscuelaList().add(escuela);
                fkEscSedNew = em.merge(fkEscSedNew);
            }
            for (Empleado empleadoListNewEmpleado : empleadoListNew) {
                if (!empleadoListOld.contains(empleadoListNewEmpleado)) {
                    Escuela oldFkEmUsu1OfEmpleadoListNewEmpleado = empleadoListNewEmpleado.getFkEmUsu1();
                    empleadoListNewEmpleado.setFkEmUsu1(escuela);
                    empleadoListNewEmpleado = em.merge(empleadoListNewEmpleado);
                    if (oldFkEmUsu1OfEmpleadoListNewEmpleado != null && !oldFkEmUsu1OfEmpleadoListNewEmpleado.equals(escuela)) {
                        oldFkEmUsu1OfEmpleadoListNewEmpleado.getEmpleadoList().remove(empleadoListNewEmpleado);
                        oldFkEmUsu1OfEmpleadoListNewEmpleado = em.merge(oldFkEmUsu1OfEmpleadoListNewEmpleado);
                    }
                }
            }
            for (Carrera carreraListNewCarrera : carreraListNew) {
                if (!carreraListOld.contains(carreraListNewCarrera)) {
                    Escuela oldFkCarEscOfCarreraListNewCarrera = carreraListNewCarrera.getFkCarEsc();
                    carreraListNewCarrera.setFkCarEsc(escuela);
                    carreraListNewCarrera = em.merge(carreraListNewCarrera);
                    if (oldFkCarEscOfCarreraListNewCarrera != null && !oldFkCarEscOfCarreraListNewCarrera.equals(escuela)) {
                        oldFkCarEscOfCarreraListNewCarrera.getCarreraList().remove(carreraListNewCarrera);
                        oldFkCarEscOfCarreraListNewCarrera = em.merge(oldFkCarEscOfCarreraListNewCarrera);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = escuela.getEsId();
                if (findEscuela(id) == null) {
                    throw new NonexistentEntityException("The escuela with id " + id + " no longer exists.");
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
            Escuela escuela;
            try {
                escuela = em.getReference(Escuela.class, id);
                escuela.getEsId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The escuela with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Empleado> empleadoListOrphanCheck = escuela.getEmpleadoList();
            for (Empleado empleadoListOrphanCheckEmpleado : empleadoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Escuela (" + escuela + ") cannot be destroyed since the Empleado " + empleadoListOrphanCheckEmpleado + " in its empleadoList field has a non-nullable fkEmUsu1 field.");
            }
            List<Carrera> carreraListOrphanCheck = escuela.getCarreraList();
            for (Carrera carreraListOrphanCheckCarrera : carreraListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Escuela (" + escuela + ") cannot be destroyed since the Carrera " + carreraListOrphanCheckCarrera + " in its carreraList field has a non-nullable fkCarEsc field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Sede fkEscSed = escuela.getFkEscSed();
            if (fkEscSed != null) {
                fkEscSed.getEscuelaList().remove(escuela);
                fkEscSed = em.merge(fkEscSed);
            }
            em.remove(escuela);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Escuela> findEscuelaEntities() {
        return findEscuelaEntities(true, -1, -1);
    }

    public List<Escuela> findEscuelaEntities(int maxResults, int firstResult) {
        return findEscuelaEntities(false, maxResults, firstResult);
    }

    private List<Escuela> findEscuelaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Escuela.class));
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

    public Escuela findEscuela(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Escuela.class, id);
        } finally {
            em.close();
        }
    }

    public int getEscuelaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Escuela> rt = cq.from(Escuela.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
