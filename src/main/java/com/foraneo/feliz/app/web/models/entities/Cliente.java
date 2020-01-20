package com.foraneo.feliz.app.web.models.entities;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
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
	private List<Pedido> pedidos;

	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IDUSUARIO")
    private Usuario user;
	
	public Usuario getUser() {
		return user;
	}

	public void setUser(Usuario user) {
		this.user = user;
	}

	public Cliente() {
		super();
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

	public List<Pedido> getPedidos() {
		return pedidos;
	}

	public void setPedidos(List<Pedido> pedidos) {
		this.pedidos = pedidos;
	}
	
	@PrePersist // Se llama al método antes de que la entidad se inserte en la base de datos
    public void prePersist() {
        fRegistro = Calendar.getInstance();
    }
}
