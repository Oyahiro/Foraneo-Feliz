package com.foraneo.feliz.app.web.service;

import java.util.List;

import com.foraneo.feliz.app.web.models.entities.Encomendero;

public interface IEncomenderoService {

	public void save(Encomendero encomendero);
	
	public Encomendero findById(Integer id);
	
	public void delete(Integer id);

	public List<Encomendero>findAll();
	
}
