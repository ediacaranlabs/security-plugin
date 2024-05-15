package br.com.uoutec.community.ediacaran.security.jaas;

import br.com.uoutec.community.ediacaran.security.AuthenticatedSubject;
import br.com.uoutec.community.ediacaran.security.Subject;

public class UserPrincipal implements java.security.Principal {

	private br.com.uoutec.community.ediacaran.security.Principal userPrincipal;

	private AuthenticatedSubject authenticatedSubject;
	
	public UserPrincipal(br.com.uoutec.community.ediacaran.security.Principal userPrincipal) {
		this.userPrincipal = userPrincipal;
		this.authenticatedSubject = new AuthenticatedSubject(userPrincipal);
	}

	@Override
	public String getName() {
		return String.valueOf(userPrincipal.getUserPrincipal());
	}

	public br.com.uoutec.community.ediacaran.security.Principal getUserPrincipal(){
		return userPrincipal;
	}

	public Subject getSubject(){
		return authenticatedSubject;
	}
	
}
