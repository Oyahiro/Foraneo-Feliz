package com.foraneo.feliz.app.web.models.entities;

import java.io.Serializable;
import java.util.Calendar;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;

@Entity
@Table(name="PEDIDO")
public class Pedido implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name="IDPEDIDO")
	private Integer idpedido;
	
	@Column(name="ESTADO")
	@NotNull
	private Boolean estado;
	
	@Column(name="TOTAL")
	@NotNull
	private Float total;
	
	@Column(name="DIRECCION")
	@Size(max=50)
	@NotBlank
	private String direccion;
	
	@Column(name="FECHAPEDIDO")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Calendar fechapedido;
	
	@JoinColumn (name="IDENCOMENDERO", referencedColumnName="IDPERSONA")
	@ManyToOne
	private Encomendero encomendero;
	
	@JoinColumn (name="IDCLIENTE", referencedColumnName="IDPERSONA")
	@ManyToOne
	private Cliente cliente;
	
	@JsonIgnore
	@OneToMany(mappedBy="pedido", fetch=FetchType.LAZY)
	private List<Detalle> detalle;

	public Pedido() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Integer getIdpedido() {
		return idpedido;
	}

	public void setIdpedido(Integer idpedido) {
		this.idpedido = idpedido;
	}

	public Boolean getEstado() {
		return estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

	public Float getTotal() {
		return total;
	}

	public void setTotal(Float total) {
		this.total = total;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public Calendar getFechapedido() {
		return fechapedido;
	}

	public void setFechapedido(Calendar fechapedido) {
		this.fechapedido = fechapedido;
	}

	public Encomendero getEncomendero() {
		return encomendero;
	}

	public void setEncomendero(Encomendero encomendero) {
		this.encomendero = encomendero;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<Detalle> getDetalle() {
		return detalle;
	}

	public void setDetalle(List<Detalle> detalle) {
		this.detalle = detalle;
	}
}
