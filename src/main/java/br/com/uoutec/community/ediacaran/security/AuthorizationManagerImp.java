package br.com.uoutec.community.ediacaran.security;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

import javax.inject.Singleton;

import br.com.uoutec.application.security.ContextSystemSecurityCheck;
import br.com.uoutec.application.security.RuntimeSecurityPermission;
import br.com.uoutec.ediacaran.core.plugins.EntityContextPlugin;
import br.com.uoutec.ediacaran.core.plugins.PluginType;

@Singleton
public class AuthorizationManagerImp 
	implements AuthorizationManager {

	public static final String PERMISSION_PREFIX = "app.security.";
	
	public static final String AUTHORIZATION_PROPERTY = 
			"authorization_provider";
	
	private final ConcurrentMap<String, Role> roles;
	
	private final ConcurrentMap<String, AuthorizationEntry> authorizationMap;
	
	private ConcurrentMap<String, AuthorizationProvider> providers;
	
	private PluginType pluginType;
	
	public AuthorizationManagerImp() {
		this.pluginType = EntityContextPlugin.getEntity(PluginType.class);
		this.roles = new ConcurrentHashMap<String, Role>();
		this.authorizationMap = new ConcurrentHashMap<String, AuthorizationEntry>();
		this.providers = new ConcurrentHashMap<>();
	}
	
	@Override
	public void registerAuthorizationProvider(String name, AuthorizationProvider value) {
		
		ContextSystemSecurityCheck.checkPermission(
				new RuntimeSecurityPermission(PERMISSION_PREFIX + "authorization.provider.register"));
		
		if(providers.putIfAbsent(name, value) != null) {
			throw new IllegalStateException("LoginModuleProvider");
		}
		
	}
	
	@Override
	public List<String> getAuthorizationProviders() {
		return providers.keySet().stream().collect(Collectors.toList());
	}
	
	@Override
	public void unregisterAuthorizationProvider(String name) {
	
		ContextSystemSecurityCheck.checkPermission(
				new RuntimeSecurityPermission(PERMISSION_PREFIX + "authorization.provider.unregister"));
		
		providers.remove(name);
	}
	
	public String registerRole(Role role) throws SecurityRegistryException{
		
		ContextSystemSecurityCheck.checkPermission(
				new RuntimeSecurityPermission(PERMISSION_PREFIX + "role.register"));
		
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
		
		AuthorizationEntry ae = 
				new AuthorizationEntry(id, name, description, resourceBundle);
		
		authorizationMap.put(id, ae);
		
		return id;	
	}
	
	public boolean unregisterAuthorization(String value) {
		
		ContextSystemSecurityCheck.checkPermission(
				new RuntimeSecurityPermission(PERMISSION_PREFIX + "authorization.unregister"));
		
		return authorizationMap.remove(value) != null;
	}
	
	@Override
	public Set<Authorization> toAuthorization(String... value) {
		return getAuthorizationProvider().toAuthorization(value);
	}
	
	public Set<Authorization> toAuthorization(Set<String> value){
		return getAuthorizationProvider().toAuthorization(value);
	}
	
	public Role getRole(String id) {
		return roles.get(id);
	}
	
	public List<Role> getAllRoles(){
		List<Role> list = roles.values().stream().collect(Collectors.toList());
		Collections.sort(list, (o1, o2) -> o1.getId().compareTo(o2.getId()));
		return Collections.unmodifiableList(list);
	}
	
	public List<AuthorizationEntry> getAllAuthorizations(){
		List<AuthorizationEntry> list = 
				authorizationMap.values().stream()
				.collect(Collectors.toList());
		
		Collections.sort(list, (o1, o2) -> o1.getId().compareTo(o2.getId()));
		return Collections.unmodifiableList(list);
	}
	
	/* private */
	
	private AuthorizationProvider getAuthorizationProvider() {
		
		String value = pluginType.getConfiguration().getString(AUTHORIZATION_PROPERTY);
		
		AuthorizationProvider authorizationProvider = providers.get(value);
		
		if(authorizationProvider == null) {
			throw new NullPointerException("provider");
		}
		
		return authorizationProvider;
	}
	
	
}
