package com.gtt.springboot.service;

import com.gtt.springboot.entities.AppRole;
import com.gtt.springboot.entities.AppUser;

public interface AccountService {
	
	public AppUser saveUser(String username,String password, String confirmedPassword);
	public AppRole save(AppRole role);
	
	public AppUser loadUserByUsername(String username);
	public void addRoleToUser(String username, String rolename);
	

}
