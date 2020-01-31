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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.foraneo.feliz.app.web.reporting.LlaveValor;
import com.foraneo.feliz.app.web.models.entities.Cliente;
import com.foraneo.feliz.app.web.models.entities.Detalle;
import com.foraneo.feliz.app.web.models.entities.Encomendero;
import com.foraneo.feliz.app.web.models.entities.Pedido;
import com.foraneo.feliz.app.web.models.entities.Platillo;
import com.foraneo.feliz.app.web.models.entities.Restaurante;
import com.foraneo.feliz.app.web.models.entities.Usuario;
import com.foraneo.feliz.app.web.service.IPlatilloService;
import com.foraneo.feliz.app.web.service.IRestauranteService;
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
	
	@Autowired
	private IRestauranteService srvRestaurante;
	
	@GetMapping(value="/create")
	public String create(Model model) {
		Pedido pedido = new Pedido();
		
		List<Restaurante> restaurantes = srvRestaurante.findAll();
		
		model.addAttribute("title", "Nuevo registro de pedido");
		model.addAttribute("pedido", pedido);  //El model reemplaza al ViewBag
		
		model.addAttribute("restaurantes", restaurantes);
		model.addAttribute("detalles", new ArrayList<Detalle>());
		
		return "pedido/form";
	}
	
	@GetMapping(value="/retrieve/{id}")
	public String retrieve(@PathVariable(value="id") Integer id, Model model) {
		Pedido pedido = srvPedido.findById(id);
		model.addAttribute("pedido", pedido);
		model.addAttribute("title", pedido.getDetalles().get(0).getPlatillo().getRestaurante().getNombre());
		
		return "pedido/card";
	}
	
	@GetMapping(value="/delete/{id}")
	public String delete(@PathVariable(value="id") Integer id, Model model, RedirectAttributes flash) {
		try {
			Pedido p = srvPedido.findById(id);
			if(!p.getEstado()) {
				srvPedido.delete(id);
				flash.addFlashAttribute("message", "El registro se eliminó exitosamente");
			}else {
				flash.addFlashAttribute("message", "No es posible eliminar un pedido que ya está en camino");
			}
		}catch(Exception ex) {
			flash.addFlashAttribute("message", "El registro no pudo eliminarse"); //El model sirve para la misma vista no si se cambia de vista
			ex.getStackTrace();
		}
		return "redirect:/pedido/list";
	}
	
	@GetMapping(value="/update/{id}")
	public String update(@PathVariable(value="id") Integer id, Model model) {
		Pedido pedido = srvPedido.findById(id);
		
		if(!pedido.getEstado()) {
			List<Restaurante> restaurantes = srvRestaurante.findAll();
			
			model.addAttribute("pedido", pedido);
			model.addAttribute("title", "Actualizar datos de pedido");
			model.addAttribute("restaurantes", restaurantes);	
			model.addAttribute("detalles", pedido.getDetalles());
			return "pedido/form";
		}else {
			return "redirect:/pedido/list";
		}
	}
	
	@GetMapping(value="/list")
	public String list(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetail = (UserDetails) auth.getPrincipal();
		Usuario usuario = srvUsuario.findByUsername(userDetail.getUsername());
		String rol = usuario.getRoles().get(0).getNombre();
		List<Pedido> list = null;
		model.addAttribute("title", "Lista de pedidos");
		
		switch(rol) {
			case "ROLE_USER":
				list = srvPedido.findByCliente(usuario.getCliente().getIdPersona());
				
				if(!list.isEmpty())
					list.get(0).getDetalles().get(0).getPlatillo().getRestaurante().getIdrestaurante();
				
				model.addAttribute("list", list);
				return "pedido/list";
			
			case "ROLE_RESTAURANTE":
				Restaurante rest = usuario.getRestaurante();
				list = srvPedido.findByRestaurante(rest.getIdrestaurante());
				model.addAttribute("list", list);
				return "pedido/list";
				
			case "ROLE_ENCOMENDERO":
				list = srvPedido.findByEstado();
				model.addAttribute("list", list);
				return "pedido/list";
				
			case "ROLE_ADMIN":
				list = srvPedido.findAll();
				model.addAttribute("list", list);
				return "pedido/list";
			
			default:
				model.addAttribute("list", list);
				return "pedido/list";
		}
	}
	
	@PostMapping(value="save")
	public String save(@Valid Pedido pedido, BindingResult result, Model model, RedirectAttributes mensaje,
			@SessionAttribute(value="detalles") List<Detalle> detalles, SessionStatus session) {
		try {
			if(result.hasErrors()) {
				return "pedido/form";
			}

			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			UserDetails userDetail = (UserDetails) auth.getPrincipal();
			Usuario usuario = srvUsuario.findByUsername(userDetail.getUsername());
			Cliente cliente = usuario.getCliente();
			
			float total=0;
			for(Detalle d:detalles) 
				total+=d.getTotalindividual();
			
			pedido.setTotal(total);
			pedido.setDetalles(detalles);
			pedido.setCliente(cliente);
			pedido.setEstado(false);
			
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
		platillo.getRestaurante().setUserRest(null);
		detail.setPlatillo(platillo);
		detail.setTotalindividual(individual);
		detalles.add(detail);
		
		return detalles;		
	}
	
	@PostMapping(value="/delDetail", produces="application/json")
	public @ResponseBody List<Detalle> delDetail(@RequestBody Integer id, 
			@SessionAttribute(value="detalles") List<Detalle> detalles) {
		detalles.remove(detalles.get(id));
		return detalles;		
	}
	
	@PostMapping(value="/cargarLista", produces="application/json")
	public @ResponseBody List<Detalle> cargarLista(@SessionAttribute(value="detalles") List<Detalle> detalles) {
		for(Detalle de:detalles) {
			de.getPlatillo().getRestaurante().setUserRest(null);
		}
		
		return detalles;		
	}
	
	@PostMapping(value="/platillos", produces="application/json")
	public @ResponseBody List<Platillo> platillos(@RequestBody Integer id) {
		List<Platillo> nplatillos = srvPlatillo.findByRestaurante(id);
		
		//Si no se hace esto aparece el error parsererror en el javascript
		//for(Platillo p: nplatillos)
			//p.getRestaurante().setUserRest(null);
		
		return nplatillos;
	}
	
	@GetMapping(value="/atender/{id}")
	public String atender(@PathVariable(value="id") Integer id, Model model) {
		Pedido pedido = srvPedido.findById(id);
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetail = (UserDetails) auth.getPrincipal();
		Usuario usuario = srvUsuario.findByUsername(userDetail.getUsername());
		Encomendero encomendero = usuario.getEncomendero();
		
		pedido.setEncomendero(encomendero);
		pedido.setEstado(true);
		
		srvPedido.save(pedido);
		
		return "redirect:/pedido/retrieve/"+pedido.getIdpedido();
	}
	
	@GetMapping(value = "/report")
	public String report(Model model) {		
		model.addAttribute("title", "Reportes de ventas");
		return "pedido/report";
	}
	
	@GetMapping(value = "/loadData", produces="application/json")
	public @ResponseBody List<LlaveValor> loadData() {	
		List<LlaveValor> lista = srvPedido.countPlatillosMasPedidos();
		return lista;
	}

	@GetMapping(value = "/loadData2", produces="application/json")
	public @ResponseBody List<LlaveValor> loadData2() {
		List<LlaveValor> lista = srvPedido.countEncomenderosMasEficientes();
		return lista;
	}
	
}
