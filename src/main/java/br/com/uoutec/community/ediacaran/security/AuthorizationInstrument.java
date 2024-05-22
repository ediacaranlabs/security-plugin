package br.com.uoutec.community.ediacaran.security;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

public interface AuthorizationInstrument extends Serializable {

    boolean isGranted(String permission);
    
    boolean isPermitted(String permission);

    boolean[] isPermitted(String... permissions);

    boolean isPermittedAll(String... permissions);

    boolean isPermittedAll(List<String> permissions);
    
    void checkPermissions(String ... permissions) throws AuthorizationException;
    
    void checkPermission(String permission) throws AuthorizationException;

    boolean hasRole(String roleIdentifier);
    
    boolean[] hasRoles(String ... roleIdentifiers);
    
    boolean[] hasRoles(List<String> roleIdentifiers);
    
    void checkRole(String roleIdentifier) throws AuthorizationException;

    void checkRoles(Collection<String> roleIdentifiers) throws AuthorizationException;

    void checkRoles(String... roleIdentifiers) throws AuthorizationException;
    
}
