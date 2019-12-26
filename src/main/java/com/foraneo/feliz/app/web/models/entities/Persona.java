package com.foraneo.feliz.app.web.models.entities;

import java.util.Calendar;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.sun.istack.NotNull;

@MappedSuperclass
public abstract class Persona {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name="IDPERSONA")
	private  Integer idPersona;
		
	@Column(name="NOMBRES")
	@Size(max=55)
	@NotBlank
	private  String  nombres;
	
	@Column(name="APELLIDOS")
	@Size(max=55)
	@NotBlank
	private  String apellidos;

	@Column(name="FNACIMIENTO")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@Past
	@NotNull
	private Calendar fNacimiento;
	
	@Column(name="CELULAR")
	@Size(min=10, max=10)
	@NotBlank
	private  String  celular;

	public Persona() {
		super();
	}

	public Persona(Integer idPersona, @Size(max = 55) String nombres, @Size(max = 55) String apellidos,
			@Past Calendar fNacimiento, @Size(max = 10) String celular) {
		super();
		this.idPersona = idPersona;
		this.nombres = nombres;
		this.apellidos = apellidos;
		this.fNacimiento = fNacimiento;
		this.celular = celular;
	}

	public Integer getIdPersona() {
		return idPersona;
	}

	public void setIdPersona(Integer idPersona) {
		this.idPersona = idPersona;
	}

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public Calendar getfNacimiento() {
		return fNacimiento;
	}

	public void setfNacimiento(Calendar fNacimiento) {
		this.fNacimiento = fNacimiento;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}
}