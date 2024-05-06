package br.com.uoutec.community.ediacaran.security;

public class AuthorizationEntry {

	private final String id;
	
	private final Authorization authorization;

	public AuthorizationEntry(String id, Authorization authorization) {
		this.id = id;
		this.authorization = authorization;
	}

	public String getId() {
		return id;
	}

	public Authorization getAuthorization() {
		return authorization;
	}

}
