package br.com.uoutec.community.ediacaran.security;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

import javax.inject.Singleton;

import br.com.uoutec.application.security.ContextSystemSecurityCheck;
import br.com.uoutec.application.security.RuntimeSecurityPermission;

@Singleton
public class AuthorizationManagerImp 
	implements AuthorizationManager {

	public static final String PERMISSION_PREFIX = "app.security.";
	
	public static final String AUTHORIZATION_PROPERTY = 
			"authorization_provider";
	
	private final ConcurrentMap<String, RoleEntity> roles;
	
	private final ConcurrentMap<String, AuthorizationEntity> authorizationMap;
	
	public AuthorizationManagerImp() {
		this.roles = new ConcurrentHashMap<String, RoleEntity>();
		this.authorizationMap = new ConcurrentHashMap<String, AuthorizationEntity>();
	}
	
	public String registerRole(String id, String name, String description, 
			String resourceBundle) throws SecurityRegistryException{
		
		ContextSystemSecurityCheck.checkPermission(
				new RuntimeSecurityPermission(PERMISSION_PREFIX + "role.register"));
		
		RoleEntity role = new RoleEntity(id, name, description, name, description, resourceBundle);
		
		if(roles.putIfAbsent(role.getId(), role) != null) {
			throw new SecurityRegistryException("has been added: " + role.getId());
		}
		
		return role.getId();
	}
	
	public boolean unregisterRole(String id) {
		
		ContextSystemSecurityCheck.checkPermission(
				new RuntimeSecurityPermission(PERMISSION_PREFIX + "role.unregister"));
		
		return roles.remove(id) != null;
	}
	
	public String registerAuthorization(String id, String name, String description, 
			String resourceBundle) throws SecurityRegistryException {
		
		ContextSystemSecurityCheck.checkPermission(
				new RuntimeSecurityPermission(PERMISSION_PREFIX + "authorization.register"));
		
		AuthorizationEntity ae = 
				new AuthorizationEntity(id, name, description, resourceBundle);
		
		authorizationMap.put(id, ae);
		
		return id;	
	}
	
	public boolean unregisterAuthorization(String value) {
		
		ContextSystemSecurityCheck.checkPermission(
				new RuntimeSecurityPermission(PERMISSION_PREFIX + "authorization.unregister"));
		
		return authorizationMap.remove(value) != null;
	}
	
	public RoleEntity getRole(String id) {
		return roles.get(id);
	}
	
	public List<RoleEntity> getAllRoles(){
		List<RoleEntity> list = roles.values().stream().collect(Collectors.toList());
		Collections.sort(list, (o1, o2) -> o1.getId().compareTo(o2.getId()));
		return Collections.unmodifiableList(list);
	}
	
	public List<AuthorizationEntity> getAllAuthorizations(){
		List<AuthorizationEntity> list = 
				authorizationMap.values().stream()
				.collect(Collectors.toList());
		
		Collections.sort(list, (o1, o2) -> o1.getId().compareTo(o2.getId()));
		return Collections.unmodifiableList(list);
	}
	
}
