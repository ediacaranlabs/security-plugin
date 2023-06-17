package br.com.uoutec.community.ediacaran.security;

import java.util.Collection;
import java.util.List;

import br.com.uoutec.community.ediacaran.security.jaas.Authentication;

public interface Subject {

	Principal getPrincipal();

    boolean isPermitted(String permission);

    boolean[] isPermitted(String... permissions);

    boolean isPermittedAll(String... permissions);

    void checkPermissions(String ... permissions) throws AuthorizationException;
    
    void checkPermission(String permission) throws AuthorizationException;

    boolean hasRole(String roleIdentifier);
    
    boolean[] hasRoles(String ... roleIdentifiers);
    
    boolean[] hasRoles(List<String> roleIdentifiers);
    
    void checkRole(String roleIdentifier) throws AuthorizationException;

    void checkRoles(Collection<String> roleIdentifiers) throws AuthorizationException;

    void checkRoles(String... roleIdentifiers) throws AuthorizationException;

    void login(Authentication token) throws AuthorizationException;

    boolean isAuthenticated();

    void logout();
	
}
