package com.foraneo.feliz.app.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.foraneo.feliz.app.web.models.dao.IDetalle;
import com.foraneo.feliz.app.web.models.entities.Detalle;

@Service
public class DetalleService implements IDetalleService{
	
	@Autowired
	private IDetalle dao;
	
	@Override
	@Transactional
	public void save(Detalle detalle) {
		dao.save(detalle);
	}

	@Override
	@Transactional(readOnly = true)
	public Detalle findById(Integer id) {
		return dao.findById(id).get();
	}

	@Override
	@Transactional
	public void delete(Integer id) {
		dao.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Detalle> findAll() {
		return (List<Detalle>)dao.findAll();
	}

}
