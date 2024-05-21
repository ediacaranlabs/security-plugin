package br.com.uoutec.community.ediacaran.security;

public abstract class AbstractSubject
	extends AbstractAuthorizationInstrument
	implements Subject {

	private static final long serialVersionUID = 2662648972841058599L;

	@Override
	public boolean isAuthenticated() {
		return getPrincipal() != null;
	}
	
}
