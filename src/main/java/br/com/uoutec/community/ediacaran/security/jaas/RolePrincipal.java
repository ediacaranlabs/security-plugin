package br.com.uoutec.community.ediacaran.security.jaas;

import java.security.Principal;

public class RolePrincipal implements Principal {

	private final String role;
	  
	public RolePrincipal(String value) {
		this.role = value;
	}

	@Override
	public String getName() {
		return role;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((role == null) ? 0 : role.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RolePrincipal other = (RolePrincipal) obj;
		if (role == null) {
			if (other.role != null)
				return false;
		} else if (!role.equals(other.role))
			return false;
		return true;
	}

}
