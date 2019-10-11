/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller;

import com.controller.exceptions.IllegalOrphanException;
import com.controller.exceptions.NonexistentEntityException;
import com.entities.Carrera;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.entities.TipoCarrera;
import com.entities.Escuela;
import com.entities.Estudiante;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Nataly
 */
public class CarreraJpaController implements Serializable {

    public CarreraJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Carrera carrera) {
        if (carrera.getEstudianteList() == null) {
            carrera.setEstudianteList(new ArrayList<Estudiante>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoCarrera fkCarTca = carrera.getFkCarTca();
            if (fkCarTca != null) {
                fkCarTca = em.getReference(fkCarTca.getClass(), fkCarTca.getTcId());
                carrera.setFkCarTca(fkCarTca);
            }
            Escuela fkCarEsc = carrera.getFkCarEsc();
            if (fkCarEsc != null) {
                fkCarEsc = em.getReference(fkCarEsc.getClass(), fkCarEsc.getEsId());
                carrera.setFkCarEsc(fkCarEsc);
            }
            List<Estudiante> attachedEstudianteList = new ArrayList<Estudiante>();
            for (Estudiante estudianteListEstudianteToAttach : carrera.getEstudianteList()) {
                estudianteListEstudianteToAttach = em.getReference(estudianteListEstudianteToAttach.getClass(), estudianteListEstudianteToAttach.getEsId());
                attachedEstudianteList.add(estudianteListEstudianteToAttach);
            }
            carrera.setEstudianteList(attachedEstudianteList);
            em.persist(carrera);
            if (fkCarTca != null) {
                fkCarTca.getCarreraList().add(carrera);
                fkCarTca = em.merge(fkCarTca);
            }
            if (fkCarEsc != null) {
                fkCarEsc.getCarreraList().add(carrera);
                fkCarEsc = em.merge(fkCarEsc);
            }
            for (Estudiante estudianteListEstudiante : carrera.getEstudianteList()) {
                Carrera oldFkEstCarOfEstudianteListEstudiante = estudianteListEstudiante.getFkEstCar();
                estudianteListEstudiante.setFkEstCar(carrera);
                estudianteListEstudiante = em.merge(estudianteListEstudiante);
                if (oldFkEstCarOfEstudianteListEstudiante != null) {
                    oldFkEstCarOfEstudianteListEstudiante.getEstudianteList().remove(estudianteListEstudiante);
                    oldFkEstCarOfEstudianteListEstudiante = em.merge(oldFkEstCarOfEstudianteListEstudiante);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Carrera carrera) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Carrera persistentCarrera = em.find(Carrera.class, carrera.getCaId());
            TipoCarrera fkCarTcaOld = persistentCarrera.getFkCarTca();
            TipoCarrera fkCarTcaNew = carrera.getFkCarTca();
            Escuela fkCarEscOld = persistentCarrera.getFkCarEsc();
            Escuela fkCarEscNew = carrera.getFkCarEsc();
            List<Estudiante> estudianteListOld = persistentCarrera.getEstudianteList();
            List<Estudiante> estudianteListNew = carrera.getEstudianteList();
            List<String> illegalOrphanMessages = null;
            for (Estudiante estudianteListOldEstudiante : estudianteListOld) {
                if (!estudianteListNew.contains(estudianteListOldEstudiante)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Estudiante " + estudianteListOldEstudiante + " since its fkEstCar field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (fkCarTcaNew != null) {
                fkCarTcaNew = em.getReference(fkCarTcaNew.getClass(), fkCarTcaNew.getTcId());
                carrera.setFkCarTca(fkCarTcaNew);
            }
            if (fkCarEscNew != null) {
                fkCarEscNew = em.getReference(fkCarEscNew.getClass(), fkCarEscNew.getEsId());
                carrera.setFkCarEsc(fkCarEscNew);
            }
            List<Estudiante> attachedEstudianteListNew = new ArrayList<Estudiante>();
            for (Estudiante estudianteListNewEstudianteToAttach : estudianteListNew) {
                estudianteListNewEstudianteToAttach = em.getReference(estudianteListNewEstudianteToAttach.getClass(), estudianteListNewEstudianteToAttach.getEsId());
                attachedEstudianteListNew.add(estudianteListNewEstudianteToAttach);
            }
            estudianteListNew = attachedEstudianteListNew;
            carrera.setEstudianteList(estudianteListNew);
            carrera = em.merge(carrera);
            if (fkCarTcaOld != null && !fkCarTcaOld.equals(fkCarTcaNew)) {
                fkCarTcaOld.getCarreraList().remove(carrera);
                fkCarTcaOld = em.merge(fkCarTcaOld);
            }
            if (fkCarTcaNew != null && !fkCarTcaNew.equals(fkCarTcaOld)) {
                fkCarTcaNew.getCarreraList().add(carrera);
                fkCarTcaNew = em.merge(fkCarTcaNew);
            }
            if (fkCarEscOld != null && !fkCarEscOld.equals(fkCarEscNew)) {
                fkCarEscOld.getCarreraList().remove(carrera);
                fkCarEscOld = em.merge(fkCarEscOld);
            }
            if (fkCarEscNew != null && !fkCarEscNew.equals(fkCarEscOld)) {
                fkCarEscNew.getCarreraList().add(carrera);
                fkCarEscNew = em.merge(fkCarEscNew);
            }
            for (Estudiante estudianteListNewEstudiante : estudianteListNew) {
                if (!estudianteListOld.contains(estudianteListNewEstudiante)) {
                    Carrera oldFkEstCarOfEstudianteListNewEstudiante = estudianteListNewEstudiante.getFkEstCar();
                    estudianteListNewEstudiante.setFkEstCar(carrera);
                    estudianteListNewEstudiante = em.merge(estudianteListNewEstudiante);
                    if (oldFkEstCarOfEstudianteListNewEstudiante != null && !oldFkEstCarOfEstudianteListNewEstudiante.equals(carrera)) {
                        oldFkEstCarOfEstudianteListNewEstudiante.getEstudianteList().remove(estudianteListNewEstudiante);
                        oldFkEstCarOfEstudianteListNewEstudiante = em.merge(oldFkEstCarOfEstudianteListNewEstudiante);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = carrera.getCaId();
                if (findCarrera(id) == null) {
                    throw new NonexistentEntityException("The carrera with id " + id + " no longer exists.");
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
            Carrera carrera;
            try {
                carrera = em.getReference(Carrera.class, id);
                carrera.getCaId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The carrera with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Estudiante> estudianteListOrphanCheck = carrera.getEstudianteList();
            for (Estudiante estudianteListOrphanCheckEstudiante : estudianteListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Carrera (" + carrera + ") cannot be destroyed since the Estudiante " + estudianteListOrphanCheckEstudiante + " in its estudianteList field has a non-nullable fkEstCar field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            TipoCarrera fkCarTca = carrera.getFkCarTca();
            if (fkCarTca != null) {
                fkCarTca.getCarreraList().remove(carrera);
                fkCarTca = em.merge(fkCarTca);
            }
            Escuela fkCarEsc = carrera.getFkCarEsc();
            if (fkCarEsc != null) {
                fkCarEsc.getCarreraList().remove(carrera);
                fkCarEsc = em.merge(fkCarEsc);
            }
            em.remove(carrera);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Carrera> findCarreraEntities() {
        return findCarreraEntities(true, -1, -1);
    }

    public List<Carrera> findCarreraEntities(int maxResults, int firstResult) {
        return findCarreraEntities(false, maxResults, firstResult);
    }

    private List<Carrera> findCarreraEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Carrera.class));
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

    public Carrera findCarrera(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Carrera.class, id);
        } finally {
            em.close();
        }
    }

    public int getCarreraCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Carrera> rt = cq.from(Carrera.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
