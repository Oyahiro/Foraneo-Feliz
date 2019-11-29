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

import com.foraneo.feliz.app.web.models.entities.Pedido;
import com.foraneo.feliz.app.web.service.IPedidoService;

@Controller
@RequestMapping(value="/pedido") //Las rutas se componen por el Mapping del Controlador + el Get Mapping del metodo
public class PedidoController {

	@Autowired //Para crear inyeccion de dependencias entre el controlador y el servicio
	private IPedidoService service;
	
	@GetMapping(value="/create")
	public String create(Model model) {
		Pedido pedido = new Pedido();
		model.addAttribute("pedido", pedido);  //El model reemplaza al ViewBag
		model.addAttribute("tittle", "Registro de nuevo pedido");
		return "pedido/form";
	}
	
	@GetMapping(value="/retrieve/{id}")
	public String retrieve(@PathVariable(value="id") Integer id, Model model) {
		Pedido pedido = service.findById(id);
		model.addAttribute("pedido", pedido);
		model.addAttribute("tittle", "Detalle de pedido");
		return "pedido/card";
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
		return "redirect:/pedido/list";
	}
	
	@GetMapping(value="/update/{id}")
	public String update(@PathVariable(value="id") Integer id, Model model) {
		Pedido pedido = service.findById(id);
		model.addAttribute("pedido", pedido);
		model.addAttribute("title", "Actualizar datos de pedido");
		return "pedido/form";
	}
	
	@GetMapping(value="/list")
	public String list(Model model) {
		List<Pedido> list = service.findAll();
		model.addAttribute("list", list);
		return "pedido/list";
	}
	
	@PostMapping(value="save")
	public String save(Pedido pedido, Model model, RedirectAttributes flash) {
		try {
			service.save(pedido); //El service ya sabe si es nuevo o un antiguo y lo actualiza
			flash.addFlashAttribute("message", "Registro guardado con éxito");
		}catch(Exception ex) {
			flash.addFlashAttribute("error", "No se pudo guardar");
		}
		return "redirect:/pedido/list";
	}
}
