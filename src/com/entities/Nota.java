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
@Table(name = "nota")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Nota.findAll", query = "SELECT n FROM Nota n"),
    @NamedQuery(name = "Nota.findByNoId", query = "SELECT n FROM Nota n WHERE n.noId = :noId"),
    @NamedQuery(name = "Nota.findByNoNota", query = "SELECT n FROM Nota n WHERE n.noNota = :noNota")})
public class Nota implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "no_id")
    private Integer noId;
    @Basic(optional = false)
    @Column(name = "no_nota")
    private double noNota;
    @JoinColumn(name = "fk_not_mat", referencedColumnName = "ma_id")
    @ManyToOne(optional = false)
    private Materia fkNotMat;

    public Nota() {
    }

    public Nota(Integer noId) {
        this.noId = noId;
    }

    public Nota(Integer noId, double noNota) {
        this.noId = noId;
        this.noNota = noNota;
    }

    public Integer getNoId() {
        return noId;
    }

    public void setNoId(Integer noId) {
        this.noId = noId;
    }

    public double getNoNota() {
        return noNota;
    }

    public void setNoNota(double noNota) {
        this.noNota = noNota;
    }

    public Materia getFkNotMat() {
        return fkNotMat;
    }

    public void setFkNotMat(Materia fkNotMat) {
        this.fkNotMat = fkNotMat;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (noId != null ? noId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Nota)) {
            return false;
        }
        Nota other = (Nota) object;
        if ((this.noId == null && other.noId != null) || (this.noId != null && !this.noId.equals(other.noId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entities.Nota[ noId=" + noId + " ]";
    }
    
}
