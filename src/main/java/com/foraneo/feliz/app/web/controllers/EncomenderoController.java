package com.foraneo.feliz.app.web.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.foraneo.feliz.app.web.models.entities.Encomendero;
import com.foraneo.feliz.app.web.service.IEncomenderoService;

@Controller
@RequestMapping(value="/encomendero") //Las rutas se componen por el Mapping del Controlador + el Get Mapping del metodo
public class EncomenderoController {

	@Autowired //Para crear inyeccion de dependencias entre el controlador y el servicio
	private IEncomenderoService service;
	
	@GetMapping(value="/create")
	public String create(Model model) {
		Encomendero encomendero = new Encomendero();
		model.addAttribute("encomendero", encomendero);  //El model reemplaza al ViewBag
		model.addAttribute("title", "Registrar encomendero");
		return "encomendero/form";
	}
	
	@GetMapping(value="/retrieve/{id}")
	public String retrieve(@PathVariable(value="id") Integer id, Model model) {
		Encomendero encomendero = service.findById(id);
		model.addAttribute("encomendero", encomendero);
		model.addAttribute("title", "Detalles de encomendero");
		return "encomendero/card";
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
		return "redirect:/encomendero/list";
	}
	
	@GetMapping(value="/update/{id}")
	public String update(@PathVariable(value="id") Integer id, Model model) {
		Encomendero encomendero = service.findById(id);
		model.addAttribute("encomendero", encomendero);
		model.addAttribute("title", "Actualizar encomendero");
		return "encomendero/form";
	}
	
	@GetMapping(value="/list")
	public String list(Model model) {
		List<Encomendero> list = service.findAll();
		model.addAttribute("list", list);
		model.addAttribute("title", "Lista de encomenderos");
		return "encomendero/list";
	}
	
	@PostMapping(value="/save")
	public String save(@Valid Encomendero encomendero, BindingResult result, Model model, RedirectAttributes flash) {
		try {
			if(result.hasErrors())
			{
				if(encomendero.getIdPersona() == null) {
					model.addAttribute("title","Registrar encomendero");					
				}
				else {
					model.addAttribute("title","Actualizar encomendero");
				}				
				return"encomendero/form";
			}
			service.save(encomendero); //El service ya sabe si es nuevo o un antiguo y lo actualiza
			flash.addFlashAttribute("message", "Registro guardado con éxito");
		}catch(Exception ex) {
			flash.addFlashAttribute("error", "No se pudo guardar");
		}
		return "redirect:/encomendero/list";
	}
}
