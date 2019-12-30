package com.foraneo.feliz.app.web.service;

import java.util.List;

import com.foraneo.feliz.app.web.models.entities.Platillo;

public interface IPlatilloService {

	public void save(Platillo platillo);
	
	public Platillo findById(Integer id);
	
	public void delete(Integer id);

	public List<Platillo>findAll();
	
	public List<Platillo> findByRestaurante(Integer id);
	
}
