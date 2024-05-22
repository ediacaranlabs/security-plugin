package br.com.uoutec.community.ediacaran.security;

import java.io.Serializable;
import java.util.Set;

public interface Principal 
	extends AuthorizationInstrument, 
	Serializable {

	java.security.Principal getUserPrincipal();
	
	Set<java.security.Principal> getPrincipals();
	
    Set<String> getStringRoles();
    
	Set<RoleEntity> getRoles();

    Set<String> getStringPermissions();
	
    Set<Authorization> getPermissions();
    
}
