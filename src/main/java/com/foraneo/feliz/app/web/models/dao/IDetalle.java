package com.foraneo.feliz.app.web.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.foraneo.feliz.app.web.models.entities.Detalle;

public interface IDetalle extends CrudRepository<Detalle, Integer>{
	
	@Query("SELECT D FROM Detalle D WHERE D.pedido.idpedido = :id")
	public List<Detalle> findByPedido(Integer id);
}
