package com.foraneo.feliz.app.web.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.foraneo.feliz.app.web.models.dao.IUsuario;
import com.foraneo.feliz.app.web.models.entities.Rol;
import com.foraneo.feliz.app.web.models.entities.Usuario;

@Service
public class UsuarioService implements UserDetailsService {
	
	@Autowired
	private IUsuario dao;
		
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = dao.findByNombre(username);
		
		if(usuario == null) {
			throw new UsernameNotFoundException("Usuario " + username + " no encontrado");
		}
		
		List<GrantedAuthority> roles = new ArrayList<GrantedAuthority>();
		for(Rol rol: usuario.getRoles()) {
			roles.add(new SimpleGrantedAuthority(rol.getNombre()));
		}
		
		if(roles.isEmpty()) {
			throw new UsernameNotFoundException("Usuario " + username + " no tiene roles asignados");
		}
			
		return new User(usuario.getNombre(), usuario.getContrasenia(), usuario.getHabilitado(), true, true, true, roles);
	}
	
	@Transactional
	public void save(Usuario usuario) {
		dao.save(usuario);		
	}
	
	@Transactional	
	public List<Usuario> findAll(){		
		return (List<Usuario>) dao.findAll();
	}
}
