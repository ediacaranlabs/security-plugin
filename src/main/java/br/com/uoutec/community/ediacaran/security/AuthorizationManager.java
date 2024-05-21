package br.com.uoutec.community.ediacaran.security;

import java.util.List;
import java.util.Set;

import br.com.uoutec.ediacaran.core.plugins.PublicBean;

public interface AuthorizationManager extends PublicBean {

	void registerAuthorizationProvider(String name, AuthorizationProvider value);

	List<String> getAuthorizationProviders();

	void unregisterAuthorizationProvider(String name);
	
	String registerRole(Role role) throws SecurityRegistryException;

	boolean unregisterRole(String id);
	
	String registerAuthorization(String id, String name, String description, 
			String resourceBundle) throws SecurityRegistryException;
	
	boolean unregisterAuthorization(String value);
	
	Role getRole(String id);
	
	Set<Authorization> toAuthorization(Set<String> value);
	
	Set<Authorization> toAuthorization(String ... value);
	
	List<Role> getAllRoles();
	
	List<AuthorizationEntry> getAllAuthorizations();
	
}
