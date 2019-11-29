package com.foraneo.feliz.app.web.service;

import java.util.List;

import com.foraneo.feliz.app.web.models.entities.Detalle;

public interface IDetalleService {
	
	public void save(Detalle detalle);
	
	public Detalle findById(Integer id);
	
	public void delete(Integer id);

	public List<Detalle>findAll();
	
}
