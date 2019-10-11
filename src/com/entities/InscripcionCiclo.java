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
@Table(name = "inscripcion_ciclo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "InscripcionCiclo.findAll", query = "SELECT i FROM InscripcionCiclo i"),
    @NamedQuery(name = "InscripcionCiclo.findByIcId", query = "SELECT i FROM InscripcionCiclo i WHERE i.icId = :icId"),
    @NamedQuery(name = "InscripcionCiclo.findByIcMateriasXCiclo", query = "SELECT i FROM InscripcionCiclo i WHERE i.icMateriasXCiclo = :icMateriasXCiclo"),
    @NamedQuery(name = "InscripcionCiclo.findByIcJornada", query = "SELECT i FROM InscripcionCiclo i WHERE i.icJornada = :icJornada")})
public class InscripcionCiclo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ic_id")
    private Integer icId;
    @Basic(optional = false)
    @Column(name = "ic_materias_x_ciclo")
    private String icMateriasXCiclo;
    @Basic(optional = false)
    @Column(name = "ic_jornada")
    private String icJornada;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkMatInc")
    private List<Materia> materiaList;
    @JoinColumn(name = "fk_inc_est", referencedColumnName = "es_id")
    @ManyToOne(optional = false)
    private Estudiante fkIncEst;
    @JoinColumn(name = "fk_inc_cic", referencedColumnName = "ci_id")
    @ManyToOne(optional = false)
    private Ciclo fkIncCic;

    public InscripcionCiclo() {
    }

    public InscripcionCiclo(Integer icId) {
        this.icId = icId;
    }

    public InscripcionCiclo(Integer icId, String icMateriasXCiclo, String icJornada) {
        this.icId = icId;
        this.icMateriasXCiclo = icMateriasXCiclo;
        this.icJornada = icJornada;
    }

    public Integer getIcId() {
        return icId;
    }

    public void setIcId(Integer icId) {
        this.icId = icId;
    }

    public String getIcMateriasXCiclo() {
        return icMateriasXCiclo;
    }

    public void setIcMateriasXCiclo(String icMateriasXCiclo) {
        this.icMateriasXCiclo = icMateriasXCiclo;
    }

    public String getIcJornada() {
        return icJornada;
    }

    public void setIcJornada(String icJornada) {
        this.icJornada = icJornada;
    }

    @XmlTransient
    public List<Materia> getMateriaList() {
        return materiaList;
    }

    public void setMateriaList(List<Materia> materiaList) {
        this.materiaList = materiaList;
    }

    public Estudiante getFkIncEst() {
        return fkIncEst;
    }

    public void setFkIncEst(Estudiante fkIncEst) {
        this.fkIncEst = fkIncEst;
    }

    public Ciclo getFkIncCic() {
        return fkIncCic;
    }

    public void setFkIncCic(Ciclo fkIncCic) {
        this.fkIncCic = fkIncCic;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (icId != null ? icId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InscripcionCiclo)) {
            return false;
        }
        InscripcionCiclo other = (InscripcionCiclo) object;
        if ((this.icId == null && other.icId != null) || (this.icId != null && !this.icId.equals(other.icId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entities.InscripcionCiclo[ icId=" + icId + " ]";
    }
    
}
