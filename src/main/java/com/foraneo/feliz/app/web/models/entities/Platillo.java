package com.foraneo.feliz.app.web.models.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;

@Entity
@Table(name="PLATILLO")
public class Platillo implements Serializable{

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name="IDPLATILLO")
	private  Integer idplatillo;
	
	@Column(name="NOMBRE")
	@Size(max=30)
	@NotEmpty
	private String nombre;
	
	@Column(name="DESCRIPCION")
	@Size(max=100)
	@NotEmpty
	private String descripcion;
	
	@Column(name="PRECIO")
	@NotNull
	private Float precio;

	@JsonIgnore
	@OneToMany(mappedBy="platillo", fetch=FetchType.LAZY)
	private List<Detalle> detalle;
	
	@JoinColumn (name="IDRESTAURANTE", referencedColumnName="IDRESTAURANTE")
	@ManyToOne
	private Restaurante restaurante;

	public Platillo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Integer getIdplatillo() {
		return idplatillo;
	}

	public void setIdplatillo(Integer idplatillo) {
		this.idplatillo = idplatillo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Float getPrecio() {
		return precio;
	}

	public void setPrecio(Float precio) {
		this.precio = precio;
	}

	public Restaurante getRestaurante() {
		return restaurante;
	}

	public void setRestaurante(Restaurante restaurante) {
		this.restaurante = restaurante;
	}
}
