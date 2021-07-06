package br.com.uoutec.community.ediacaran.security.pub;

public interface AuthenticationProvider {

	Authentication get(Object id);
	
	String getRealmName();
	
}
