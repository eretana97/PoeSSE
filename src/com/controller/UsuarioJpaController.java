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
import com.entities.TipoUsuario;
import com.entities.Empleado;
import java.util.ArrayList;
import java.util.List;
import com.entities.Estudiante;
import com.entities.Usuario;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Nataly
 */
public class UsuarioJpaController implements Serializable {

    public UsuarioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Usuario usuario) {
        if (usuario.getEmpleadoList() == null) {
            usuario.setEmpleadoList(new ArrayList<Empleado>());
        }
        if (usuario.getEstudianteList() == null) {
            usuario.setEstudianteList(new ArrayList<Estudiante>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoUsuario fkUsuTip = usuario.getFkUsuTip();
            if (fkUsuTip != null) {
                fkUsuTip = em.getReference(fkUsuTip.getClass(), fkUsuTip.getTiId());
                usuario.setFkUsuTip(fkUsuTip);
            }
            List<Empleado> attachedEmpleadoList = new ArrayList<Empleado>();
            for (Empleado empleadoListEmpleadoToAttach : usuario.getEmpleadoList()) {
                empleadoListEmpleadoToAttach = em.getReference(empleadoListEmpleadoToAttach.getClass(), empleadoListEmpleadoToAttach.getEmId());
                attachedEmpleadoList.add(empleadoListEmpleadoToAttach);
            }
            usuario.setEmpleadoList(attachedEmpleadoList);
            List<Estudiante> attachedEstudianteList = new ArrayList<Estudiante>();
            for (Estudiante estudianteListEstudianteToAttach : usuario.getEstudianteList()) {
                estudianteListEstudianteToAttach = em.getReference(estudianteListEstudianteToAttach.getClass(), estudianteListEstudianteToAttach.getEsId());
                attachedEstudianteList.add(estudianteListEstudianteToAttach);
            }
            usuario.setEstudianteList(attachedEstudianteList);
            em.persist(usuario);
            if (fkUsuTip != null) {
                fkUsuTip.getUsuarioList().add(usuario);
                fkUsuTip = em.merge(fkUsuTip);
            }
            for (Empleado empleadoListEmpleado : usuario.getEmpleadoList()) {
                Usuario oldFkEmUsuOfEmpleadoListEmpleado = empleadoListEmpleado.getFkEmUsu();
                empleadoListEmpleado.setFkEmUsu(usuario);
                empleadoListEmpleado = em.merge(empleadoListEmpleado);
                if (oldFkEmUsuOfEmpleadoListEmpleado != null) {
                    oldFkEmUsuOfEmpleadoListEmpleado.getEmpleadoList().remove(empleadoListEmpleado);
                    oldFkEmUsuOfEmpleadoListEmpleado = em.merge(oldFkEmUsuOfEmpleadoListEmpleado);
                }
            }
            for (Estudiante estudianteListEstudiante : usuario.getEstudianteList()) {
                Usuario oldFkEstUsuOfEstudianteListEstudiante = estudianteListEstudiante.getFkEstUsu();
                estudianteListEstudiante.setFkEstUsu(usuario);
                estudianteListEstudiante = em.merge(estudianteListEstudiante);
                if (oldFkEstUsuOfEstudianteListEstudiante != null) {
                    oldFkEstUsuOfEstudianteListEstudiante.getEstudianteList().remove(estudianteListEstudiante);
                    oldFkEstUsuOfEstudianteListEstudiante = em.merge(oldFkEstUsuOfEstudianteListEstudiante);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Usuario usuario) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario persistentUsuario = em.find(Usuario.class, usuario.getUsId());
            TipoUsuario fkUsuTipOld = persistentUsuario.getFkUsuTip();
            TipoUsuario fkUsuTipNew = usuario.getFkUsuTip();
            List<Empleado> empleadoListOld = persistentUsuario.getEmpleadoList();
            List<Empleado> empleadoListNew = usuario.getEmpleadoList();
            List<Estudiante> estudianteListOld = persistentUsuario.getEstudianteList();
            List<Estudiante> estudianteListNew = usuario.getEstudianteList();
            List<String> illegalOrphanMessages = null;
            for (Empleado empleadoListOldEmpleado : empleadoListOld) {
                if (!empleadoListNew.contains(empleadoListOldEmpleado)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Empleado " + empleadoListOldEmpleado + " since its fkEmUsu field is not nullable.");
                }
            }
            for (Estudiante estudianteListOldEstudiante : estudianteListOld) {
                if (!estudianteListNew.contains(estudianteListOldEstudiante)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Estudiante " + estudianteListOldEstudiante + " since its fkEstUsu field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (fkUsuTipNew != null) {
                fkUsuTipNew = em.getReference(fkUsuTipNew.getClass(), fkUsuTipNew.getTiId());
                usuario.setFkUsuTip(fkUsuTipNew);
            }
            List<Empleado> attachedEmpleadoListNew = new ArrayList<Empleado>();
            for (Empleado empleadoListNewEmpleadoToAttach : empleadoListNew) {
                empleadoListNewEmpleadoToAttach = em.getReference(empleadoListNewEmpleadoToAttach.getClass(), empleadoListNewEmpleadoToAttach.getEmId());
                attachedEmpleadoListNew.add(empleadoListNewEmpleadoToAttach);
            }
            empleadoListNew = attachedEmpleadoListNew;
            usuario.setEmpleadoList(empleadoListNew);
            List<Estudiante> attachedEstudianteListNew = new ArrayList<Estudiante>();
            for (Estudiante estudianteListNewEstudianteToAttach : estudianteListNew) {
                estudianteListNewEstudianteToAttach = em.getReference(estudianteListNewEstudianteToAttach.getClass(), estudianteListNewEstudianteToAttach.getEsId());
                attachedEstudianteListNew.add(estudianteListNewEstudianteToAttach);
            }
            estudianteListNew = attachedEstudianteListNew;
            usuario.setEstudianteList(estudianteListNew);
            usuario = em.merge(usuario);
            if (fkUsuTipOld != null && !fkUsuTipOld.equals(fkUsuTipNew)) {
                fkUsuTipOld.getUsuarioList().remove(usuario);
                fkUsuTipOld = em.merge(fkUsuTipOld);
            }
            if (fkUsuTipNew != null && !fkUsuTipNew.equals(fkUsuTipOld)) {
                fkUsuTipNew.getUsuarioList().add(usuario);
                fkUsuTipNew = em.merge(fkUsuTipNew);
            }
            for (Empleado empleadoListNewEmpleado : empleadoListNew) {
                if (!empleadoListOld.contains(empleadoListNewEmpleado)) {
                    Usuario oldFkEmUsuOfEmpleadoListNewEmpleado = empleadoListNewEmpleado.getFkEmUsu();
                    empleadoListNewEmpleado.setFkEmUsu(usuario);
                    empleadoListNewEmpleado = em.merge(empleadoListNewEmpleado);
                    if (oldFkEmUsuOfEmpleadoListNewEmpleado != null && !oldFkEmUsuOfEmpleadoListNewEmpleado.equals(usuario)) {
                        oldFkEmUsuOfEmpleadoListNewEmpleado.getEmpleadoList().remove(empleadoListNewEmpleado);
                        oldFkEmUsuOfEmpleadoListNewEmpleado = em.merge(oldFkEmUsuOfEmpleadoListNewEmpleado);
                    }
                }
            }
            for (Estudiante estudianteListNewEstudiante : estudianteListNew) {
                if (!estudianteListOld.contains(estudianteListNewEstudiante)) {
                    Usuario oldFkEstUsuOfEstudianteListNewEstudiante = estudianteListNewEstudiante.getFkEstUsu();
                    estudianteListNewEstudiante.setFkEstUsu(usuario);
                    estudianteListNewEstudiante = em.merge(estudianteListNewEstudiante);
                    if (oldFkEstUsuOfEstudianteListNewEstudiante != null && !oldFkEstUsuOfEstudianteListNewEstudiante.equals(usuario)) {
                        oldFkEstUsuOfEstudianteListNewEstudiante.getEstudianteList().remove(estudianteListNewEstudiante);
                        oldFkEstUsuOfEstudianteListNewEstudiante = em.merge(oldFkEstUsuOfEstudianteListNewEstudiante);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = usuario.getUsId();
                if (findUsuario(id) == null) {
                    throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.");
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
            Usuario usuario;
            try {
                usuario = em.getReference(Usuario.class, id);
                usuario.getUsId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Empleado> empleadoListOrphanCheck = usuario.getEmpleadoList();
            for (Empleado empleadoListOrphanCheckEmpleado : empleadoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the Empleado " + empleadoListOrphanCheckEmpleado + " in its empleadoList field has a non-nullable fkEmUsu field.");
            }
            List<Estudiante> estudianteListOrphanCheck = usuario.getEstudianteList();
            for (Estudiante estudianteListOrphanCheckEstudiante : estudianteListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the Estudiante " + estudianteListOrphanCheckEstudiante + " in its estudianteList field has a non-nullable fkEstUsu field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            TipoUsuario fkUsuTip = usuario.getFkUsuTip();
            if (fkUsuTip != null) {
                fkUsuTip.getUsuarioList().remove(usuario);
                fkUsuTip = em.merge(fkUsuTip);
            }
            em.remove(usuario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Usuario> findUsuarioEntities() {
        return findUsuarioEntities(true, -1, -1);
    }

    public List<Usuario> findUsuarioEntities(int maxResults, int firstResult) {
        return findUsuarioEntities(false, maxResults, firstResult);
    }

    private List<Usuario> findUsuarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Usuario.class));
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

    public Usuario findUsuario(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuario.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Usuario> rt = cq.from(Usuario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
