package com.foraneo.feliz.app.web.models.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name="ENCOMENDERO")
public class Encomendero extends Persona implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name="CEDULA")
	@Size(max=10)
	private String cedula;
	
	@OneToMany(mappedBy="encomendero", fetch=FetchType.LAZY)
	private List<Pedido> pedido;

	public Encomendero() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getCedula() {
		return cedula;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
