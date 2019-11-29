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
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="CLIENTE")
public class Cliente extends Persona implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name="CORREO")
	@Size(max=30)
	private String correo;
	
	@Column(name="FREGISTRO")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern="dd-MM-yyyy")
	private Calendar fRegistro;
	
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
