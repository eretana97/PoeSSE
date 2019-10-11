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
@Table(name = "hoja_control")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HojaControl.findAll", query = "SELECT h FROM HojaControl h"),
    @NamedQuery(name = "HojaControl.findByHcId", query = "SELECT h FROM HojaControl h WHERE h.hcId = :hcId"),
    @NamedQuery(name = "HojaControl.findByHcFecha", query = "SELECT h FROM HojaControl h WHERE h.hcFecha = :hcFecha"),
    @NamedQuery(name = "HojaControl.findByHcActividad", query = "SELECT h FROM HojaControl h WHERE h.hcActividad = :hcActividad"),
    @NamedQuery(name = "HojaControl.findByHcTotalHoras", query = "SELECT h FROM HojaControl h WHERE h.hcTotalHoras = :hcTotalHoras")})
public class HojaControl implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "hc_id")
    private Integer hcId;
    @Basic(optional = false)
    @Column(name = "hc_fecha")
    @Temporal(TemporalType.DATE)
    private Date hcFecha;
    @Basic(optional = false)
    @Column(name = "hc_actividad")
    private String hcActividad;
    @Basic(optional = false)
    @Column(name = "hc_total_horas")
    private int hcTotalHoras;
    @JoinColumn(name = "fk_hco_sol", referencedColumnName = "so_id")
    @ManyToOne(optional = false)
    private Solicitud fkHcoSol;

    public HojaControl() {
    }

    public HojaControl(Integer hcId) {
        this.hcId = hcId;
    }

    public HojaControl(Integer hcId, Date hcFecha, String hcActividad, int hcTotalHoras) {
        this.hcId = hcId;
        this.hcFecha = hcFecha;
        this.hcActividad = hcActividad;
        this.hcTotalHoras = hcTotalHoras;
    }

    public Integer getHcId() {
        return hcId;
    }

    public void setHcId(Integer hcId) {
        this.hcId = hcId;
    }

    public Date getHcFecha() {
        return hcFecha;
    }

    public void setHcFecha(Date hcFecha) {
        this.hcFecha = hcFecha;
    }

    public String getHcActividad() {
        return hcActividad;
    }

    public void setHcActividad(String hcActividad) {
        this.hcActividad = hcActividad;
    }

    public int getHcTotalHoras() {
        return hcTotalHoras;
    }

    public void setHcTotalHoras(int hcTotalHoras) {
        this.hcTotalHoras = hcTotalHoras;
    }

    public Solicitud getFkHcoSol() {
        return fkHcoSol;
    }

    public void setFkHcoSol(Solicitud fkHcoSol) {
        this.fkHcoSol = fkHcoSol;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (hcId != null ? hcId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HojaControl)) {
            return false;
        }
        HojaControl other = (HojaControl) object;
        if ((this.hcId == null && other.hcId != null) || (this.hcId != null && !this.hcId.equals(other.hcId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entities.HojaControl[ hcId=" + hcId + " ]";
    }
    
}
