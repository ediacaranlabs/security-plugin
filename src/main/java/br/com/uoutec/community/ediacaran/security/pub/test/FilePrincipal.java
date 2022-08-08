package br.com.uoutec.community.ediacaran.security.pub.test;

import java.util.Set;

import br.com.uoutec.community.ediacaran.core.security.Authorization;
import br.com.uoutec.community.ediacaran.core.security.Principal;

public class FilePrincipal implements Principal{

	private final FileUser user;
	
	public FilePrincipal(FileUser user) {
		super();
		this.user = user;
	}

	@Override
	public Object getUserPrincipal() {
		return user.getName();
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
		return null;
	}

}
