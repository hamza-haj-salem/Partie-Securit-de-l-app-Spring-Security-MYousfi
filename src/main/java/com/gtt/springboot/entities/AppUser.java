package com.gtt.springboot.entities;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Entity
public class AppUser {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(unique=true)
	private String username;
	@JsonProperty(access = Access.WRITE_ONLY) // pour n'est pas afficher le mdp 
	//par expl lorsque j'ajoute un user aver Advanced REST client il affiche tous les cordonnée de user
	// et je veux que le mdp ne soit pas affichées.
	private String password;
	private boolean activated ;
	@ManyToMany(fetch = FetchType.EAGER) // EAGER :lorsque je demande à jpa a charger un user .. automatiquement il va charger ses roles
	private Collection<AppRole> roles = new ArrayList<>();
	
	
	public AppUser() {
		super();
	}
	
	public AppUser(Long id, String username, String password, boolean activated, Collection<AppRole> roles) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.activated = activated;
		this.roles = roles;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public boolean isActivated() {
		return activated;
	}
	public void setActivated(boolean activated) {
		this.activated = activated;
	}
	public Collection<AppRole> getRoles() {
		return roles;
	}
	public void setRoles(Collection<AppRole> roles) {
		this.roles = roles;
	}

	@Override
	public String toString() {
		return "AppUser [id=" + id + ", username=" + username + ", password=" + password + ", activated=" + activated
				+ ", roles=" + roles + "]";
	}
	
	
	

}
