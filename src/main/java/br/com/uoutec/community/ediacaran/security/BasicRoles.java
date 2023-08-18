package br.com.uoutec.community.ediacaran.security;

public enum BasicRoles {

	MANAGER("manager"),
	
	USER("user");
	
	private String name;
	
	BasicRoles(String name) {
		this.name = name;
	}
	
	public String toString() {
		return name;
	}
	
}
