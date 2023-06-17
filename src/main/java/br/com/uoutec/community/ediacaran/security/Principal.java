package br.com.uoutec.community.ediacaran.security;

import java.util.Set;

public interface Principal {

	Object getUserPrincipal();
	
    Set<String> getRoles();

    Set<String> getStringPermissions();
	
    Set<Authorization> getPermissions();
    
}
