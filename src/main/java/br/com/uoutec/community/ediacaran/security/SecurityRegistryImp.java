package br.com.uoutec.community.ediacaran.security;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

import javax.inject.Singleton;

import br.com.uoutec.application.security.ContextSystemSecurityCheck;
import br.com.uoutec.application.security.RuntimeSecurityPermission;

@Singleton
public class SecurityRegistryImp implements SecurityRegistry {

	private static final String idRegex = "[A-Z0-9]+(\\-[A-Z0-9]+)*";
	
	private final Authorization authorization;
	
	private final ConcurrentMap<String, Role> roles;

	private final ConcurrentMap<String, AuthorizationEntry> authorizationMap;
	
	public SecurityRegistryImp() {
		authorization = new Authorization("*", "all", "All");
		this.roles = new ConcurrentHashMap<String, Role>();
		this.authorizationMap = new ConcurrentHashMap<String, AuthorizationEntry>();
	}
	
	@Override
	public void flush() {
	}

	@Override
	public String registerRole(Role role) throws SecurityRegistryException {
		
		ContextSystemSecurityCheck.checkPermission(new RuntimeSecurityPermission(PERMISSION_PREFIX + "role.register"));
		
		if(roles.putIfAbsent(role.getId(), role) != null) {
			throw new SecurityRegistryException("has been added: " + role.getId());
		}
		
		return role.getId();
	}

	@Override
	public boolean unregisterRole(String id) {
		
		ContextSystemSecurityCheck.checkPermission(new RuntimeSecurityPermission(PERMISSION_PREFIX + "role.unregister"));
		
		return roles.remove(id) != null;
	}

	@Override
	public String registerAuthorization(Authorization value, String... groups) throws SecurityRegistryException {
		
		ContextSystemSecurityCheck.checkPermission(new RuntimeSecurityPermission(PERMISSION_PREFIX + "authorization.register"));
		
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
		
		String authorizationID = groups.length == 0?
				value.getId() :
				Arrays.stream(groups).collect(Collectors.joining(":")).concat(":").concat(value.getId());
		
		authorizationMap.put(authorizationID, new AuthorizationEntry(authorizationID, value));
		
		return authorizationID;
	}

	@Override
	public boolean unregisterAuthorization(String ... groups) {
		
		ContextSystemSecurityCheck.checkPermission(new RuntimeSecurityPermission(PERMISSION_PREFIX + "authorization.unregister"));

		String authorizationID = 
				Arrays.stream(groups).collect(Collectors.joining(":"));
		
		authorizationMap.remove(authorizationID);
		
		return authorization.remove(groups);
	}

	@Override
	public Authorization getAuthorization(String... id) {
		return id.length == 0? authorization : authorization.get(id);
	}

	@Override
	public Set<Authorization> toAuthorization(Set<String> value) {
		
		Authorization root = new Authorization("*", "*", "*");
		
		if(value != null) {
			for(String str: value) {
				String[] vals = str.split("\\:+");
				String[] groups;
				Authorization authorization;
				
				if(vals.length > 1) {
					groups = Arrays.copyOf(vals, vals.length - 1);
					authorization = new Authorization(vals[vals.length - 1], vals[vals.length - 1], vals[vals.length - 1]);
				}
				else {
					groups = new String[0];
					authorization = new Authorization(vals[0], vals[0], vals[0]);
				}
				root.put(authorization, true, groups);
			}
		}
		
		return root.getChilds();
	}

	@Override
	public Role getRole(String id) {
		return roles.get(id);
	}

	@Override
	public List<Role> getAllRoles() {
		List<Role> list = roles.values().stream().collect(Collectors.toList());
		Collections.sort(list, (o1, o2) -> o1.getId().compareTo(o2.getId()));
		return Collections.unmodifiableList(list);
	}

	@Override
	public List<AuthorizationEntry> getAllAuthorizations() {
		List<AuthorizationEntry> list = authorizationMap.values().stream().collect(Collectors.toList());
		Collections.sort(list, (o1, o2) -> o1.getId().compareTo(o2.getId()));
		return Collections.unmodifiableList(list);
	}
	
}
