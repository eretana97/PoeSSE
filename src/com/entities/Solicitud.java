/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Nataly
 */
@Entity
@Table(name = "solicitud")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Solicitud.findAll", query = "SELECT s FROM Solicitud s"),
    @NamedQuery(name = "Solicitud.findBySoId", query = "SELECT s FROM Solicitud s WHERE s.soId = :soId"),
    @NamedQuery(name = "Solicitud.findBySoFecha", query = "SELECT s FROM Solicitud s WHERE s.soFecha = :soFecha"),
    @NamedQuery(name = "Solicitud.findBySoComentarios", query = "SELECT s FROM Solicitud s WHERE s.soComentarios = :soComentarios"),
    @NamedQuery(name = "Solicitud.findBySoAdjunto", query = "SELECT s FROM Solicitud s WHERE s.soAdjunto = :soAdjunto"),
    @NamedQuery(name = "Solicitud.findBySoObservaciones", query = "SELECT s FROM Solicitud s WHERE s.soObservaciones = :soObservaciones"),
    @NamedQuery(name = "Solicitud.findBySoEstado", query = "SELECT s FROM Solicitud s WHERE s.soEstado = :soEstado")})
public class Solicitud implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "so_id")
    private Integer soId;
    @Basic(optional = false)
    @Column(name = "so_fecha")
    @Temporal(TemporalType.DATE)
    private Date soFecha;
    @Column(name = "so_comentarios")
    private String soComentarios;
    @Column(name = "so_adjunto")
    private String soAdjunto;
    @Column(name = "so_observaciones")
    private String soObservaciones;
    @Basic(optional = false)
    @Column(name = "so_estado")
    private int soEstado;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkActSol")
    private List<Actividad> actividadList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkHcoSol")
    private List<HojaControl> hojaControlList;
    @JoinColumn(name = "fk_sol_est", referencedColumnName = "es_id")
    @ManyToOne(optional = false)
    private Estudiante fkSolEst;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkInsSol")
    private List<Institucion> institucionList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkHssSol")
    private List<HorariosSse> horariosSseList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkFfiSol")
    private List<FormularioFinalizacion> formularioFinalizacionList;

    public Solicitud() {
    }

    public Solicitud(Integer soId) {
        this.soId = soId;
    }

    public Solicitud(Integer soId, Date soFecha, int soEstado) {
        this.soId = soId;
        this.soFecha = soFecha;
        this.soEstado = soEstado;
    }

    public Integer getSoId() {
        return soId;
    }

    public void setSoId(Integer soId) {
        this.soId = soId;
    }

    public Date getSoFecha() {
        return soFecha;
    }

    public void setSoFecha(Date soFecha) {
        this.soFecha = soFecha;
    }

    public String getSoComentarios() {
        return soComentarios;
    }

    public void setSoComentarios(String soComentarios) {
        this.soComentarios = soComentarios;
    }

    public String getSoAdjunto() {
        return soAdjunto;
    }

    public void setSoAdjunto(String soAdjunto) {
        this.soAdjunto = soAdjunto;
    }

    public String getSoObservaciones() {
        return soObservaciones;
    }

    public void setSoObservaciones(String soObservaciones) {
        this.soObservaciones = soObservaciones;
    }

    public int getSoEstado() {
        return soEstado;
    }

    public void setSoEstado(int soEstado) {
        this.soEstado = soEstado;
    }

    @XmlTransient
    public List<Actividad> getActividadList() {
        return actividadList;
    }

    public void setActividadList(List<Actividad> actividadList) {
        this.actividadList = actividadList;
    }

    @XmlTransient
    public List<HojaControl> getHojaControlList() {
        return hojaControlList;
    }

    public void setHojaControlList(List<HojaControl> hojaControlList) {
        this.hojaControlList = hojaControlList;
    }

    public Estudiante getFkSolEst() {
        return fkSolEst;
    }

    public void setFkSolEst(Estudiante fkSolEst) {
        this.fkSolEst = fkSolEst;
    }

    @XmlTransient
    public List<Institucion> getInstitucionList() {
        return institucionList;
    }

    public void setInstitucionList(List<Institucion> institucionList) {
        this.institucionList = institucionList;
    }

    @XmlTransient
    public List<HorariosSse> getHorariosSseList() {
        return horariosSseList;
    }

    public void setHorariosSseList(List<HorariosSse> horariosSseList) {
        this.horariosSseList = horariosSseList;
    }

    @XmlTransient
    public List<FormularioFinalizacion> getFormularioFinalizacionList() {
        return formularioFinalizacionList;
    }

    public void setFormularioFinalizacionList(List<FormularioFinalizacion> formularioFinalizacionList) {
        this.formularioFinalizacionList = formularioFinalizacionList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (soId != null ? soId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Solicitud)) {
            return false;
        }
        Solicitud other = (Solicitud) object;
        if ((this.soId == null && other.soId != null) || (this.soId != null && !this.soId.equals(other.soId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entities.Solicitud[ soId=" + soId + " ]";
    }
    
}
