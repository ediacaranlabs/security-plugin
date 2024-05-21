package br.com.uoutec.community.ediacaran.security.jaas;

import java.security.Principal;

public class AuthorizationPrincipal implements Principal {

	private final String authorization;
	  
	public AuthorizationPrincipal(String value) {
		this.authorization = value;
	}

	@Override
	public String getName() {
		return authorization;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((authorization == null) ? 0 : authorization.hashCode());
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
		AuthorizationPrincipal other = (AuthorizationPrincipal) obj;
		if (authorization == null) {
			if (other.authorization != null)
				return false;
		} else if (!authorization.equals(other.authorization))
			return false;
		return true;
	}

}
