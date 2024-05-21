package br.com.uoutec.community.ediacaran.security;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public abstract class AbstractAuthorizationInstrument 
	implements AuthorizationInstrument {

	private static final long serialVersionUID = 2663338046126294960L;

	protected abstract Principal getPrincipal();
	
	@Override
	public boolean isPermitted(String permission) {
		
		Principal principal = getPrincipal();
		
		if(principal == null) {
			return false;
		}
		
		if(principal.getPermissions() != null) {
			for( Authorization a: principal.getPermissions()) {
				if(a.isPermitted(permission)) {
					return true;
				}
			}
		}
		
		return false;
		
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
    public boolean isPermittedAll(List<String> permissions) {
		for(String p: permissions) {
			if(!isPermitted(p)) {
				return false;
			}
		}
		
		return true;
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
		return principal == null? false : principal.getStringRoles().contains(roleIdentifier);
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
	public boolean[] hasRoles(String... roleIdentifiers) {
		
		boolean[] result = new boolean[roleIdentifiers.length];
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

}
