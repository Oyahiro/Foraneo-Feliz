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
		
		.antMatchers("/cliente/**", "/encomendero/**").permitAll()
		.antMatchers("/platillo/**", "/restaurante/**").permitAll()
		
		/*.antMatchers("/cliente/**", "/encomendero/**").hasAnyRole("ADMIN")
		.antMatchers("/platillo/**", "/restaurante/**").permitAll()
		
		.antMatchers("/platillo/form", "/restaurante/form").hasAnyRole("ADMIN", "RESTAURANTE")
		.antMatchers("/platillo/list", "/restaurante/list").permitAll()
		.antMatchers("/platillo/card", "/restaurante/card").permitAll()*/
		
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
