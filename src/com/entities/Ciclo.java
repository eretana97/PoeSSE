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
@Table(name = "ciclo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ciclo.findAll", query = "SELECT c FROM Ciclo c"),
    @NamedQuery(name = "Ciclo.findByCiId", query = "SELECT c FROM Ciclo c WHERE c.ciId = :ciId"),
    @NamedQuery(name = "Ciclo.findByCiCiclo", query = "SELECT c FROM Ciclo c WHERE c.ciCiclo = :ciCiclo"),
    @NamedQuery(name = "Ciclo.findByCiA\u00f1o", query = "SELECT c FROM Ciclo c WHERE c.ciA\u00f1o = :ciA\u00f1o")})
public class Ciclo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ci_id")
    private Integer ciId;
    @Basic(optional = false)
    @Column(name = "ci_ciclo")
    private String ciCiclo;
    @Basic(optional = false)
    @Column(name = "ci_a\u00f1o")
    private String ciAño;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIncCic")
    private List<InscripcionCiclo> inscripcionCicloList;

    public Ciclo() {
    }

    public Ciclo(Integer ciId) {
        this.ciId = ciId;
    }

    public Ciclo(Integer ciId, String ciCiclo, String ciAño) {
        this.ciId = ciId;
        this.ciCiclo = ciCiclo;
        this.ciAño = ciAño;
    }

    public Integer getCiId() {
        return ciId;
    }

    public void setCiId(Integer ciId) {
        this.ciId = ciId;
    }

    public String getCiCiclo() {
        return ciCiclo;
    }

    public void setCiCiclo(String ciCiclo) {
        this.ciCiclo = ciCiclo;
    }

    public String getCiAño() {
        return ciAño;
    }

    public void setCiAño(String ciAño) {
        this.ciAño = ciAño;
    }

    @XmlTransient
    public List<InscripcionCiclo> getInscripcionCicloList() {
        return inscripcionCicloList;
    }

    public void setInscripcionCicloList(List<InscripcionCiclo> inscripcionCicloList) {
        this.inscripcionCicloList = inscripcionCicloList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ciId != null ? ciId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ciclo)) {
            return false;
        }
        Ciclo other = (Ciclo) object;
        if ((this.ciId == null && other.ciId != null) || (this.ciId != null && !this.ciId.equals(other.ciId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entities.Ciclo[ ciId=" + ciId + " ]";
    }
    
}
