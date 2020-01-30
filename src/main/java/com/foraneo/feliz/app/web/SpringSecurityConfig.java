package com.foraneo.feliz.app.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.foraneo.feliz.app.web.security.LoginSuccessHandler;
import com.foraneo.feliz.app.web.service.UsuarioService;

@EnableGlobalMethodSecurity(securedEnabled=true)
@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private LoginSuccessHandler successHandler;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	public void configurerGlobal(AuthenticationManagerBuilder builder) throws Exception {
		builder.userDetailsService(usuarioService).passwordEncoder(passwordEncoder);	
	}
	
	public void configure(HttpSecurity http) throws Exception{
		http.authorizeRequests()
		.antMatchers("/", "/css/**", "/js/**", "/img/**", "/images/**").permitAll()
		
		/*.antMatchers("/cliente/**").permitAll()
		.antMatchers("/platillo/**").permitAll()
		.antMatchers("/encomendero/**").permitAll()
		.antMatchers("/restaurante/**").permitAll()
		.antMatchers("/pedido/**").permitAll()*/
		
		.antMatchers("/platillo/create").hasAnyRole("ADMIN", "RESTAURANTE")
		.antMatchers("/platillo/save").hasAnyRole("ADMIN", "RESTAURANTE")
		.antMatchers("/platillo/list").permitAll()
		.antMatchers("/platillo/onlylist/**").permitAll()
		.antMatchers("/platillo/retrieve/**").permitAll()
		
		.antMatchers("/restaurante/create").hasAnyRole("ADMIN")
		.antMatchers("/restaurante/save").hasAnyRole("ADMIN")
		.antMatchers("/restaurante/list").permitAll()
		.antMatchers("/restaurante/retrieve/**").permitAll()
		
		.antMatchers("/cliente/create").anonymous()
		.antMatchers("/cliente/save").anonymous()
		.antMatchers("/cliente/list").hasAnyRole("ADMIN")
		.antMatchers("/cliente/retrieve/**").hasAnyRole("ADMIN")
		
		.antMatchers("/encomendero/create").hasAnyRole("ADMIN")
		.antMatchers("/encomendero/save").hasAnyRole("ADMIN")
		.antMatchers("/encomendero/list").hasAnyRole("ADMIN")
		.antMatchers("/encomendero/retrieve/**").hasAnyRole("ADMIN")
		
		.antMatchers("/pedido/create").hasAnyRole("USER")
		.antMatchers("/pedido/save").hasAnyRole("USER")
		.antMatchers("/pedido/addDetail").hasAnyRole("USER")
		.antMatchers("/pedido/delDetail").hasAnyRole("USER")
		.antMatchers("/pedido/list").hasAnyRole("ENCOMENDERO", "USER", "RESTAURANTE", "ADMIN")
		.antMatchers("/pedido/onlylist/**").hasAnyRole("ENCOMENDERO", "USER", "RESTAURANTE", "ADMIN")
		.antMatchers("/pedido/report").hasAnyRole("ADMIN")
		.antMatchers("/pedido/loadData").hasAnyRole("ADMIN")
		
		.antMatchers("/usuario/**").permitAll()
		
		.anyRequest().authenticated()
		.and()
			.formLogin().successHandler(successHandler)
			.loginPage("/login").permitAll()
		.and()
			.logout().permitAll()
		.and()
			.exceptionHandling().accessDeniedPage("/error_403")
		.and()
			.csrf().ignoringAntMatchers("/h2-console/**")
		.and()
			.headers().frameOptions().sameOrigin();
	}
}
