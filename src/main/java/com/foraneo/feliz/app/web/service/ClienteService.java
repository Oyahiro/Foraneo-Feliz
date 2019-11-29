package com.foraneo.feliz.app.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.foraneo.feliz.app.web.models.dao.ICliente;
import com.foraneo.feliz.app.web.models.entities.Cliente;

@Service
public class ClienteService implements IClienteService{
	
	@Autowired
	private ICliente dao;
	
	@Override
	@Transactional
	public void save(Cliente cliente) {
		dao.save(cliente);
	}

	@Override
	@Transactional(readOnly = true)
	public Cliente findById(Integer id) {
		return dao.findById(id).get();
	}

	@Override
	@Transactional
	public void delete(Integer id) {
		dao.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Cliente> findAll() {
		return (List<Cliente>)dao.findAll();
	}

}
