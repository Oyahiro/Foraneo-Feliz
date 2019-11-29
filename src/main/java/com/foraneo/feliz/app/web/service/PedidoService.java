package com.foraneo.feliz.app.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.foraneo.feliz.app.web.models.dao.IPedido;
import com.foraneo.feliz.app.web.models.entities.Pedido;

@Service
public class PedidoService implements IPedidoService{
	
	@Autowired
	private IPedido dao;

	@Override
	@Transactional
	public void save(Pedido pedido) {
		dao.save(pedido);
	}

	@Override
	@Transactional(readOnly = true)
	public Pedido findById(Integer id) {
		return dao.findById(id).get();
	}

	@Override
	@Transactional
	public void delete(Integer id) {
		dao.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Pedido> findAll() {
		return (List<Pedido>)dao.findAll();
	}

}
