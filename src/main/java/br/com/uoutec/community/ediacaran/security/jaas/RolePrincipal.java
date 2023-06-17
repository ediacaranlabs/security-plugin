package br.com.uoutec.community.ediacaran.security.jaas;

import java.security.Principal;

public class RolePrincipal implements Principal {

	private String name;
	  
	public RolePrincipal(String name) {
		this.name = name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

}
