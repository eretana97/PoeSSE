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
@Table(name = "estudiante")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Estudiante.findAll", query = "SELECT e FROM Estudiante e"),
    @NamedQuery(name = "Estudiante.findByEsId", query = "SELECT e FROM Estudiante e WHERE e.esId = :esId"),
    @NamedQuery(name = "Estudiante.findByEsNombre", query = "SELECT e FROM Estudiante e WHERE e.esNombre = :esNombre"),
    @NamedQuery(name = "Estudiante.findByEsApellido", query = "SELECT e FROM Estudiante e WHERE e.esApellido = :esApellido"),
    @NamedQuery(name = "Estudiante.findByEsCorreo", query = "SELECT e FROM Estudiante e WHERE e.esCorreo = :esCorreo"),
    @NamedQuery(name = "Estudiante.findByEsCarnet", query = "SELECT e FROM Estudiante e WHERE e.esCarnet = :esCarnet"),
    @NamedQuery(name = "Estudiante.findByEsTelefono", query = "SELECT e FROM Estudiante e WHERE e.esTelefono = :esTelefono"),
    @NamedQuery(name = "Estudiante.findByEsDireccion", query = "SELECT e FROM Estudiante e WHERE e.esDireccion = :esDireccion"),
    @NamedQuery(name = "Estudiante.findByEsSolvenciaHoras", query = "SELECT e FROM Estudiante e WHERE e.esSolvenciaHoras = :esSolvenciaHoras"),
    @NamedQuery(name = "Estudiante.findByEsEstado", query = "SELECT e FROM Estudiante e WHERE e.esEstado = :esEstado")})
public class Estudiante implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "es_id")
    private Integer esId;
    @Basic(optional = false)
    @Column(name = "es_nombre")
    private String esNombre;
    @Basic(optional = false)
    @Column(name = "es_apellido")
    private String esApellido;
    @Basic(optional = false)
    @Column(name = "es_correo")
    private String esCorreo;
    @Basic(optional = false)
    @Column(name = "es_carnet")
    private String esCarnet;
    @Column(name = "es_telefono")
    private String esTelefono;
    @Column(name = "es_direccion")
    private String esDireccion;
    @Basic(optional = false)
    @Column(name = "es_solvencia_horas")
    private int esSolvenciaHoras;
    @Basic(optional = false)
    @Column(name = "es_estado")
    private int esEstado;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIncEst")
    private List<InscripcionCiclo> inscripcionCicloList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkSolEst")
    private List<Solicitud> solicitudList;
    @JoinColumn(name = "fk_est_usu", referencedColumnName = "us_id")
    @ManyToOne(optional = false)
    private Usuario fkEstUsu;
    @JoinColumn(name = "fk_est_car", referencedColumnName = "ca_id")
    @ManyToOne(optional = false)
    private Carrera fkEstCar;

    public Estudiante() {
    }

    public Estudiante(Integer esId) {
        this.esId = esId;
    }

    public Estudiante(Integer esId, String esNombre, String esApellido, String esCorreo, String esCarnet, int esSolvenciaHoras, int esEstado) {
        this.esId = esId;
        this.esNombre = esNombre;
        this.esApellido = esApellido;
        this.esCorreo = esCorreo;
        this.esCarnet = esCarnet;
        this.esSolvenciaHoras = esSolvenciaHoras;
        this.esEstado = esEstado;
    }

    public Integer getEsId() {
        return esId;
    }

    public void setEsId(Integer esId) {
        this.esId = esId;
    }

    public String getEsNombre() {
        return esNombre;
    }

    public void setEsNombre(String esNombre) {
        this.esNombre = esNombre;
    }

    public String getEsApellido() {
        return esApellido;
    }

    public void setEsApellido(String esApellido) {
        this.esApellido = esApellido;
    }

    public String getEsCorreo() {
        return esCorreo;
    }

    public void setEsCorreo(String esCorreo) {
        this.esCorreo = esCorreo;
    }

    public String getEsCarnet() {
        return esCarnet;
    }

    public void setEsCarnet(String esCarnet) {
        this.esCarnet = esCarnet;
    }

    public String getEsTelefono() {
        return esTelefono;
    }

    public void setEsTelefono(String esTelefono) {
        this.esTelefono = esTelefono;
    }

    public String getEsDireccion() {
        return esDireccion;
    }

    public void setEsDireccion(String esDireccion) {
        this.esDireccion = esDireccion;
    }

    public int getEsSolvenciaHoras() {
        return esSolvenciaHoras;
    }

    public void setEsSolvenciaHoras(int esSolvenciaHoras) {
        this.esSolvenciaHoras = esSolvenciaHoras;
    }

    public int getEsEstado() {
        return esEstado;
    }

    public void setEsEstado(int esEstado) {
        this.esEstado = esEstado;
    }

    @XmlTransient
    public List<InscripcionCiclo> getInscripcionCicloList() {
        return inscripcionCicloList;
    }

    public void setInscripcionCicloList(List<InscripcionCiclo> inscripcionCicloList) {
        this.inscripcionCicloList = inscripcionCicloList;
    }

    @XmlTransient
    public List<Solicitud> getSolicitudList() {
        return solicitudList;
    }

    public void setSolicitudList(List<Solicitud> solicitudList) {
        this.solicitudList = solicitudList;
    }

    public Usuario getFkEstUsu() {
        return fkEstUsu;
    }

    public void setFkEstUsu(Usuario fkEstUsu) {
        this.fkEstUsu = fkEstUsu;
    }

    public Carrera getFkEstCar() {
        return fkEstCar;
    }

    public void setFkEstCar(Carrera fkEstCar) {
        this.fkEstCar = fkEstCar;
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
        if (!(object instanceof Estudiante)) {
            return false;
        }
        Estudiante other = (Estudiante) object;
        if ((this.esId == null && other.esId != null) || (this.esId != null && !this.esId.equals(other.esId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entities.Estudiante[ esId=" + esId + " ]";
    }
    
}
