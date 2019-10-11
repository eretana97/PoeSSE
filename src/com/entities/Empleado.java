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
@Table(name = "empleado")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Empleado.findAll", query = "SELECT e FROM Empleado e"),
    @NamedQuery(name = "Empleado.findByEmId", query = "SELECT e FROM Empleado e WHERE e.emId = :emId"),
    @NamedQuery(name = "Empleado.findByEmNombre", query = "SELECT e FROM Empleado e WHERE e.emNombre = :emNombre"),
    @NamedQuery(name = "Empleado.findByEmApellido", query = "SELECT e FROM Empleado e WHERE e.emApellido = :emApellido"),
    @NamedQuery(name = "Empleado.findByEmCarnet", query = "SELECT e FROM Empleado e WHERE e.emCarnet = :emCarnet"),
    @NamedQuery(name = "Empleado.findByFkEmEsc", query = "SELECT e FROM Empleado e WHERE e.fkEmEsc = :fkEmEsc")})
public class Empleado implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "em_id")
    private Integer emId;
    @Basic(optional = false)
    @Column(name = "em_nombre")
    private String emNombre;
    @Basic(optional = false)
    @Column(name = "em_apellido")
    private String emApellido;
    @Basic(optional = false)
    @Column(name = "em_carnet")
    private String emCarnet;
    @Basic(optional = false)
    @Column(name = "fk_em_esc")
    private int fkEmEsc;
    @JoinColumn(name = "fk_em_usu", referencedColumnName = "us_id")
    @ManyToOne(optional = false)
    private Usuario fkEmUsu;
    @JoinColumn(name = "fk_em_usu", referencedColumnName = "es_id")
    @ManyToOne(optional = false)
    private Escuela fkEmUsu1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkHatEm")
    private List<HorarioAtencion> horarioAtencionList;

    public Empleado() {
    }

    public Empleado(Integer emId) {
        this.emId = emId;
    }

    public Empleado(Integer emId, String emNombre, String emApellido, String emCarnet, int fkEmEsc) {
        this.emId = emId;
        this.emNombre = emNombre;
        this.emApellido = emApellido;
        this.emCarnet = emCarnet;
        this.fkEmEsc = fkEmEsc;
    }

    public Integer getEmId() {
        return emId;
    }

    public void setEmId(Integer emId) {
        this.emId = emId;
    }

    public String getEmNombre() {
        return emNombre;
    }

    public void setEmNombre(String emNombre) {
        this.emNombre = emNombre;
    }

    public String getEmApellido() {
        return emApellido;
    }

    public void setEmApellido(String emApellido) {
        this.emApellido = emApellido;
    }

    public String getEmCarnet() {
        return emCarnet;
    }

    public void setEmCarnet(String emCarnet) {
        this.emCarnet = emCarnet;
    }

    public int getFkEmEsc() {
        return fkEmEsc;
    }

    public void setFkEmEsc(int fkEmEsc) {
        this.fkEmEsc = fkEmEsc;
    }

    public Usuario getFkEmUsu() {
        return fkEmUsu;
    }

    public void setFkEmUsu(Usuario fkEmUsu) {
        this.fkEmUsu = fkEmUsu;
    }

    public Escuela getFkEmUsu1() {
        return fkEmUsu1;
    }

    public void setFkEmUsu1(Escuela fkEmUsu1) {
        this.fkEmUsu1 = fkEmUsu1;
    }

    @XmlTransient
    public List<HorarioAtencion> getHorarioAtencionList() {
        return horarioAtencionList;
    }

    public void setHorarioAtencionList(List<HorarioAtencion> horarioAtencionList) {
        this.horarioAtencionList = horarioAtencionList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (emId != null ? emId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Empleado)) {
            return false;
        }
        Empleado other = (Empleado) object;
        if ((this.emId == null && other.emId != null) || (this.emId != null && !this.emId.equals(other.emId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entities.Empleado[ emId=" + emId + " ]";
    }
    
}
