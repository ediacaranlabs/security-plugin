package br.com.uoutec.community.ediacaran.security;

import java.util.List;
import java.util.Set;

import br.com.uoutec.ediacaran.core.plugins.PublicBean;
import br.com.uoutec.entity.registry.Registry;

public interface SecurityRegistry extends Registry, PublicBean{

	public static final String PERMISSION_PREFIX = "app.security.";
	
	String registerRole(Role role) throws SecurityRegistryException;

	boolean unregisterRole(String id);

	String registerAuthorization(Authorization authorization, String ... groups) throws SecurityRegistryException;
	
	boolean unregisterAuthorization(String ... id);

	Authorization getAuthorization(String ... id);
	
	Set<Authorization> toAuthorization(Set<String> value);
	
	Role getRole(String id);
	
	List<Role> getAllRoles();
	
	List<AuthorizationEntry> getAllAuthorizations();
	
}
