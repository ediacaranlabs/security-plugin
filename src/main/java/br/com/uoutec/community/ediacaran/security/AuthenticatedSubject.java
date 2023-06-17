package br.com.uoutec.community.ediacaran.security;

import br.com.uoutec.community.ediacaran.security.jaas.Authentication;

public class AuthenticatedSubject extends AbstractSubject {

	private Principal principal;
	
	public AuthenticatedSubject(Principal principal) {
		this.principal = principal;
	}

	@Override
	public Principal getPrincipal() {
		return principal;
	}

	@Override
	public void login(Authentication token) throws AuthorizationException {
	}

	@Override
	public void logout() {
		this.principal = null;
	}

}
