package com.foraneo.feliz.app.web.models.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

@Entity
@Table(name = "USUARIO")
public class Usuario implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name="IDUSUARIO")
	private  Integer idusuario;
	
	@Column(name="NOMBRE", unique = true)	
	private String nombre;
	
	@Column(name="CONTRASENIA", length=60)	
	private String contrasenia;
	
	@Column(name="HABILITADO")	
	private Boolean habilitado;
	
	@Column(name="CREADOEN")
	private Calendar creadoEn;
		
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name= "IDUSUARIO")
	private List<Rol> roles;
		
	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
	private Cliente cliente;
	
	@OneToOne(mappedBy = "userRest", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
	private Restaurante restaurante;
	
<<<<<<< HEAD
	@OneToOne(mappedBy = "userEnc", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
	private Encomendero encomendero;
	
=======
>>>>>>> develop
	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<Rol> getRoles() {
		if(roles == null)
			roles = new ArrayList<>();
		return roles;
	}

	public void setRoles(List<Rol> roles) {
		this.roles = roles;
	}

	public Usuario() {
		super();
	}
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getContrasenia() {
		return contrasenia;
	}

	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}
	
	public Boolean getHabilitado() {
		return habilitado;
	}

	public void setHabilitado(Boolean habilitado) {
		this.habilitado = habilitado;
	}

	public Integer getIdusuario() {
		return idusuario;
	}

	public void setIdusuario(Integer idusuario) {
		this.idusuario = idusuario;
	}

	public Calendar getCreadoEn() {
		return creadoEn;
	}

	public void setCreadoEn(Calendar creadoEn) {
		this.creadoEn = creadoEn;
	}

	@PrePersist // Se llama al método antes de que la entidad se inserte en la base de datos
    public void prePersist() {
        creadoEn = Calendar.getInstance();
        habilitado = true;
    }
}