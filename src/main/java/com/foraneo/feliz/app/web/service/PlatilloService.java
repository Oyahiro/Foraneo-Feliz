package com.foraneo.feliz.app.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.foraneo.feliz.app.web.models.dao.IPlatillo;
import com.foraneo.feliz.app.web.models.entities.Platillo;

@Service
public class PlatilloService implements IPlatilloService{
	
	@Autowired
	private IPlatillo dao;

	@Override
	@Transactional
	public void save(Platillo platillo) {
		dao.save(platillo);
	}

	@Override
	@Transactional(readOnly = true)
	public Platillo findById(Integer id) {
		return dao.findById(id).get();
	}

	@Override
	@Transactional
	public void delete(Integer id) {
		dao.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Platillo> findAll() {
		return (List<Platillo>)dao.findAll();
	}

}
