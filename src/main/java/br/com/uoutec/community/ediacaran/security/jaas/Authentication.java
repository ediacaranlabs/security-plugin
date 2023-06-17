package br.com.uoutec.community.ediacaran.security.jaas;

public interface Authentication {

    Object getPrincipal();
    
    Object getCredentials();
	
}
