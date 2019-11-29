package com.foraneo.feliz.app.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.foraneo.feliz.app.web.models.dao.IRestaurante;
import com.foraneo.feliz.app.web.models.entities.Restaurante;

@Service
public class RestauranteService implements IRestauranteService{
	
	@Autowired
	private IRestaurante dao;

	@Override
	@Transactional
	public void save(Restaurante restaurante) {
		dao.save(restaurante);
	}

	@Override
	@Transactional(readOnly = true)
	public Restaurante findById(Integer id) {
		return dao.findById(id).get();
	}

	@Override
	@Transactional
	public void delete(Integer id) {
		dao.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Restaurante> findAll() {
		return (List<Restaurante>)dao.findAll();
	}

}
