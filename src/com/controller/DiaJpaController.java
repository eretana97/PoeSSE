/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller;

import com.controller.exceptions.IllegalOrphanException;
import com.controller.exceptions.NonexistentEntityException;
import com.entities.Dia;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.entities.HorarioAtencion;
import java.util.ArrayList;
import java.util.List;
import com.entities.HorariosSse;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Nataly
 */
public class DiaJpaController implements Serializable {

    public DiaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Dia dia) {
        if (dia.getHorarioAtencionList() == null) {
            dia.setHorarioAtencionList(new ArrayList<HorarioAtencion>());
        }
        if (dia.getHorariosSseList() == null) {
            dia.setHorariosSseList(new ArrayList<HorariosSse>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<HorarioAtencion> attachedHorarioAtencionList = new ArrayList<HorarioAtencion>();
            for (HorarioAtencion horarioAtencionListHorarioAtencionToAttach : dia.getHorarioAtencionList()) {
                horarioAtencionListHorarioAtencionToAttach = em.getReference(horarioAtencionListHorarioAtencionToAttach.getClass(), horarioAtencionListHorarioAtencionToAttach.getHaId());
                attachedHorarioAtencionList.add(horarioAtencionListHorarioAtencionToAttach);
            }
            dia.setHorarioAtencionList(attachedHorarioAtencionList);
            List<HorariosSse> attachedHorariosSseList = new ArrayList<HorariosSse>();
            for (HorariosSse horariosSseListHorariosSseToAttach : dia.getHorariosSseList()) {
                horariosSseListHorariosSseToAttach = em.getReference(horariosSseListHorariosSseToAttach.getClass(), horariosSseListHorariosSseToAttach.getHsId());
                attachedHorariosSseList.add(horariosSseListHorariosSseToAttach);
            }
            dia.setHorariosSseList(attachedHorariosSseList);
            em.persist(dia);
            for (HorarioAtencion horarioAtencionListHorarioAtencion : dia.getHorarioAtencionList()) {
                Dia oldFkHatDiaOfHorarioAtencionListHorarioAtencion = horarioAtencionListHorarioAtencion.getFkHatDia();
                horarioAtencionListHorarioAtencion.setFkHatDia(dia);
                horarioAtencionListHorarioAtencion = em.merge(horarioAtencionListHorarioAtencion);
                if (oldFkHatDiaOfHorarioAtencionListHorarioAtencion != null) {
                    oldFkHatDiaOfHorarioAtencionListHorarioAtencion.getHorarioAtencionList().remove(horarioAtencionListHorarioAtencion);
                    oldFkHatDiaOfHorarioAtencionListHorarioAtencion = em.merge(oldFkHatDiaOfHorarioAtencionListHorarioAtencion);
                }
            }
            for (HorariosSse horariosSseListHorariosSse : dia.getHorariosSseList()) {
                Dia oldFkHssDiaOfHorariosSseListHorariosSse = horariosSseListHorariosSse.getFkHssDia();
                horariosSseListHorariosSse.setFkHssDia(dia);
                horariosSseListHorariosSse = em.merge(horariosSseListHorariosSse);
                if (oldFkHssDiaOfHorariosSseListHorariosSse != null) {
                    oldFkHssDiaOfHorariosSseListHorariosSse.getHorariosSseList().remove(horariosSseListHorariosSse);
                    oldFkHssDiaOfHorariosSseListHorariosSse = em.merge(oldFkHssDiaOfHorariosSseListHorariosSse);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Dia dia) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Dia persistentDia = em.find(Dia.class, dia.getDiId());
            List<HorarioAtencion> horarioAtencionListOld = persistentDia.getHorarioAtencionList();
            List<HorarioAtencion> horarioAtencionListNew = dia.getHorarioAtencionList();
            List<HorariosSse> horariosSseListOld = persistentDia.getHorariosSseList();
            List<HorariosSse> horariosSseListNew = dia.getHorariosSseList();
            List<String> illegalOrphanMessages = null;
            for (HorarioAtencion horarioAtencionListOldHorarioAtencion : horarioAtencionListOld) {
                if (!horarioAtencionListNew.contains(horarioAtencionListOldHorarioAtencion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain HorarioAtencion " + horarioAtencionListOldHorarioAtencion + " since its fkHatDia field is not nullable.");
                }
            }
            for (HorariosSse horariosSseListOldHorariosSse : horariosSseListOld) {
                if (!horariosSseListNew.contains(horariosSseListOldHorariosSse)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain HorariosSse " + horariosSseListOldHorariosSse + " since its fkHssDia field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<HorarioAtencion> attachedHorarioAtencionListNew = new ArrayList<HorarioAtencion>();
            for (HorarioAtencion horarioAtencionListNewHorarioAtencionToAttach : horarioAtencionListNew) {
                horarioAtencionListNewHorarioAtencionToAttach = em.getReference(horarioAtencionListNewHorarioAtencionToAttach.getClass(), horarioAtencionListNewHorarioAtencionToAttach.getHaId());
                attachedHorarioAtencionListNew.add(horarioAtencionListNewHorarioAtencionToAttach);
            }
            horarioAtencionListNew = attachedHorarioAtencionListNew;
            dia.setHorarioAtencionList(horarioAtencionListNew);
            List<HorariosSse> attachedHorariosSseListNew = new ArrayList<HorariosSse>();
            for (HorariosSse horariosSseListNewHorariosSseToAttach : horariosSseListNew) {
                horariosSseListNewHorariosSseToAttach = em.getReference(horariosSseListNewHorariosSseToAttach.getClass(), horariosSseListNewHorariosSseToAttach.getHsId());
                attachedHorariosSseListNew.add(horariosSseListNewHorariosSseToAttach);
            }
            horariosSseListNew = attachedHorariosSseListNew;
            dia.setHorariosSseList(horariosSseListNew);
            dia = em.merge(dia);
            for (HorarioAtencion horarioAtencionListNewHorarioAtencion : horarioAtencionListNew) {
                if (!horarioAtencionListOld.contains(horarioAtencionListNewHorarioAtencion)) {
                    Dia oldFkHatDiaOfHorarioAtencionListNewHorarioAtencion = horarioAtencionListNewHorarioAtencion.getFkHatDia();
                    horarioAtencionListNewHorarioAtencion.setFkHatDia(dia);
                    horarioAtencionListNewHorarioAtencion = em.merge(horarioAtencionListNewHorarioAtencion);
                    if (oldFkHatDiaOfHorarioAtencionListNewHorarioAtencion != null && !oldFkHatDiaOfHorarioAtencionListNewHorarioAtencion.equals(dia)) {
                        oldFkHatDiaOfHorarioAtencionListNewHorarioAtencion.getHorarioAtencionList().remove(horarioAtencionListNewHorarioAtencion);
                        oldFkHatDiaOfHorarioAtencionListNewHorarioAtencion = em.merge(oldFkHatDiaOfHorarioAtencionListNewHorarioAtencion);
                    }
                }
            }
            for (HorariosSse horariosSseListNewHorariosSse : horariosSseListNew) {
                if (!horariosSseListOld.contains(horariosSseListNewHorariosSse)) {
                    Dia oldFkHssDiaOfHorariosSseListNewHorariosSse = horariosSseListNewHorariosSse.getFkHssDia();
                    horariosSseListNewHorariosSse.setFkHssDia(dia);
                    horariosSseListNewHorariosSse = em.merge(horariosSseListNewHorariosSse);
                    if (oldFkHssDiaOfHorariosSseListNewHorariosSse != null && !oldFkHssDiaOfHorariosSseListNewHorariosSse.equals(dia)) {
                        oldFkHssDiaOfHorariosSseListNewHorariosSse.getHorariosSseList().remove(horariosSseListNewHorariosSse);
                        oldFkHssDiaOfHorariosSseListNewHorariosSse = em.merge(oldFkHssDiaOfHorariosSseListNewHorariosSse);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = dia.getDiId();
                if (findDia(id) == null) {
                    throw new NonexistentEntityException("The dia with id " + id + " no longer exists.");
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
            Dia dia;
            try {
                dia = em.getReference(Dia.class, id);
                dia.getDiId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The dia with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<HorarioAtencion> horarioAtencionListOrphanCheck = dia.getHorarioAtencionList();
            for (HorarioAtencion horarioAtencionListOrphanCheckHorarioAtencion : horarioAtencionListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Dia (" + dia + ") cannot be destroyed since the HorarioAtencion " + horarioAtencionListOrphanCheckHorarioAtencion + " in its horarioAtencionList field has a non-nullable fkHatDia field.");
            }
            List<HorariosSse> horariosSseListOrphanCheck = dia.getHorariosSseList();
            for (HorariosSse horariosSseListOrphanCheckHorariosSse : horariosSseListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Dia (" + dia + ") cannot be destroyed since the HorariosSse " + horariosSseListOrphanCheckHorariosSse + " in its horariosSseList field has a non-nullable fkHssDia field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(dia);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Dia> findDiaEntities() {
        return findDiaEntities(true, -1, -1);
    }

    public List<Dia> findDiaEntities(int maxResults, int firstResult) {
        return findDiaEntities(false, maxResults, firstResult);
    }

    private List<Dia> findDiaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Dia.class));
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

    public Dia findDia(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Dia.class, id);
        } finally {
            em.close();
        }
    }

    public int getDiaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Dia> rt = cq.from(Dia.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
