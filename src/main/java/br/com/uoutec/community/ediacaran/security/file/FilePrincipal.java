package br.com.uoutec.community.ediacaran.security.file;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import br.com.uoutec.community.ediacaran.security.AbstractAuthorizationInstrument;
import br.com.uoutec.community.ediacaran.security.Authorization;
import br.com.uoutec.community.ediacaran.security.Principal;
import br.com.uoutec.community.ediacaran.security.RoleEntity;
import br.com.uoutec.community.ediacaran.security.jaas.RolePrincipal;
import br.com.uoutec.community.ediacaran.security.jaas.UserPrincipal;

public class FilePrincipal 
	extends AbstractAuthorizationInstrument implements Principal{

	private static final long serialVersionUID = 9059423309825551689L;

	private final FileUser user;
	
	private UserPrincipal userPrincipal;
	
	private Set<java.security.Principal> principals;
	
	@SuppressWarnings("unchecked")
	public FilePrincipal(FileUser user) {
		this.user = user;
		this.principals = new HashSet<>();
		this.userPrincipal = new UserPrincipal(user.getName(), 
				user.getRoles(),
				user.getRoles().stream().map((e)->new RoleEntity(e, null, null)).collect(Collectors.toSet()), 
				user.getStringPermissions() == null? Collections.EMPTY_SET : user.getStringPermissions(), 
				user.getPermissions() == null? Collections.EMPTY_SET : user.getPermissions(), 
				principals
		);
		loadPrinpals();
	}

	private void loadPrinpals(){
		
		principals.add(this.userPrincipal);
		
	    if(userPrincipal.getRoles() != null && !userPrincipal.getRoles().isEmpty()) {
	    	for(String roleName: userPrincipal.getStringRoles()) {
		        RolePrincipal role = new RolePrincipal(roleName);
		        principals.add(role);
	    	}
	    }
		
	}
	
	@Override
	public java.security.Principal getUserPrincipal() {
		return userPrincipal;
	}

	@Override
	public Set<java.security.Principal> getPrincipals() {
		return principals;
	}
	
	@Override
	public Set<String> getStringRoles() {
		return userPrincipal.getStringRoles();
	}

	@Override
	public Set<RoleEntity> getRoles() {
		return userPrincipal.getRoles();
	}
	
	@Override
	public Set<String> getStringPermissions() {
		return user.getStringPermissions();
	}

	@Override
	public Set<Authorization> getPermissions() {
		return user.getPermissions();
	}

	@Override
	protected Principal getPrincipal() {
		return this;
	}

}
