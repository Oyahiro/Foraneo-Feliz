package com.foraneo.feliz.app.web.models.entities;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="CLIENTE")
public class Cliente extends Persona implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Column(name="CORREO")
	@Size(max=30)
	@NotBlank
	@Email
	private String correo;
	
	@Column(name="FREGISTRO")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Calendar fRegistro;
	
	@JsonIgnore
	@OneToMany(mappedBy="cliente", fetch=FetchType.LAZY)
	private List<Pedido> pedido;

	public Cliente() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}
	
	public Calendar getfRegistro() {
		return fRegistro;
	}

	public void setfRegistro(Calendar fRegistro) {
		this.fRegistro = fRegistro;
	}
}
