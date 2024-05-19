package br.com.uoutec.community.ediacaran.security.jaas;

import java.util.Collections;
import java.util.Set;

import br.com.uoutec.community.ediacaran.security.Authorization;

public class UserPrincipalEntity 
	implements java.security.Principal, Principal {

	private final Object systemID;
	
	private final Set<String> roles;
	
	private final Set<String> stringPermissions;
	
	private final Set<Authorization> permissions;
	
	private final Set<java.security.Principal> principals;
	
	public UserPrincipalEntity(Object systemID, Set<String> roles, 
			Set<String> stringPermissions,  Set<Authorization> permissions, 
			Set<java.security.Principal> principals) {
		this.systemID = systemID;
		this.roles = Collections.unmodifiableSet(roles);
		this.stringPermissions = Collections.unmodifiableSet(stringPermissions);
		this.permissions = Collections.unmodifiableSet(permissions);
		this.principals = Collections.unmodifiableSet(principals);
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
	public Set<String> getRoles() {
		return roles;
	}

	@Override
	public Set<String> getStringPermissions() {
		return stringPermissions;
	}

	@Override
	public Set<Authorization> getPermissions() {
		return permissions;
	}
	
}
