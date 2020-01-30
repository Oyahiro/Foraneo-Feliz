package com.foraneo.feliz.app.web.models.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="ENCOMENDERO")
public class Encomendero extends Persona implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Column(name="CEDULA")
	@Size(min=10, max=10)
	@NotBlank
	private String cedula;
	
	@JsonIgnore
	@OneToMany(mappedBy="encomendero", fetch=FetchType.LAZY)
	private List<Pedido> pedido;

	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IDUSUARIO")
    private Usuario userEnc;
	
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

	public List<Pedido> getPedido() {
		return pedido;
	}

	public void setPedido(List<Pedido> pedido) {
		this.pedido = pedido;
	}

	public Usuario getUserEnc() {
		return userEnc;
	}

	public void setUserEnc(Usuario userEnc) {
		this.userEnc = userEnc;
	}
}
