package com.gtt.springboot.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.gtt.springboot.entities.AppUser;
import com.gtt.springboot.service.AccountService;

import lombok.Data;

@RestController
public class UserController {
	// JE TEST AVEC Advanced REST client : EN LIGNE CLIENT
	
	@Autowired
	private AccountService accountService;
	
	@PostMapping("/register")   // les donnees saisie par user ne doivent pas etre comme ca pour
							// cela j'ai cree une class UserForm
						
	public AppUser register(/*String username , String password , String confirmedPassword*/
					@RequestBody UserForm userForm) {
		return accountService.saveUser(userForm.getUsername(),userForm.getPassword(),userForm.getConfirmedPassword());
		
	}

}

class UserForm{
	private String username ;
	private String password ;
	private  String confirmedPassword;
		
	public UserForm() {
		super();
	}

	public UserForm(String username, String password, String confirmedPassword) {
		super();
		this.username = username;
		this.password = password;
		this.confirmedPassword = confirmedPassword;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmedPassword() {
		return confirmedPassword;
	}

	public void setConfirmedPassword(String confirmedPassword) {
		this.confirmedPassword = confirmedPassword;
	} 
	
	
	
	
}
