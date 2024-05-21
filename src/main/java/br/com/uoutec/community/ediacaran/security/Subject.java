package br.com.uoutec.community.ediacaran.security;

import br.com.uoutec.community.ediacaran.security.jaas.Authentication;

public interface Subject extends AuthorizationInstrument {

	Principal getPrincipal();

    void login(Authentication token) throws AuthorizationException;

    boolean isAuthenticated();

	void logout();
    
}
