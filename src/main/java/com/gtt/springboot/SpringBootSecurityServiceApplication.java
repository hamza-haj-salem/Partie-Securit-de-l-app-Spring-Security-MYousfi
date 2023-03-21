package com.gtt.springboot;

import java.util.stream.Stream;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.gtt.springboot.entities.AppRole;
import com.gtt.springboot.service.AccountService;

@SpringBootApplication
public class SpringBootSecurityServiceApplication {
	
	//Je teste cette app par l'outil advanced REST client

	public static void main(String[] args) {
		SpringApplication.run(SpringBootSecurityServiceApplication.class, args);
	}
	@Bean
	CommandLineRunner start( AccountService accountService) {
		return args ->{
			accountService.save(new AppRole(null,"USER"));
			accountService.save(new AppRole(null,"ADMIN"));
			Stream.of("user1","user2","user3","admin").forEach(un->{
				accountService.saveUser(un,"1234","1234");
			});
			
			accountService.addRoleToUser("admin", "ADMIN");
		};
	} 
	
	@Bean
	public BCryptPasswordEncoder getBCPE() {
		return new BCryptPasswordEncoder();
		
	}
	

}
