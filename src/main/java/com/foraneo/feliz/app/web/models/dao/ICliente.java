package com.foraneo.feliz.app.web.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.foraneo.feliz.app.web.models.entities.Cliente;;

public interface ICliente extends CrudRepository<Cliente, Integer>{

}
