package com.foraneo.feliz.app.web.models.entities;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.annotation.Transient;

import com.sun.istack.NotNull;

@Entity
@Table(name="DETALLE")
public class Detalle implements Serializable{

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name="IDDETALLE")
	private  Integer iddetalle;
	
	@Column(name="CANTIDAD")
	@NotNull
	private Integer cantidad;
	
	@Column(name="TOTALINDIVIDUAL")
	private Float totalindividual;
	
	@JoinColumn (name="IDPLATILLO", referencedColumnName="IDPLATILLO")
	@ManyToOne
	private Platillo platillo;

	@Transient
	private Integer platilloid;
	
	public Integer getPlatilloid() {
		return platilloid;
	}

	public void setPlatilloid(Integer platilloid) {
		this.platilloid = platilloid;
	}

	public Detalle() {
		super();
	}

	public Integer getIddetalle() {
		return iddetalle;
	}

	public void setIddetalle(Integer iddetalle) {
		this.iddetalle = iddetalle;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public Float getTotalindividual() {
		return totalindividual;
	}

	public void setTotalindividual(Float totalindividual) {
		this.totalindividual = totalindividual;
	}

	public Platillo getPlatillo() {
		return platillo;
	}

	public void setPlatillo(Platillo platillo) {
		this.platillo = platillo;
	}
}
