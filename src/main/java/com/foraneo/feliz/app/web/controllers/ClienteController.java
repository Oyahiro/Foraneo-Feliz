package com.foraneo.feliz.app.web.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.foraneo.feliz.app.web.models.entities.Cliente;
import com.foraneo.feliz.app.web.service.IClienteService;

@Controller
@RequestMapping(value="/cliente") //Las rutas se componen por el Mapping del Controlador + el Get Mapping del metodo
public class ClienteController {
	
	@Autowired //Para crear inyeccion de dependencias entre el controlador y el servicio
	private IClienteService service;
	
	@GetMapping(value="/create")
	public String create(Model model) {
		Cliente cliente = new Cliente();
		model.addAttribute("cliente", cliente);  //El model reemplaza al ViewBag
		model.addAttribute("tittle", "Registro de nuevo cliente");
		return "cliente/form";
	}
	
	@GetMapping(value="/retrieve/{id}")
	public String retrieve(@PathVariable(value="id") Integer id, Model model) {
		Cliente cliente = service.findById(id);
		model.addAttribute("cliente", cliente);
		model.addAttribute("tittle", "Detalle de cliente");
		return "cliente/card";
	}
	
	@GetMapping(value="/delete/{id}")
	public String delete(@PathVariable(value="id") Integer id, Model model, RedirectAttributes flash) {
		try {
			service.delete(id);
			flash.addFlashAttribute("message", "El registro se eliminó exitosamente");
		}catch(Exception ex) {
			flash.addFlashAttribute("message", "El registro no pudo eliminarse"); //El model sirve para la misma vista no si se cambia de vista
			ex.getStackTrace();
		}
		return "redirect:/cliente/list";
	}
	
	@GetMapping(value="/update/{id}")
	public String update(@PathVariable(value="id") Integer id, Model model) {
		Cliente cliente = service.findById(id);
		model.addAttribute("cliente", cliente);
		model.addAttribute("title", "Actualizar cliente");
		return "cliente/form";
	}
	
	@GetMapping(value="/list")
	public String list(Model model) {
		List<Cliente> list = service.findAll();
		model.addAttribute("list", list);
		return "cliente/list";
	}
	
	@PostMapping(value="save")
	public String save(Cliente cliente, Model model, RedirectAttributes flash) {
		try {
			service.save(cliente); //El service ya sabe si es nuevo o un antiguo y lo actualiza
			flash.addFlashAttribute("message", "Registro guardado con éxito");
		}catch(Exception ex) {
			flash.addFlashAttribute("error", "No se pudo guardar");
		}
		return "redirect:/cliente/list";
	}
}
