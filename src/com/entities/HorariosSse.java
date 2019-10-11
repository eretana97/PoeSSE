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
@Table(name = "horarios_sse")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HorariosSse.findAll", query = "SELECT h FROM HorariosSse h"),
    @NamedQuery(name = "HorariosSse.findByHsId", query = "SELECT h FROM HorariosSse h WHERE h.hsId = :hsId"),
    @NamedQuery(name = "HorariosSse.findByHsHorario", query = "SELECT h FROM HorariosSse h WHERE h.hsHorario = :hsHorario"),
    @NamedQuery(name = "HorariosSse.findByHsComentarioModificacion", query = "SELECT h FROM HorariosSse h WHERE h.hsComentarioModificacion = :hsComentarioModificacion")})
public class HorariosSse implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "hs_id")
    private Integer hsId;
    @Basic(optional = false)
    @Column(name = "hs_horario")
    private String hsHorario;
    @Column(name = "hs_comentario_modificacion")
    private String hsComentarioModificacion;
    @JoinColumn(name = "fk_hss_sol", referencedColumnName = "so_id")
    @ManyToOne(optional = false)
    private Solicitud fkHssSol;
    @JoinColumn(name = "fk_hss_dia", referencedColumnName = "di_id")
    @ManyToOne(optional = false)
    private Dia fkHssDia;

    public HorariosSse() {
    }

    public HorariosSse(Integer hsId) {
        this.hsId = hsId;
    }

    public HorariosSse(Integer hsId, String hsHorario) {
        this.hsId = hsId;
        this.hsHorario = hsHorario;
    }

    public Integer getHsId() {
        return hsId;
    }

    public void setHsId(Integer hsId) {
        this.hsId = hsId;
    }

    public String getHsHorario() {
        return hsHorario;
    }

    public void setHsHorario(String hsHorario) {
        this.hsHorario = hsHorario;
    }

    public String getHsComentarioModificacion() {
        return hsComentarioModificacion;
    }

    public void setHsComentarioModificacion(String hsComentarioModificacion) {
        this.hsComentarioModificacion = hsComentarioModificacion;
    }

    public Solicitud getFkHssSol() {
        return fkHssSol;
    }

    public void setFkHssSol(Solicitud fkHssSol) {
        this.fkHssSol = fkHssSol;
    }

    public Dia getFkHssDia() {
        return fkHssDia;
    }

    public void setFkHssDia(Dia fkHssDia) {
        this.fkHssDia = fkHssDia;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (hsId != null ? hsId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HorariosSse)) {
            return false;
        }
        HorariosSse other = (HorariosSse) object;
        if ((this.hsId == null && other.hsId != null) || (this.hsId != null && !this.hsId.equals(other.hsId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entities.HorariosSse[ hsId=" + hsId + " ]";
    }
    
}
