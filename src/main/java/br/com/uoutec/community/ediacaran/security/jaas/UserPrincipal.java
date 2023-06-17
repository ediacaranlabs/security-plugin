package br.com.uoutec.community.ediacaran.security.jaas;

public class UserPrincipal implements java.security.Principal {

	private br.com.uoutec.community.ediacaran.security.Principal userPrincipal;

	public UserPrincipal(br.com.uoutec.community.ediacaran.security.Principal userPrincipal) {
		this.userPrincipal = userPrincipal;
	}

	@Override
	public String getName() {
		return String.valueOf(userPrincipal.getUserPrincipal());
	}

	public br.com.uoutec.community.ediacaran.security.Principal getUserPrincipal(){
		return userPrincipal;
	}
	
}
