package com.foraneo.feliz.app.web.controllers;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.foraneo.feliz.app.web.models.entities.Cliente;
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
	private UsuarioService srvUsuario;
	
	@Autowired
	private IPlatilloService srvPlatillo;
	
	@GetMapping(value="/create")
	public String create(Model model) {
		Pedido pedido = new Pedido();
		
		//List<Cliente> clientes = srvCliente.findAll();
		List<Platillo> platillos = srvPlatillo.findAll();
		
		model.addAttribute("title", "Nuevo registro de pedido");
		model.addAttribute("pedido", pedido);  //El model reemplaza al ViewBag
		
		//model.addAttribute("clientes", clientes);
		model.addAttribute("platillos", platillos);	
		
		model.addAttribute("detalles", new ArrayList<Detalle>());
		
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
				System.out.println("************ hasErrores ************");
				System.out.println("Direccion: " + pedido.getDireccion());
				System.out.println("Error 0: " + result.getAllErrors().get(0).getDefaultMessage());
				
				System.out.println("Detalle: " + detalles.get(0).getPlatillo().getNombre());
				return "pedido/form";
			}

			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			UserDetails userDetail = (UserDetails) auth.getPrincipal();
			Usuario usuario = srvUsuario.findByUsername(userDetail.getUsername());
			Cliente cliente = usuario.getCliente();
			
			pedido.setTotal(0f);
			pedido.setDetalles(detalles);
			pedido.setCliente(cliente);
			
			System.out.println("ID: " + pedido.getIdpedido());
			System.out.println("Estado: " + pedido.getEstado());
			System.out.println("Total: " + pedido.getTotal());
			System.out.println("Direccion: " + pedido.getDireccion());
			System.out.println("Fecha: " + pedido.getFechapedido());
			
			System.out.println("Cliente: " + pedido.getCliente().getNombres());
			System.out.println("Detalle: " + pedido.getDetalles().get(0).getPlatillo().getNombre());
			
			srvPedido.save(pedido);
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
		float individual = platillo.getPrecio() * detail.getCantidad();
		detail.setPlatillo(platillo);
		detail.setTotalindividual(individual);
		detalles.add(detail);
		
		return detalles;		
	}
}
