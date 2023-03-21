package com.gtt.springboot.sec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.web.servlet.oauth2.resourceserver.JwtDsl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
				// normalement on injecte l'inetrface mais j'inject le servImp .. inter fait un pblm
	@Autowired //On a besoin de créer une classe qui implemente l'interface UserDetailsService (On va la creer dans le package sec)
	private UserDetailsServiceImpl userdetailService;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	//	Lorsque il y a un user qui veux connecter On va appele une interface qui s'appel userdetailService
		// et qu'elle meeme va appele la methode loadUserByUsername()
												//On va utiliser le cryptage de password saisie par user
		auth.userDetailsService(userdetailService).passwordEncoder(bCryptPasswordEncoder);
		
		}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//http.formLogin(); // haka une auth basé sur session
		
		 	//Cross-site Request Forgery est une attack ** 
		    //spring Sécurité fournit une protection CSRF par défaut
		http.csrf().disable(); // on va activer l'auth basé sur Token
		//on va plus utiliser les session .. mais la session pour nous c'est la jwt :
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); //auth de type stateless
								// on dit à spring ce n'est pas oblig d'utiliser les session et 
								//dans ce cas là c'est à nous de gérer les session par jwt
		http.authorizeRequests().antMatchers("/login/**","/register").permitAll();//pour le login on va le permettre
		http.authorizeRequests().antMatchers("/appUsers/**","/appRoles/**").hasAuthority("ADMIN");
		http.authorizeRequests().anyRequest().authenticated();
		//LE FILTRE :
		http.addFilter(new JWTAuthenticationFilter(authenticationManager()));//on
		// va ajouter un filter qui va intercepter les requets
		// before  : filter qui va se placer en 1er plan **
		//il ya plusiers filters qu'on peurt les ajouter 
		http.addFilterBefore(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
		
	}
	
	

}
