package br.com.uoutec.community.ediacaran.security;

import java.util.List;
import java.util.Set;

import br.com.uoutec.ediacaran.core.plugins.PublicBean;

public interface AuthorizationManager extends PublicBean {

	String registerRole(String id, String name, String description, 
			String resourceBundle) throws SecurityRegistryException;

	boolean unregisterRole(String id);
	
	String registerAuthorization(String id, String name, String description, 
			String resourceBundle) throws SecurityRegistryException;
	
	boolean unregisterAuthorization(String value);
	
	RoleEntity getRole(String id);

	List<RoleEntity> getRoles(String ... values);

	List<RoleEntity> getRoles(Set<String> values);
	
	List<RoleEntity> getAllRoles();

	List<AuthorizationEntity> getAuthorizations(String ... values);

	List<AuthorizationEntity> getAuthorizations(Set<String> values);
	
	List<AuthorizationEntity> getAllAuthorizations();
	
}
