package com.foraneo.feliz.app.web.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.foraneo.feliz.app.web.models.entities.Platillo;

public interface IPlatillo extends CrudRepository<Platillo, Integer>{

	@Query("SELECT P FROM Platillo P WHERE P.restaurante.idrestaurante = :id")
	public List<Platillo> findByRestaurante(Integer id);
}
