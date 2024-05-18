package br.com.uoutec.community.ediacaran.security.file;

import java.util.HashSet;
import java.util.Set;

import br.com.uoutec.community.ediacaran.security.Authorization;
import br.com.uoutec.community.ediacaran.security.Principal;
import br.com.uoutec.community.ediacaran.security.jaas.RolePrincipal;
import br.com.uoutec.community.ediacaran.security.jaas.UserPrincipal;

public class FilePrincipal implements Principal{

	private final FileUser user;
	
	private UserPrincipal userPrincipal;
	
	private Set<java.security.Principal> principals;
	
	public FilePrincipal(FileUser user) {
		this.user = user;
		this.principals = new HashSet<>();
		this.userPrincipal = new UserPrincipal(user.getName(), 
				user.getRoles(), user.getStringPermissions(), user.getPermissions(), principals);
		loadPrinpals();
	}

	private void loadPrinpals(){
		
		principals.add(this.userPrincipal);
		
	    if(userPrincipal.getRoles() != null && !userPrincipal.getRoles().isEmpty()) {
	    	for(String roleName: userPrincipal.getRoles()) {
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
	public Set<String> getRoles() {
		return user.getRoles();
	}

	@Override
	public Set<String> getStringPermissions() {
		return user.getStringPermissions();
	}

	@Override
	public Set<Authorization> getPermissions() {
		return user.getPermissions();
	}

}
