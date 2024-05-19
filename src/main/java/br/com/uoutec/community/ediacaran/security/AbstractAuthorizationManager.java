package br.com.uoutec.community.ediacaran.security;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

import br.com.uoutec.application.security.ContextSystemSecurityCheck;
import br.com.uoutec.application.security.RuntimeSecurityPermission;
import br.com.uoutec.ediacaran.core.UserPrincipalProvider;
import br.com.uoutec.ediacaran.core.plugins.EntityContextPlugin;

public abstract class AbstractAuthorizationManager 
	implements AuthorizationManager {

	public static final String PERMISSION_PREFIX = "app.security.";
	
	private static final String idRegex = "[A-Z0-9]+(\\_[A-Z0-9]+)*";
	
	private final AuthorizationEntity authorization;
	
	private final ConcurrentMap<String, Role> roles;

	private final ConcurrentMap<String, AuthorizationEntry> authorizationMap;

	public AbstractAuthorizationManager() {
		authorization = new AuthorizationEntity("*", "all", "All");
		this.roles = new ConcurrentHashMap<String, Role>();
		this.authorizationMap = new ConcurrentHashMap<String, AuthorizationEntry>();
	}
	
	public Subject getSubject() {
		UserPrincipalProvider securityProvider = 
				EntityContextPlugin.getEntity(UserPrincipalProvider.class);
		
		Principal userPrincipal = (Principal)securityProvider.getUserPrincipal();
		
		if(userPrincipal instanceof Principal) {
			return new AuthenticatedSubject(userPrincipal);
		}
		
		return null;
	}
	
	public String registerRole(Role role) throws SecurityRegistryException{
		
		ContextSystemSecurityCheck.checkPermission(new RuntimeSecurityPermission(PERMISSION_PREFIX + "role.register"));
		
		if(roles.putIfAbsent(role.getId(), role) != null) {
			throw new SecurityRegistryException("has been added: " + role.getId());
		}
		
		return role.getId();
	}

	public boolean unregisterRole(String id) {
		
		ContextSystemSecurityCheck.checkPermission(new RuntimeSecurityPermission(PERMISSION_PREFIX + "role.unregister"));
		
		return roles.remove(id) != null;
	}
	
	public String registerAuthorization(String id, String name, String description, 
			String resourceBundle) throws SecurityRegistryException {
		
		ContextSystemSecurityCheck.checkPermission(new RuntimeSecurityPermission(PERMISSION_PREFIX + "authorization.register"));
		
		AuthorizationEntryRequest aer = toAuthorization(id, name, description, resourceBundle);
		AuthorizationEntity value = aer.authorization;
		String[] groups = aer.groups;
		
		if(value == null) {
			throw new NullPointerException();
		}
		
		if(!value.getId().matches(idRegex)) {
			throw new SecurityRegistryException("invalid id: " + value.getId());
		}
		
		for(String g: groups) {
			
			if(!g.matches(idRegex)) {
				throw new SecurityRegistryException("invalid group id: " + value.getId());
			}
			
		}
		
		authorization.put(value, false, groups);
		
		authorizationMap.put(id, new AuthorizationEntry(id, value));
		
		return id;	
	}
	
	public boolean unregisterAuthorization(String value) {
		
		ContextSystemSecurityCheck.checkPermission(new RuntimeSecurityPermission(PERMISSION_PREFIX + "authorization.unregister"));
		
		authorizationMap.remove(value);
		String[] groups = toGroups(value);
		
		return authorization.remove(groups);
	}
	
	public Authorization getAuthorization(String value) {
		String[] groups = toGroups(value);
		return groups.length == 0? authorization : authorization.get(groups);
	}

	public Set<Authorization> toAuthorization(Set<String> value){
		
		AuthorizationEntity root = new AuthorizationEntity("*", "*", "*");
		
		if(value != null) {
			for(String str: value) {
				String[] vals = str.split("\\:+");
				String[] groups;
				AuthorizationEntity authorization;
				
				if(vals.length > 1) {
					groups = Arrays.copyOf(vals, vals.length - 1);
					authorization = new AuthorizationEntity(vals[vals.length - 1], vals[vals.length - 1], vals[vals.length - 1]);
				}
				else {
					groups = new String[0];
					authorization = new AuthorizationEntity(vals[0], vals[0], vals[0]);
				}
				
				root.put(authorization, true, groups);
				
			}
		}
		
		return root.getChilds();	
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
	
	private static class AuthorizationEntryRequest {
		
		public AuthorizationEntity authorization;
		
		public String[] groups;

		public AuthorizationEntryRequest(AuthorizationEntity authorization, String[] groups) {
			super();
			this.authorization = authorization;
			this.groups = groups;
		}
		
	}
	
	private String[] toGroups(String id) {
		return id.split("\\:");
	}
			
	private AuthorizationEntryRequest toAuthorization(String id, String name, String description, 
			String resourceBundle) {
		String[] strs = id.split("\\:");
		String au = strs[strs.length - 1];
		String[] groups = Arrays.copyOf(strs, strs.length - 1) ;
		
		return 
			new AuthorizationEntryRequest(
					new AuthorizationEntity(
							au, 
							name, 
							description, 
							resourceBundle, 
							name, 
							description, 
							new HashSet<AuthorizationEntity>()
					), 
					groups
			);
	}
}
