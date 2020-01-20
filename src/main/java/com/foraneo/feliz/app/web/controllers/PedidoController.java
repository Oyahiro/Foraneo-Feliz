package com.foraneo.feliz.app.web.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.foraneo.feliz.app.web.models.entities.Detalle;
import com.foraneo.feliz.app.web.models.entities.Pedido;
import com.foraneo.feliz.app.web.models.entities.Platillo;
import com.foraneo.feliz.app.web.models.entities.Usuario;
import com.foraneo.feliz.app.web.service.IPlatilloService;
import com.foraneo.feliz.app.web.service.PedidoService;
import com.foraneo.feliz.app.web.service.UsuarioService;

@Controller
@RequestMapping(value="/pedido") //Las rutas se componen por el Mapping del Controlador + el Get Mapping del metodo
@SessionAttributes({"detalles"})
public class PedidoController {

	@Autowired //Para crear inyeccion de dependencias entre el controlador y el servicio
	private PedidoService srvPedido;
	
	@Autowired
	private UsuarioService srvCliente;
	
	@Autowired
	private IPlatilloService srvPlatillo;
	
	@GetMapping(value="/create")
	public String create(Model model/*, @PathVariable(value="id") Integer id*/) {
		Pedido pedido = new Pedido();
		//pedido.setPersonaid(id);
		List<Usuario> clientes = srvCliente.findAll();
		List<Platillo> platillos = srvPlatillo.findAll();
		
		model.addAttribute("pedido", pedido);  //El model reemplaza al ViewBag
		model.addAttribute("detalles", new ArrayList<Detalle>());
		model.addAttribute("title", "Nuevo registro de pedido");
		model.addAttribute("clientes", clientes);
		model.addAttribute("platillos", platillos);	
		
		return "pedido/form";
	}
	
	/*@GetMapping(value="/retrieve/{id}")
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
	}*/
	
	@GetMapping(value="/list")
	public String list(Model model) {
		List<Pedido> list = srvPedido.findAll();
		model.addAttribute("list", list);
		return "pedido/list";
	}
	
	@PostMapping(value="save")
	public String save(@Valid Pedido pedido, BindingResult result, Model model, RedirectAttributes mensaje,
			@SessionAttribute(value="detalles") List<Detalle> detalles, SessionStatus session) {
		try {
			if(result.hasErrors()) {
				return "pedido/form";
			}


			//pedido.setCliente(cliente);
			pedido.setDetalles(detalles);
			
			srvPedido.save(pedido); //El service ya sabe si es nuevo o un antiguo y lo actualiza
			session.setComplete();
		}catch(Exception ex) {
			//flash.addFlashAttribute("error", "No se pudo guardar");
		}
		return "redirect:/pedido/list";
	}
	
	@PostMapping(value="/addDetail", produces="application/json")
	public @ResponseBody List<Detalle> addDetail(@RequestBody Detalle detail, 
			@SessionAttribute(value="detalles") List<Detalle> detalles) {		
		Platillo platillo = srvPlatillo.findById(detail.getPlatilloid());
		detail.setPlatillo(platillo);		
		detalles.add(detail);		
		return detalles;		
	}
}
