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
@Table(name = "tipo_usuario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoUsuario.findAll", query = "SELECT t FROM TipoUsuario t"),
    @NamedQuery(name = "TipoUsuario.findByTiId", query = "SELECT t FROM TipoUsuario t WHERE t.tiId = :tiId"),
    @NamedQuery(name = "TipoUsuario.findByTiTipo", query = "SELECT t FROM TipoUsuario t WHERE t.tiTipo = :tiTipo")})
public class TipoUsuario implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ti_id")
    private Integer tiId;
    @Basic(optional = false)
    @Column(name = "ti_tipo")
    private String tiTipo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkUsuTip")
    private List<Usuario> usuarioList;

    public TipoUsuario() {
    }

    public TipoUsuario(Integer tiId) {
        this.tiId = tiId;
    }

    public TipoUsuario(Integer tiId, String tiTipo) {
        this.tiId = tiId;
        this.tiTipo = tiTipo;
    }

    public Integer getTiId() {
        return tiId;
    }

    public void setTiId(Integer tiId) {
        this.tiId = tiId;
    }

    public String getTiTipo() {
        return tiTipo;
    }

    public void setTiTipo(String tiTipo) {
        this.tiTipo = tiTipo;
    }

    @XmlTransient
    public List<Usuario> getUsuarioList() {
        return usuarioList;
    }

    public void setUsuarioList(List<Usuario> usuarioList) {
        this.usuarioList = usuarioList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tiId != null ? tiId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoUsuario)) {
            return false;
        }
        TipoUsuario other = (TipoUsuario) object;
        if ((this.tiId == null && other.tiId != null) || (this.tiId != null && !this.tiId.equals(other.tiId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entities.TipoUsuario[ tiId=" + tiId + " ]";
    }
    
}
