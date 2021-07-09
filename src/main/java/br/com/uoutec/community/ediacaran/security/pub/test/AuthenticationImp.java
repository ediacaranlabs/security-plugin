package br.com.uoutec.community.ediacaran.security.pub.test;

import java.util.Set;

import br.com.uoutec.community.ediacaran.security.pub.Authentication;

public class AuthenticationImp 
	implements Authentication{

	private String username;
	
	private String password;
	
	private byte[] credentialsSalt;
	
	private Set<String> roles;

	private Set<String> stringPermissions;

	private boolean locked;
	
	public AuthenticationImp(String username, String password, byte[] credentialsSalt, Set<String> roles,
			Set<String> stringPermissions, boolean locked) {
		this.username = username;
		this.password = password;
		this.credentialsSalt = credentialsSalt;
		this.roles = roles;
		this.stringPermissions = stringPermissions;
		this.locked = locked;
	}

	@Override
	public Object getUsername() {
		return username;
	}

	@Override
	public Object getPassword() {
		return password;
	}

	@Override
	public byte[] getCredentialsSalt() {
		return credentialsSalt;
	}

	@Override
	public Set<String> getRoles() {
		return roles;
	}

	@Override
	public Set<String> getStringPermissions() {
		return stringPermissions;
	}

	@Override
	public boolean isLocked() {
		return locked;
	}

}
