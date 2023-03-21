package com.gtt.springboot.sec;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.management.RuntimeErrorException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gtt.springboot.entities.AppUser;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter{
	
	
	private AuthenticationManager authenticationManager;
	
	public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response){
		try {
			AppUser appUser = new ObjectMapper().readValue(request.getInputStream(), AppUser.class);
			return authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(appUser.getUsername(), appUser.getPassword()));
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}
	
	//Si; aprés recup de username , mdp et comparaison avec données dans bd  ; Tous va bien
	// il fait appel à cette méthode.
	//On va profiter à cette meth pour  generer un webToken
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		User user = (User) authResult.getPrincipal(); // il va retourne l'utilisateur authentifié
		//maintenat j'ai l'utilisateur
		List<String> roles = new ArrayList<>();
		authResult.getAuthorities().forEach(a -> {
			roles.add(a.getAuthority());
		});
		String jwt = JWT.create()
				.withIssuer(request.getRequestURI())
				.withSubject(user.getUsername())
				.withArrayClaim("role", roles.toArray(new String[roles.size()]))
				.withExpiresAt(new Date(System.currentTimeMillis()+SecurityParams.EXPIRATION))
				.sign(Algorithm.HMAC256(SecurityParams.SECRET));//hamza@hajsalem.com : secret qu'il faut le proteger
			response.addHeader(SecurityParams.JWT_HEADER_NAME, jwt); //ajouter jwt à la header pour l'envoyer
	}
	
	

}
