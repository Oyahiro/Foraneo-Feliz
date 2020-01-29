package com.foraneo.feliz.app.web.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.foraneo.feliz.app.web.models.entities.Pedido;;

public interface IPedido extends CrudRepository<Pedido, Integer>{

	@Query("SELECT P FROM Pedido P WHERE P.cliente.idPersona = :id")
	public List<Pedido> findByCliente(Integer id);
	
	/*@Query("SELECT P FROM Pedido P WHERE P.estado = :false")
	public List<Pedido> findByEstado();
	
	@Query("SELECT P FROM Pedido P WHERE P.cliente.idPersona = :id AND P.estado = :false")
	public List<Pedido> findByClienteyEstado(Integer id);*/
}
