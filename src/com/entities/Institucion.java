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
@Table(name = "institucion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Institucion.findAll", query = "SELECT i FROM Institucion i"),
    @NamedQuery(name = "Institucion.findByInId", query = "SELECT i FROM Institucion i WHERE i.inId = :inId"),
    @NamedQuery(name = "Institucion.findByInNombre", query = "SELECT i FROM Institucion i WHERE i.inNombre = :inNombre"),
    @NamedQuery(name = "Institucion.findByInEncargado", query = "SELECT i FROM Institucion i WHERE i.inEncargado = :inEncargado"),
    @NamedQuery(name = "Institucion.findByInDireccion", query = "SELECT i FROM Institucion i WHERE i.inDireccion = :inDireccion"),
    @NamedQuery(name = "Institucion.findByInTelefono", query = "SELECT i FROM Institucion i WHERE i.inTelefono = :inTelefono"),
    @NamedQuery(name = "Institucion.findByInSitioWeb", query = "SELECT i FROM Institucion i WHERE i.inSitioWeb = :inSitioWeb")})
public class Institucion implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "in_id")
    private Integer inId;
    @Basic(optional = false)
    @Column(name = "in_nombre")
    private String inNombre;
    @Basic(optional = false)
    @Column(name = "in_encargado")
    private String inEncargado;
    @Basic(optional = false)
    @Column(name = "in_direccion")
    private String inDireccion;
    @Basic(optional = false)
    @Column(name = "in_telefono")
    private String inTelefono;
    @Basic(optional = false)
    @Column(name = "in_sitio_web")
    private String inSitioWeb;
    @JoinColumn(name = "fk_ins_sol", referencedColumnName = "so_id")
    @ManyToOne(optional = false)
    private Solicitud fkInsSol;

    public Institucion() {
    }

    public Institucion(Integer inId) {
        this.inId = inId;
    }

    public Institucion(Integer inId, String inNombre, String inEncargado, String inDireccion, String inTelefono, String inSitioWeb) {
        this.inId = inId;
        this.inNombre = inNombre;
        this.inEncargado = inEncargado;
        this.inDireccion = inDireccion;
        this.inTelefono = inTelefono;
        this.inSitioWeb = inSitioWeb;
    }

    public Integer getInId() {
        return inId;
    }

    public void setInId(Integer inId) {
        this.inId = inId;
    }

    public String getInNombre() {
        return inNombre;
    }

    public void setInNombre(String inNombre) {
        this.inNombre = inNombre;
    }

    public String getInEncargado() {
        return inEncargado;
    }

    public void setInEncargado(String inEncargado) {
        this.inEncargado = inEncargado;
    }

    public String getInDireccion() {
        return inDireccion;
    }

    public void setInDireccion(String inDireccion) {
        this.inDireccion = inDireccion;
    }

    public String getInTelefono() {
        return inTelefono;
    }

    public void setInTelefono(String inTelefono) {
        this.inTelefono = inTelefono;
    }

    public String getInSitioWeb() {
        return inSitioWeb;
    }

    public void setInSitioWeb(String inSitioWeb) {
        this.inSitioWeb = inSitioWeb;
    }

    public Solicitud getFkInsSol() {
        return fkInsSol;
    }

    public void setFkInsSol(Solicitud fkInsSol) {
        this.fkInsSol = fkInsSol;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (inId != null ? inId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Institucion)) {
            return false;
        }
        Institucion other = (Institucion) object;
        if ((this.inId == null && other.inId != null) || (this.inId != null && !this.inId.equals(other.inId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entities.Institucion[ inId=" + inId + " ]";
    }
    
}
