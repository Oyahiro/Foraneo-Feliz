package com.foraneo.feliz.app.web.models.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;

@Entity
@Table(name = "USUARIO")
public class Usuario extends Persona implements Serializable {

	private static final long serialVersionUID = 1L;
	
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
	
	public Usuario(Integer id) {
		super();
		this.setIdPersona(id);
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