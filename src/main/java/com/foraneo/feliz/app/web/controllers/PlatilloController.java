package com.foraneo.feliz.app.web.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.foraneo.feliz.app.web.models.entities.Pedido;
import com.foraneo.feliz.app.web.models.entities.Platillo;
import com.foraneo.feliz.app.web.models.entities.Restaurante;
import com.foraneo.feliz.app.web.models.entities.Usuario;
import com.foraneo.feliz.app.web.service.IPlatilloService;
import com.foraneo.feliz.app.web.service.IRestauranteService;
import com.foraneo.feliz.app.web.service.UsuarioService;

@Controller
@RequestMapping(value="/platillo") //Las rutas se componen por el Mapping del Controlador + el Get Mapping del metodo
public class PlatilloController {

	@Autowired //Para crear inyeccion de dependencias entre el controlador y el servicio
	private IPlatilloService service;
	
	@Autowired
	private UsuarioService srvUsuario;
	
	@Autowired
	private IRestauranteService reservice;
	
	@GetMapping(value="/create")
	public String create(Model model) {
		Platillo platillo = new Platillo();
		model.addAttribute("platillo", platillo);  //El model reemplaza al ViewBag
		model.addAttribute("title", "Registrar platillo");
		List<Restaurante> restaurantes = reservice.findAll();
        model.addAttribute("restaurantes", restaurantes); 
		return "platillo/form";
	}
	
	@GetMapping(value="/retrieve/{id}")
	public String retrieve(@PathVariable(value="id") Integer id, Model model) {
		Platillo platillo = service.findById(id);
		model.addAttribute("platillo", platillo);
		model.addAttribute("title", "Detalle de platillo");
		return "platillo/card";
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
		return "redirect:/platillo/list";
	}
	
	@GetMapping(value="/update/{id}")
	public String update(@PathVariable(value="id") Integer id, Model model) {
		Platillo platillo = service.findById(id);
		model.addAttribute("platillo", platillo);
		model.addAttribute("title", "Actualizar platillo");
		List<Restaurante> restaurantes = reservice.findAll();
        model.addAttribute("restaurantes", restaurantes); 
		return "platillo/form";
	}
	
	@GetMapping(value="/list")
	public String list(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetail = (UserDetails) auth.getPrincipal();
		Usuario usuario = srvUsuario.findByUsername(userDetail.getUsername());
		String rol = usuario.getRoles().get(0).getNombre();
		List<Platillo> list = null;
		model.addAttribute("title", "Lista de platillos");
		
		if(rol.equals("ROLE_RESTAURANTE")) 
			list = service.findByRestaurante(usuario.getRestaurante().getIdrestaurante());
		else 
			list = service.findAll();
		
		model.addAttribute("list", list);
		return "platillo/list";
	}
	
	@GetMapping(value = "/onlylist/{id}")
	public String onlylist(@PathVariable(value = "id") Integer id, Model model) {
		List<Platillo> list = service.findByRestaurante(id);
		model.addAttribute("list", list);
		return "platillo/onlylist";
	}
	
	@PostMapping(value="/save")
	public String save(@Valid Platillo platillo, BindingResult result, Model model, RedirectAttributes flash) {
		try {
			if(result.hasErrors()){
                model.addAttribute("title", "Registrar platillo");
                List<Restaurante> restaurantes = reservice.findAll();
                model.addAttribute("restaurantes", restaurantes);
                return "platillo/form";
            }
			else {
				model.addAttribute("title","Actualizar platillo");
			}
			
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			UserDetails userDetail = (UserDetails) auth.getPrincipal();
			Usuario usuario = srvUsuario.findByUsername(userDetail.getUsername());
			String rol = usuario.getRoles().get(0).getNombre();
			
			switch(rol) {
				case "ROLE_ADMIN":
					service.save(platillo); //El service ya sabe si es nuevo o un antiguo y lo actualiza
					flash.addFlashAttribute("message", "Registro guardado con éxito");
					break;
				case "ROLE_RESTAURANTE":
					Restaurante restaurante = usuario.getRestaurante();
					platillo.setRestaurante(restaurante);
					service.save(platillo);
					break;
			}
			
			service.save(platillo); //El service ya sabe si es nuevo o un antiguo y lo actualiza
			flash.addFlashAttribute("message", "Registro guardado con éxito");
		}catch(Exception ex) {
			flash.addFlashAttribute("error", "El registro no pudo ser guardado.");
		}
		return "redirect:/platillo/list";
	}
}
