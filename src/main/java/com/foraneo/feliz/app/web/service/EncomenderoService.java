package com.foraneo.feliz.app.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.foraneo.feliz.app.web.models.dao.IEncomendero;
import com.foraneo.feliz.app.web.models.entities.Encomendero;

@Service
public class EncomenderoService implements IEncomenderoService{
	
	@Autowired
	private IEncomendero dao;
	
	@Override
	@Transactional
	public void save(Encomendero encomendero) {
		dao.save(encomendero);
	}

	@Override
	@Transactional(readOnly = true)
	public Encomendero findById(Integer id) {
		return dao.findById(id).get();
	}

	@Override
	@Transactional
	public void delete(Integer id) {
		dao.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Encomendero> findAll() {
		return (List<Encomendero>)dao.findAll();
	}

}
