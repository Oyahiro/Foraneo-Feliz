package com.foraneo.feliz.app.web.service;

import java.util.List;

import com.foraneo.feliz.app.web.models.entities.Restaurante;

public interface IRestauranteService {

	public void save(Restaurante restaurante);
	
	public Restaurante findById(Integer id);
	
	public void delete(Integer id);

	public List<Restaurante>findAll();
	
}
