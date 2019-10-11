/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Nataly
 */
@Entity
@Table(name = "dia")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Dia.findAll", query = "SELECT d FROM Dia d"),
    @NamedQuery(name = "Dia.findByDiId", query = "SELECT d FROM Dia d WHERE d.diId = :diId"),
    @NamedQuery(name = "Dia.findByDiDia", query = "SELECT d FROM Dia d WHERE d.diDia = :diDia")})
public class Dia implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "di_id")
    private Integer diId;
    @Basic(optional = false)
    @Column(name = "di_dia")
    private String diDia;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkHatDia")
    private List<HorarioAtencion> horarioAtencionList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkHssDia")
    private List<HorariosSse> horariosSseList;

    public Dia() {
    }

    public Dia(Integer diId) {
        this.diId = diId;
    }

    public Dia(Integer diId, String diDia) {
        this.diId = diId;
        this.diDia = diDia;
    }

    public Integer getDiId() {
        return diId;
    }

    public void setDiId(Integer diId) {
        this.diId = diId;
    }

    public String getDiDia() {
        return diDia;
    }

    public void setDiDia(String diDia) {
        this.diDia = diDia;
    }

    @XmlTransient
    public List<HorarioAtencion> getHorarioAtencionList() {
        return horarioAtencionList;
    }

    public void setHorarioAtencionList(List<HorarioAtencion> horarioAtencionList) {
        this.horarioAtencionList = horarioAtencionList;
    }

    @XmlTransient
    public List<HorariosSse> getHorariosSseList() {
        return horariosSseList;
    }

    public void setHorariosSseList(List<HorariosSse> horariosSseList) {
        this.horariosSseList = horariosSseList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (diId != null ? diId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Dia)) {
            return false;
        }
        Dia other = (Dia) object;
        if ((this.diId == null && other.diId != null) || (this.diId != null && !this.diId.equals(other.diId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entities.Dia[ diId=" + diId + " ]";
    }
    
}
