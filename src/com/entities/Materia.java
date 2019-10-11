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
@Table(name = "materia")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Materia.findAll", query = "SELECT m FROM Materia m"),
    @NamedQuery(name = "Materia.findByMaId", query = "SELECT m FROM Materia m WHERE m.maId = :maId"),
    @NamedQuery(name = "Materia.findByMaMateria", query = "SELECT m FROM Materia m WHERE m.maMateria = :maMateria")})
public class Materia implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ma_id")
    private Integer maId;
    @Basic(optional = false)
    @Column(name = "ma_materia")
    private String maMateria;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkNotMat")
    private List<Nota> notaList;
    @JoinColumn(name = "fk_mat_inc", referencedColumnName = "ic_id")
    @ManyToOne(optional = false)
    private InscripcionCiclo fkMatInc;

    public Materia() {
    }

    public Materia(Integer maId) {
        this.maId = maId;
    }

    public Materia(Integer maId, String maMateria) {
        this.maId = maId;
        this.maMateria = maMateria;
    }

    public Integer getMaId() {
        return maId;
    }

    public void setMaId(Integer maId) {
        this.maId = maId;
    }

    public String getMaMateria() {
        return maMateria;
    }

    public void setMaMateria(String maMateria) {
        this.maMateria = maMateria;
    }

    @XmlTransient
    public List<Nota> getNotaList() {
        return notaList;
    }

    public void setNotaList(List<Nota> notaList) {
        this.notaList = notaList;
    }

    public InscripcionCiclo getFkMatInc() {
        return fkMatInc;
    }

    public void setFkMatInc(InscripcionCiclo fkMatInc) {
        this.fkMatInc = fkMatInc;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (maId != null ? maId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Materia)) {
            return false;
        }
        Materia other = (Materia) object;
        if ((this.maId == null && other.maId != null) || (this.maId != null && !this.maId.equals(other.maId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entities.Materia[ maId=" + maId + " ]";
    }
    
}
