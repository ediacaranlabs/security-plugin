package br.com.uoutec.community.ediacaran.security.pub;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import br.com.uoutec.community.ediacaran.core.security.AuthorizationException;
import br.com.uoutec.community.ediacaran.core.security.Principal;
import br.com.uoutec.community.ediacaran.core.security.Subject;

public abstract class AbstractSubject 
	implements Subject {

	@Override
	public boolean isPermitted(String permission) {
		
		Principal principal = getPrincipal();
		
		if(principal == null) {
			return false;
		}
		
		return principal.getStringPermissions().contains(permission);
		
	}

	@Override
	public boolean[] isPermitted(String... permissions) {
		
		boolean[] result = new boolean[permissions.length];
		int i = 0;
		for(String p: permissions) {
			result[i++] = isPermitted(p);
		}
		
		return result;
	}

	@Override
	public boolean isPermittedAll(String... permissions) {
		
		for(String p: permissions) {
			if(!isPermitted(p)) {
				return false;
			}
		}
		
		return true;
	}

	@Override
	public void checkPermission(String permission) throws AuthorizationException {
		if(!isPermitted(permission)) {
			throw new AuthorizationException(permission);
		}
	}

	@Override
	public void checkPermissions(String... permissions) throws AuthorizationException {
		for(String permission: permissions) {
			if(!isPermitted(permission)) {
				throw new AuthorizationException(permission);
			}
		}
	}

	@Override
	public boolean hasRole(String roleIdentifier) {
		Principal principal = getPrincipal();
		return principal == null? false : principal.getRoles().contains(roleIdentifier);
	}

	@Override
	public boolean[] hasRoles(List<String> roleIdentifiers) {
		boolean[] result = new boolean[roleIdentifiers.size()];
		int i = 0;
		for(String p: roleIdentifiers) {
			result[i++] = hasRole(p);
		}
		
		return result;
	}

	@Override
	public void checkRole(String roleIdentifier) throws AuthorizationException {
		if(!hasRole(roleIdentifier)) {
			throw new AuthorizationException(roleIdentifier);
		}
	}

	@Override
	public void checkRoles(Collection<String> roleIdentifiers) throws AuthorizationException {
		for(String roleIdentifier: roleIdentifiers) {
			if(!hasRole(roleIdentifier)) {
				throw new AuthorizationException(roleIdentifier);
			}
		}
	}

	@Override
	public void checkRoles(String... roleIdentifiers) throws AuthorizationException {
		checkRoles(Arrays.asList(roleIdentifiers));
	}


	@Override
	public boolean isAuthenticated() {
		return getPrincipal() != null;
	}
	
}
