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
@Table(name = "tipo_carrera")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoCarrera.findAll", query = "SELECT t FROM TipoCarrera t"),
    @NamedQuery(name = "TipoCarrera.findByTcId", query = "SELECT t FROM TipoCarrera t WHERE t.tcId = :tcId"),
    @NamedQuery(name = "TipoCarrera.findByTcNombreTipo", query = "SELECT t FROM TipoCarrera t WHERE t.tcNombreTipo = :tcNombreTipo")})
public class TipoCarrera implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "tc_id")
    private Integer tcId;
    @Basic(optional = false)
    @Column(name = "tc_nombre_tipo")
    private String tcNombreTipo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkCarTca")
    private List<Carrera> carreraList;

    public TipoCarrera() {
    }

    public TipoCarrera(Integer tcId) {
        this.tcId = tcId;
    }

    public TipoCarrera(Integer tcId, String tcNombreTipo) {
        this.tcId = tcId;
        this.tcNombreTipo = tcNombreTipo;
    }

    public Integer getTcId() {
        return tcId;
    }

    public void setTcId(Integer tcId) {
        this.tcId = tcId;
    }

    public String getTcNombreTipo() {
        return tcNombreTipo;
    }

    public void setTcNombreTipo(String tcNombreTipo) {
        this.tcNombreTipo = tcNombreTipo;
    }

    @XmlTransient
    public List<Carrera> getCarreraList() {
        return carreraList;
    }

    public void setCarreraList(List<Carrera> carreraList) {
        this.carreraList = carreraList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tcId != null ? tcId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoCarrera)) {
            return false;
        }
        TipoCarrera other = (TipoCarrera) object;
        if ((this.tcId == null && other.tcId != null) || (this.tcId != null && !this.tcId.equals(other.tcId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entities.TipoCarrera[ tcId=" + tcId + " ]";
    }
    
}
