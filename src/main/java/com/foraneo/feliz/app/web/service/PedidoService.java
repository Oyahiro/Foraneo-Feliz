package com.foraneo.feliz.app.web.service;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.foraneo.feliz.app.web.models.dao.IPedido;
import com.foraneo.feliz.app.web.models.entities.Pedido;
import com.foraneo.feliz.app.web.reporting.LlaveValor;

@Service
public class PedidoService implements IPedidoService{
	
	@Autowired
	private IPedido dao;
	
	@PersistenceContext
	private EntityManager em; //Es la instancia de persistencia con la BDD

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

	@Override
	public List<Pedido> findByCliente(Integer id) {
		return dao.findByCliente(id);
	}

		/*@Override
	public List<Pedido> findByEstado() {
		return dao.findByEstado();
	}

	@Override
	public List<Pedido> findByClienteyEstado(Integer id) {
		return dao.findByClienteyEstado(id);
	}*/

	@Override
	public List<LlaveValor> countPlatillosMasPedidos() {
		StoredProcedureQuery consulta = em.createStoredProcedureQuery("ContarPlatillosPedidos");
		consulta.execute();
		List<Object[]> datos = consulta.getResultList();
		
		return datos.stream()
				.map(r -> new LlaveValor( r[0].toString(), 
						new BigInteger(r[1].toString())))
				.collect(Collectors.toList());
	}

	@Override
	public List<LlaveValor> countEncomenderosMasEficientes() {
		StoredProcedureQuery consulta = em.createStoredProcedureQuery("ContarEncomenderosEntregas");
		consulta.execute();
		List<Object[]> datos = consulta.getResultList();
		
		return datos.stream()
				.map(r -> new LlaveValor( r[0].toString(), 
						new BigInteger(r[1].toString())))
				.collect(Collectors.toList());
	}
}
