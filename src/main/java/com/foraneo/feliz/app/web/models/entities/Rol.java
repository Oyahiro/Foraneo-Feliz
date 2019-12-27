package com.foraneo.feliz.app.web.models.entities;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "ROL", uniqueConstraints= {@UniqueConstraint(columnNames= {"IDUSUARIO", "NOMBRE"})})
public class Rol implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name="IDROL")
	private Integer idrol;
	
	@Column(name="NOMBRE")	
	private String nombre;
	
	public Rol() {
		super();
	}
	
	public Rol(Integer id) {
		super();
		this.idrol = id;
	}

	public Rol(String nombre) {
		super();
		this.nombre = nombre;
	}
	
	public Integer getIdrol() {
		return idrol;
	}

	public void setIdrol(Integer idrol) {
		this.idrol = idrol;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}