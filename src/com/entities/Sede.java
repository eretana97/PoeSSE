/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
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
@Table(name = "sede")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Sede.findAll", query = "SELECT s FROM Sede s"),
    @NamedQuery(name = "Sede.findBySeId", query = "SELECT s FROM Sede s WHERE s.seId = :seId"),
    @NamedQuery(name = "Sede.findBySeSede", query = "SELECT s FROM Sede s WHERE s.seSede = :seSede")})
public class Sede implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "se_id")
    private Integer seId;
    @Basic(optional = false)
    @Column(name = "se_sede")
    private String seSede;
    @OneToMany(mappedBy = "fkEscSed")
    private List<Escuela> escuelaList;

    public Sede() {
    }

    public Sede(Integer seId) {
        this.seId = seId;
    }

    public Sede(Integer seId, String seSede) {
        this.seId = seId;
        this.seSede = seSede;
    }

    public Integer getSeId() {
        return seId;
    }

    public void setSeId(Integer seId) {
        this.seId = seId;
    }

    public String getSeSede() {
        return seSede;
    }

    public void setSeSede(String seSede) {
        this.seSede = seSede;
    }

    @XmlTransient
    public List<Escuela> getEscuelaList() {
        return escuelaList;
    }

    public void setEscuelaList(List<Escuela> escuelaList) {
        this.escuelaList = escuelaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (seId != null ? seId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Sede)) {
            return false;
        }
        Sede other = (Sede) object;
        if ((this.seId == null && other.seId != null) || (this.seId != null && !this.seId.equals(other.seId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entities.Sede[ seId=" + seId + " ]";
    }
    
}
