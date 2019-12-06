package com.foraneo.feliz.app.web.service;

import java.util.List;

import com.foraneo.feliz.app.web.models.entities.Cliente;

public interface IClienteService {

	public void save(Cliente cliente);
	
	public Cliente findById(Integer id);
	
	public void delete(Integer id);

	public List<Cliente>findAll();
	
	public List<Cliente> findByApellido(String filtro);
	
	public Cliente findByCorreo(String correo);
}
