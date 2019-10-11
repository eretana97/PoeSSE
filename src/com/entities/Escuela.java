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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "escuela")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Escuela.findAll", query = "SELECT e FROM Escuela e"),
    @NamedQuery(name = "Escuela.findByEsId", query = "SELECT e FROM Escuela e WHERE e.esId = :esId"),
    @NamedQuery(name = "Escuela.findByEsEscuela", query = "SELECT e FROM Escuela e WHERE e.esEscuela = :esEscuela")})
public class Escuela implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "es_id")
    private Integer esId;
    @Basic(optional = false)
    @Column(name = "es_escuela")
    private String esEscuela;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkEmUsu1")
    private List<Empleado> empleadoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkCarEsc")
    private List<Carrera> carreraList;
    @JoinColumn(name = "fk_esc_sed", referencedColumnName = "se_id")
    @ManyToOne
    private Sede fkEscSed;

    public Escuela() {
    }

    public Escuela(Integer esId) {
        this.esId = esId;
    }

    public Escuela(Integer esId, String esEscuela) {
        this.esId = esId;
        this.esEscuela = esEscuela;
    }

    public Integer getEsId() {
        return esId;
    }

    public void setEsId(Integer esId) {
        this.esId = esId;
    }

    public String getEsEscuela() {
        return esEscuela;
    }

    public void setEsEscuela(String esEscuela) {
        this.esEscuela = esEscuela;
    }

    @XmlTransient
    public List<Empleado> getEmpleadoList() {
        return empleadoList;
    }

    public void setEmpleadoList(List<Empleado> empleadoList) {
        this.empleadoList = empleadoList;
    }

    @XmlTransient
    public List<Carrera> getCarreraList() {
        return carreraList;
    }

    public void setCarreraList(List<Carrera> carreraList) {
        this.carreraList = carreraList;
    }

    public Sede getFkEscSed() {
        return fkEscSed;
    }

    public void setFkEscSed(Sede fkEscSed) {
        this.fkEscSed = fkEscSed;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (esId != null ? esId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Escuela)) {
            return false;
        }
        Escuela other = (Escuela) object;
        if ((this.esId == null && other.esId != null) || (this.esId != null && !this.esId.equals(other.esId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entities.Escuela[ esId=" + esId + " ]";
    }
    
}
