package br.com.uoutec.community.ediacaran.security;

import br.com.uoutec.community.ediacaran.security.jaas.Authentication;

public class AuthenticatedSubject extends AbstractSubject {

	private static final long serialVersionUID = -2227940044664499382L;
	
	private Principal principal;
	
	public AuthenticatedSubject() {
	}
	
	public AuthenticatedSubject(Principal principal) {
		this.principal = principal;
	}

	@Override
	public Principal getPrincipal() {
		return principal;
	}

	@Override
	public void login(Authentication token) throws AuthorizationException {
		throw new SecurityException();
	}

	@Override
	public void logout() {
		throw new SecurityException();
	}

}
