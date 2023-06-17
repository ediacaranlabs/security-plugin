package br.com.uoutec.community.ediacaran.security;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

import javax.inject.Singleton;

@Singleton
public class SecurityRegistryImp implements SecurityRegistry {

	private static final String idRegex = "[A-Z0-9]+(\\-[A-Z0-9]+)*";
	
	private final Authorization authorization;
	
	private final ConcurrentMap<String, Role> roles;
	
	public SecurityRegistryImp() {
		authorization = new Authorization("*", "all", "All");
		this.roles = new ConcurrentHashMap<String, Role>();
	}
	
	@Override
	public void flush() {
	}

	@Override
	public String registerRole(Role role) throws SecurityRegistryException {
		
		SecurityManager sm = System.getSecurityManager();
		
		if(sm != null) {
			sm.checkPermission(new RuntimePermission(PERMISSION_PREFIX + ".role.register"));
		}
		
		if(roles.putIfAbsent(role.getId(), role) != null) {
			throw new SecurityRegistryException("has been added: " + role.getId());
		}
		
		return role.getId();
	}

	@Override
	public boolean unregisterRole(String id) {
		
		SecurityManager sm = System.getSecurityManager();
		
		if(sm != null) {
			sm.checkPermission(new RuntimePermission(PERMISSION_PREFIX + ".role.unregister"));
		}
		
		return roles.remove(id) != null;
	}

	@Override
	public String registerAuthorization(Authorization value, String... groups) throws SecurityRegistryException {
		
		SecurityManager sm = System.getSecurityManager();
		
		if(sm != null) {
			sm.checkPermission(new RuntimePermission(PERMISSION_PREFIX + ".authorization.register"));
		}
		
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
		
		return groups.length == 0?
				value.getId() :
				Arrays.stream(groups).collect(Collectors.joining(":")).concat(":").concat(value.getId());
	}

	@Override
	public boolean unregisterAuthorization(String ... id) {
		
		SecurityManager sm = System.getSecurityManager();
		
		if(sm != null) {
			sm.checkPermission(new RuntimePermission(PERMISSION_PREFIX + ".authorization.unregister"));
		}
		
		return authorization.remove(id);
	}

	@Override
	public Authorization getAuthorization(String... id) {
		return id.length == 0? authorization : authorization.get(id);
	}

	@Override
	public Authorization toAuthorization(Set<String> value) {
		
		Authorization root = new Authorization("*", "*", "*");
		
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
		
		return root;
	}

	@Override
	public Role getRole(String id) {
		return roles.get(id);
	}

	@Override
	public Set<Role> getAll() {
		return new HashSet<Role>(roles.values());
	}

}
