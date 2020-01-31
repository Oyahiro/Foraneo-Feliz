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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="RESTAURANTE")
public class Restaurante implements Serializable{

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name="IDRESTAURANTE")
	private  Integer idrestaurante;
	
	@Column(name="NOMBRE")
	@Size(max=25)
	@NotBlank
	private String nombre;
	
	@Column(name="DIRECCION")
	@Size(max=50)
	@NotBlank
	private String direccion;
	
	@Column(name="TIPO")
	@Size(max=40)
	@NotBlank
	private String tipo;
	
	@Column(name="CELULAR")
	@Size(min=10, max=10)
	@NotBlank
	private String celular;
	
	@Column(name="CORREO")
	@Size(max=30)
	@NotBlank
	@Email
	private String correo;
	
	@Column(name="WEB")
	@Size(max=30)
	@NotBlank
	private String web;
	
	@JsonIgnore
	@OneToMany(mappedBy="restaurante", fetch=FetchType.LAZY)
	private List<Platillo> platillo;

	@JsonIgnore
	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IDUSUARIO")
    private Usuario userRest;
	
	public Restaurante() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Integer getIdrestaurante() {
		return idrestaurante;
	}

	public void setIdrestaurante(Integer idrestaurante) {
		this.idrestaurante = idrestaurante;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getWeb() {
		return web;
	}

	public void setWeb(String web) {
		this.web = web;
	}

	public List<Platillo> getPlatillo() {
		return platillo;
	}

	public void setPlatillo(List<Platillo> platillo) {
		this.platillo = platillo;
	}

	public Usuario getUserRest() {
		return userRest;
	}

	public void setUserRest(Usuario userRest) {
		this.userRest = userRest;
	}
}
