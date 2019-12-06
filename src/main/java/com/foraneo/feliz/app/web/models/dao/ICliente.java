package com.foraneo.feliz.app.web.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.foraneo.feliz.app.web.models.entities.Cliente;;

public interface ICliente extends CrudRepository<Cliente, Integer>{
	
	@Query("SELECT C FROM Cliente C WHERE LOWER(C.apellidos) LIKE CONCAT('%',?1,'%')")
	public List<Cliente> findByApellido(String filtro);
	
	public Cliente findByCorreo(String correo);
}
