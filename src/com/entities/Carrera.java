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
@Table(name = "carrera")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Carrera.findAll", query = "SELECT c FROM Carrera c"),
    @NamedQuery(name = "Carrera.findByCaId", query = "SELECT c FROM Carrera c WHERE c.caId = :caId"),
    @NamedQuery(name = "Carrera.findByCaCarrera", query = "SELECT c FROM Carrera c WHERE c.caCarrera = :caCarrera"),
    @NamedQuery(name = "Carrera.findByCaCantidadMaterias", query = "SELECT c FROM Carrera c WHERE c.caCantidadMaterias = :caCantidadMaterias")})
public class Carrera implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ca_id")
    private Integer caId;
    @Basic(optional = false)
    @Column(name = "ca_carrera")
    private String caCarrera;
    @Basic(optional = false)
    @Column(name = "ca_cantidad_materias")
    private int caCantidadMaterias;
    @JoinColumn(name = "fk_car_tca", referencedColumnName = "tc_id")
    @ManyToOne(optional = false)
    private TipoCarrera fkCarTca;
    @JoinColumn(name = "fk_car_esc", referencedColumnName = "es_id")
    @ManyToOne(optional = false)
    private Escuela fkCarEsc;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkEstCar")
    private List<Estudiante> estudianteList;

    public Carrera() {
    }

    public Carrera(Integer caId) {
        this.caId = caId;
    }

    public Carrera(Integer caId, String caCarrera, int caCantidadMaterias) {
        this.caId = caId;
        this.caCarrera = caCarrera;
        this.caCantidadMaterias = caCantidadMaterias;
    }

    public Integer getCaId() {
        return caId;
    }

    public void setCaId(Integer caId) {
        this.caId = caId;
    }

    public String getCaCarrera() {
        return caCarrera;
    }

    public void setCaCarrera(String caCarrera) {
        this.caCarrera = caCarrera;
    }

    public int getCaCantidadMaterias() {
        return caCantidadMaterias;
    }

    public void setCaCantidadMaterias(int caCantidadMaterias) {
        this.caCantidadMaterias = caCantidadMaterias;
    }

    public TipoCarrera getFkCarTca() {
        return fkCarTca;
    }

    public void setFkCarTca(TipoCarrera fkCarTca) {
        this.fkCarTca = fkCarTca;
    }

    public Escuela getFkCarEsc() {
        return fkCarEsc;
    }

    public void setFkCarEsc(Escuela fkCarEsc) {
        this.fkCarEsc = fkCarEsc;
    }

    @XmlTransient
    public List<Estudiante> getEstudianteList() {
        return estudianteList;
    }

    public void setEstudianteList(List<Estudiante> estudianteList) {
        this.estudianteList = estudianteList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (caId != null ? caId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Carrera)) {
            return false;
        }
        Carrera other = (Carrera) object;
        if ((this.caId == null && other.caId != null) || (this.caId != null && !this.caId.equals(other.caId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entities.Carrera[ caId=" + caId + " ]";
    }
    
}
