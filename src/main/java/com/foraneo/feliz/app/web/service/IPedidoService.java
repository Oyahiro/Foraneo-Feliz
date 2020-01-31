package com.foraneo.feliz.app.web.service;

import java.util.List;

import com.foraneo.feliz.app.web.reporting.LlaveValor;
import com.foraneo.feliz.app.web.models.entities.Pedido;

public interface IPedidoService {

	public void save(Pedido pedido);
	
	public Pedido findById(Integer id);
	
	public void delete(Integer id);

	public List<Pedido>findAll();
	
	public List<Pedido> findByCliente(Integer id);
	
	public List<Pedido> findByRestaurante(Integer id);
	
	public List<Pedido> findByEstado();
	
	public List<LlaveValor> countPlatillosMasPedidos();
	
	public List<LlaveValor> countEncomenderosMasEficientes();
	
}
