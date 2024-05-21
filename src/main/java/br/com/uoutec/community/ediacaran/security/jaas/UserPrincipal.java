package br.com.uoutec.community.ediacaran.security.jaas;

import java.util.Collections;
import java.util.Set;

import br.com.uoutec.community.ediacaran.security.AbstractAuthorizationInstrument;
import br.com.uoutec.community.ediacaran.security.Authorization;
import br.com.uoutec.community.ediacaran.security.Role;

public class UserPrincipal 
	extends AbstractAuthorizationInstrument 
	implements Principal {

	private static final long serialVersionUID = 3298999623798862177L;

	private final Object systemID;
	
	private final Set<String> stringRoles;
	
	private final Set<Role> roles;
	
	private final Set<String> stringPermissions;
	
	private final Set<Authorization> permissions;
	
	private final Set<java.security.Principal> principals;
	
	public UserPrincipal(Object systemID, Set<String> stringRoles,
			Set<Role> roles, Set<String> stringPermissions,  Set<Authorization> permissions, 
			Set<java.security.Principal> principals) {
		this.systemID = systemID;
		this.roles = Collections.unmodifiableSet(roles);
		this.stringPermissions = Collections.unmodifiableSet(stringPermissions);
		this.permissions = Collections.unmodifiableSet(permissions);
		this.principals = Collections.unmodifiableSet(principals);
		this.stringRoles = Collections.unmodifiableSet(stringRoles);
	}
	
	@Override
	public String getName() {
		return String.valueOf(systemID);
	}

	public Object getSystemID() {
		return systemID;
	}

	@Override
	public java.security.Principal getUserPrincipal() {
		return this;
	}

	@Override
	public Set<java.security.Principal> getPrincipals() {
		return principals;
	}

	@Override
	public Set<Role> getRoles() {
		return roles;
	}

	@Override
	public Set<String> getStringRoles() {
		return stringRoles;
	}
	
	@Override
	public Set<String> getStringPermissions() {
		return stringPermissions;
	}

	@Override
	public Set<Authorization> getPermissions() {
		return permissions;
	}

	@Override
	protected br.com.uoutec.community.ediacaran.security.Principal getPrincipal() {
		return this;
	}
	
}
