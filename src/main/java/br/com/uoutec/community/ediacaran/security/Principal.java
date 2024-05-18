package br.com.uoutec.community.ediacaran.security;

import java.util.Set;

public interface Principal {

	java.security.Principal getUserPrincipal();
	
	Set<java.security.Principal> getPrincipals();
	
    Set<String> getRoles();

    Set<String> getStringPermissions();
	
    Set<Authorization> getPermissions();
    
}
