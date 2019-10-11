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
import com.entities.Carrera;
import com.entities.TipoCarrera;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Nataly
 */
public class TipoCarreraJpaController implements Serializable {

    public TipoCarreraJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoCarrera tipoCarrera) {
        if (tipoCarrera.getCarreraList() == null) {
            tipoCarrera.setCarreraList(new ArrayList<Carrera>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Carrera> attachedCarreraList = new ArrayList<Carrera>();
            for (Carrera carreraListCarreraToAttach : tipoCarrera.getCarreraList()) {
                carreraListCarreraToAttach = em.getReference(carreraListCarreraToAttach.getClass(), carreraListCarreraToAttach.getCaId());
                attachedCarreraList.add(carreraListCarreraToAttach);
            }
            tipoCarrera.setCarreraList(attachedCarreraList);
            em.persist(tipoCarrera);
            for (Carrera carreraListCarrera : tipoCarrera.getCarreraList()) {
                TipoCarrera oldFkCarTcaOfCarreraListCarrera = carreraListCarrera.getFkCarTca();
                carreraListCarrera.setFkCarTca(tipoCarrera);
                carreraListCarrera = em.merge(carreraListCarrera);
                if (oldFkCarTcaOfCarreraListCarrera != null) {
                    oldFkCarTcaOfCarreraListCarrera.getCarreraList().remove(carreraListCarrera);
                    oldFkCarTcaOfCarreraListCarrera = em.merge(oldFkCarTcaOfCarreraListCarrera);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoCarrera tipoCarrera) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoCarrera persistentTipoCarrera = em.find(TipoCarrera.class, tipoCarrera.getTcId());
            List<Carrera> carreraListOld = persistentTipoCarrera.getCarreraList();
            List<Carrera> carreraListNew = tipoCarrera.getCarreraList();
            List<String> illegalOrphanMessages = null;
            for (Carrera carreraListOldCarrera : carreraListOld) {
                if (!carreraListNew.contains(carreraListOldCarrera)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Carrera " + carreraListOldCarrera + " since its fkCarTca field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Carrera> attachedCarreraListNew = new ArrayList<Carrera>();
            for (Carrera carreraListNewCarreraToAttach : carreraListNew) {
                carreraListNewCarreraToAttach = em.getReference(carreraListNewCarreraToAttach.getClass(), carreraListNewCarreraToAttach.getCaId());
                attachedCarreraListNew.add(carreraListNewCarreraToAttach);
            }
            carreraListNew = attachedCarreraListNew;
            tipoCarrera.setCarreraList(carreraListNew);
            tipoCarrera = em.merge(tipoCarrera);
            for (Carrera carreraListNewCarrera : carreraListNew) {
                if (!carreraListOld.contains(carreraListNewCarrera)) {
                    TipoCarrera oldFkCarTcaOfCarreraListNewCarrera = carreraListNewCarrera.getFkCarTca();
                    carreraListNewCarrera.setFkCarTca(tipoCarrera);
                    carreraListNewCarrera = em.merge(carreraListNewCarrera);
                    if (oldFkCarTcaOfCarreraListNewCarrera != null && !oldFkCarTcaOfCarreraListNewCarrera.equals(tipoCarrera)) {
                        oldFkCarTcaOfCarreraListNewCarrera.getCarreraList().remove(carreraListNewCarrera);
                        oldFkCarTcaOfCarreraListNewCarrera = em.merge(oldFkCarTcaOfCarreraListNewCarrera);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tipoCarrera.getTcId();
                if (findTipoCarrera(id) == null) {
                    throw new NonexistentEntityException("The tipoCarrera with id " + id + " no longer exists.");
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
            TipoCarrera tipoCarrera;
            try {
                tipoCarrera = em.getReference(TipoCarrera.class, id);
                tipoCarrera.getTcId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoCarrera with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Carrera> carreraListOrphanCheck = tipoCarrera.getCarreraList();
            for (Carrera carreraListOrphanCheckCarrera : carreraListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TipoCarrera (" + tipoCarrera + ") cannot be destroyed since the Carrera " + carreraListOrphanCheckCarrera + " in its carreraList field has a non-nullable fkCarTca field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(tipoCarrera);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TipoCarrera> findTipoCarreraEntities() {
        return findTipoCarreraEntities(true, -1, -1);
    }

    public List<TipoCarrera> findTipoCarreraEntities(int maxResults, int firstResult) {
        return findTipoCarreraEntities(false, maxResults, firstResult);
    }

    private List<TipoCarrera> findTipoCarreraEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoCarrera.class));
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

    public TipoCarrera findTipoCarrera(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoCarrera.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoCarreraCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoCarrera> rt = cq.from(TipoCarrera.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
