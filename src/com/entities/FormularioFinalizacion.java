/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Nataly
 */
@Entity
@Table(name = "formulario_finalizacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FormularioFinalizacion.findAll", query = "SELECT f FROM FormularioFinalizacion f"),
    @NamedQuery(name = "FormularioFinalizacion.findByFfId", query = "SELECT f FROM FormularioFinalizacion f WHERE f.ffId = :ffId"),
    @NamedQuery(name = "FormularioFinalizacion.findByFfFechaInicio", query = "SELECT f FROM FormularioFinalizacion f WHERE f.ffFechaInicio = :ffFechaInicio"),
    @NamedQuery(name = "FormularioFinalizacion.findByFfFechaFin", query = "SELECT f FROM FormularioFinalizacion f WHERE f.ffFechaFin = :ffFechaFin"),
    @NamedQuery(name = "FormularioFinalizacion.findByFfObservaciones", query = "SELECT f FROM FormularioFinalizacion f WHERE f.ffObservaciones = :ffObservaciones")})
public class FormularioFinalizacion implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ff_id")
    private Integer ffId;
    @Basic(optional = false)
    @Column(name = "ff_fecha_inicio")
    @Temporal(TemporalType.DATE)
    private Date ffFechaInicio;
    @Basic(optional = false)
    @Column(name = "ff_fecha_fin")
    @Temporal(TemporalType.DATE)
    private Date ffFechaFin;
    @Basic(optional = false)
    @Column(name = "ff_observaciones")
    private String ffObservaciones;
    @JoinColumn(name = "fk_ffi_sol", referencedColumnName = "so_id")
    @ManyToOne(optional = false)
    private Solicitud fkFfiSol;

    public FormularioFinalizacion() {
    }

    public FormularioFinalizacion(Integer ffId) {
        this.ffId = ffId;
    }

    public FormularioFinalizacion(Integer ffId, Date ffFechaInicio, Date ffFechaFin, String ffObservaciones) {
        this.ffId = ffId;
        this.ffFechaInicio = ffFechaInicio;
        this.ffFechaFin = ffFechaFin;
        this.ffObservaciones = ffObservaciones;
    }

    public Integer getFfId() {
        return ffId;
    }

    public void setFfId(Integer ffId) {
        this.ffId = ffId;
    }

    public Date getFfFechaInicio() {
        return ffFechaInicio;
    }

    public void setFfFechaInicio(Date ffFechaInicio) {
        this.ffFechaInicio = ffFechaInicio;
    }

    public Date getFfFechaFin() {
        return ffFechaFin;
    }

    public void setFfFechaFin(Date ffFechaFin) {
        this.ffFechaFin = ffFechaFin;
    }

    public String getFfObservaciones() {
        return ffObservaciones;
    }

    public void setFfObservaciones(String ffObservaciones) {
        this.ffObservaciones = ffObservaciones;
    }

    public Solicitud getFkFfiSol() {
        return fkFfiSol;
    }

    public void setFkFfiSol(Solicitud fkFfiSol) {
        this.fkFfiSol = fkFfiSol;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ffId != null ? ffId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FormularioFinalizacion)) {
            return false;
        }
        FormularioFinalizacion other = (FormularioFinalizacion) object;
        if ((this.ffId == null && other.ffId != null) || (this.ffId != null && !this.ffId.equals(other.ffId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entities.FormularioFinalizacion[ ffId=" + ffId + " ]";
    }
    
}
