/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entities;

import java.io.Serializable;
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Nataly
 */
@Entity
@Table(name = "actividad")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Actividad.findAll", query = "SELECT a FROM Actividad a"),
    @NamedQuery(name = "Actividad.findByAcId", query = "SELECT a FROM Actividad a WHERE a.acId = :acId"),
    @NamedQuery(name = "Actividad.findByAcActividad", query = "SELECT a FROM Actividad a WHERE a.acActividad = :acActividad"),
    @NamedQuery(name = "Actividad.findByAcObjectivos", query = "SELECT a FROM Actividad a WHERE a.acObjectivos = :acObjectivos"),
    @NamedQuery(name = "Actividad.findByAcMetas", query = "SELECT a FROM Actividad a WHERE a.acMetas = :acMetas"),
    @NamedQuery(name = "Actividad.findByAcDuracion", query = "SELECT a FROM Actividad a WHERE a.acDuracion = :acDuracion")})
public class Actividad implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ac_id")
    private Integer acId;
    @Basic(optional = false)
    @Column(name = "ac_actividad")
    private String acActividad;
    @Basic(optional = false)
    @Column(name = "ac_objectivos")
    private String acObjectivos;
    @Basic(optional = false)
    @Column(name = "ac_metas")
    private String acMetas;
    @Basic(optional = false)
    @Column(name = "ac_duracion")
    private String acDuracion;
    @JoinColumn(name = "fk_act_sol", referencedColumnName = "so_id")
    @ManyToOne(optional = false)
    private Solicitud fkActSol;

    public Actividad() {
    }

    public Actividad(Integer acId) {
        this.acId = acId;
    }

    public Actividad(Integer acId, String acActividad, String acObjectivos, String acMetas, String acDuracion) {
        this.acId = acId;
        this.acActividad = acActividad;
        this.acObjectivos = acObjectivos;
        this.acMetas = acMetas;
        this.acDuracion = acDuracion;
    }

    public Integer getAcId() {
        return acId;
    }

    public void setAcId(Integer acId) {
        this.acId = acId;
    }

    public String getAcActividad() {
        return acActividad;
    }

    public void setAcActividad(String acActividad) {
        this.acActividad = acActividad;
    }

    public String getAcObjectivos() {
        return acObjectivos;
    }

    public void setAcObjectivos(String acObjectivos) {
        this.acObjectivos = acObjectivos;
    }

    public String getAcMetas() {
        return acMetas;
    }

    public void setAcMetas(String acMetas) {
        this.acMetas = acMetas;
    }

    public String getAcDuracion() {
        return acDuracion;
    }

    public void setAcDuracion(String acDuracion) {
        this.acDuracion = acDuracion;
    }

    public Solicitud getFkActSol() {
        return fkActSol;
    }

    public void setFkActSol(Solicitud fkActSol) {
        this.fkActSol = fkActSol;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (acId != null ? acId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Actividad)) {
            return false;
        }
        Actividad other = (Actividad) object;
        if ((this.acId == null && other.acId != null) || (this.acId != null && !this.acId.equals(other.acId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entities.Actividad[ acId=" + acId + " ]";
    }
    
}
