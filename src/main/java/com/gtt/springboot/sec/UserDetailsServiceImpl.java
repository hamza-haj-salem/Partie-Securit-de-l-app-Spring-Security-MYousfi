package com.gtt.springboot.sec;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.gtt.springboot.entities.AppUser;
import com.gtt.springboot.service.AccountServiceImpl;

@Service // cad que cette class sera chargée au demarrage de l'appli 
public class UserDetailsServiceImpl implements UserDetailsService{
	
	@Autowired
	private AccountServiceImpl accountServiceImpl;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		 		//notre user propre : classe qu'on a cree
		AppUser appUser = accountServiceImpl.loadUserByUsername(username);
		if(appUser ==null) throw new UsernameNotFoundException("Invalid User");
		Collection<GrantedAuthority>  authorities = new ArrayList<>();
		appUser.getRoles().forEach(r -> {
			authorities.add(new SimpleGrantedAuthority(r.getRoleName()));
		});
				// user propre à s^pring
		return new User(appUser.getUsername(),appUser.getPassword(),authorities);
	}

}
