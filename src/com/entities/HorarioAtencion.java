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
@Table(name = "horario_atencion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HorarioAtencion.findAll", query = "SELECT h FROM HorarioAtencion h"),
    @NamedQuery(name = "HorarioAtencion.findByHaId", query = "SELECT h FROM HorarioAtencion h WHERE h.haId = :haId"),
    @NamedQuery(name = "HorarioAtencion.findByHaHorario", query = "SELECT h FROM HorarioAtencion h WHERE h.haHorario = :haHorario"),
    @NamedQuery(name = "HorarioAtencion.findByHaUbicacion", query = "SELECT h FROM HorarioAtencion h WHERE h.haUbicacion = :haUbicacion")})
public class HorarioAtencion implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ha_id")
    private Integer haId;
    @Basic(optional = false)
    @Column(name = "ha_horario")
    private String haHorario;
    @Basic(optional = false)
    @Column(name = "ha_ubicacion")
    private String haUbicacion;
    @JoinColumn(name = "fk_hat_em", referencedColumnName = "em_id")
    @ManyToOne(optional = false)
    private Empleado fkHatEm;
    @JoinColumn(name = "fk_hat_dia", referencedColumnName = "di_id")
    @ManyToOne(optional = false)
    private Dia fkHatDia;

    public HorarioAtencion() {
    }

    public HorarioAtencion(Integer haId) {
        this.haId = haId;
    }

    public HorarioAtencion(Integer haId, String haHorario, String haUbicacion) {
        this.haId = haId;
        this.haHorario = haHorario;
        this.haUbicacion = haUbicacion;
    }

    public Integer getHaId() {
        return haId;
    }

    public void setHaId(Integer haId) {
        this.haId = haId;
    }

    public String getHaHorario() {
        return haHorario;
    }

    public void setHaHorario(String haHorario) {
        this.haHorario = haHorario;
    }

    public String getHaUbicacion() {
        return haUbicacion;
    }

    public void setHaUbicacion(String haUbicacion) {
        this.haUbicacion = haUbicacion;
    }

    public Empleado getFkHatEm() {
        return fkHatEm;
    }

    public void setFkHatEm(Empleado fkHatEm) {
        this.fkHatEm = fkHatEm;
    }

    public Dia getFkHatDia() {
        return fkHatDia;
    }

    public void setFkHatDia(Dia fkHatDia) {
        this.fkHatDia = fkHatDia;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (haId != null ? haId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HorarioAtencion)) {
            return false;
        }
        HorarioAtencion other = (HorarioAtencion) object;
        if ((this.haId == null && other.haId != null) || (this.haId != null && !this.haId.equals(other.haId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entities.HorarioAtencion[ haId=" + haId + " ]";
    }
    
}
