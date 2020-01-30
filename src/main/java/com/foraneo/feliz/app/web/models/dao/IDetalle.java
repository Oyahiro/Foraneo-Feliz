package com.foraneo.feliz.app.web.models.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.foraneo.feliz.app.web.models.entities.Detalle;
import com.foraneo.feliz.app.web.models.entities.Pedido;

public interface IDetalle extends CrudRepository<Detalle, Integer>{
	
	@Query("SELECT P FROM Pedido P WHERE P.idpedido = :id")
	public Pedido findByPedido(Integer id);
}
