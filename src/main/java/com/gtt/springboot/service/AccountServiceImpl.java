package com.gtt.springboot.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.gtt.springboot.dao.AppRoleRepository;
import com.gtt.springboot.dao.AppUserRepository;
import com.gtt.springboot.entities.AppRole;
import com.gtt.springboot.entities.AppUser;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {
	@Autowired 
	private AppUserRepository appUserRepository;  // injection par autowired
	@Autowired
	private AppRoleRepository appRoleRepository;		// injection par autowired
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public void setAppUserRepository(AppUserRepository appUserRepository) {
		this.appUserRepository = appUserRepository;
	}

	public void setAppRoleRepository(AppRoleRepository appRoleRepository) {
		this.appRoleRepository = appRoleRepository;
	}

	public void setbCryptPasswordEncoder(BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}
	
	// injection par construteur : identique à celle par autowired
	/*public AccountServiceImpl(AppUserRepository appUserRepository,AppRoleRepository appRoleRepository,BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.appRoleRepository = appRoleRepository;
		this.appRoleRepository = appRoleRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}*/

	@Override
	public AppUser saveUser(String username, String password, String confirmedPassword) {
		AppUser user = appUserRepository.findByUsername(username);
		if(user != null ) throw new RuntimeException("User Already Exist");
		if(!password.equals(confirmedPassword)) throw new RuntimeException("Please confirm your password");
		AppUser appUser = new AppUser();
		appUser.setUsername(username);
		appUser.setActivated(true);
		appUser.setPassword(bCryptPasswordEncoder.encode(password));
		appUserRepository.save(appUser);
		addRoleToUser(username, "USER");
		return appUser;
	}

	@Override
	public AppRole save(AppRole role) {
		return appRoleRepository.save(role);
	}

	@Override
	public AppUser loadUserByUsername(String username) {
		return appUserRepository.findByUsername(username);
	}

	@Override
	public void addRoleToUser(String username, String rolename) {
		AppUser appUser = appUserRepository.findByUsername(username);
		AppRole appRole = appRoleRepository.findByRoleName(rolename);
		appUser.getRoles().add(appRole);
		
		
		
	}

}
