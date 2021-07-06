package br.com.uoutec.community.ediacaran.security.pub;

import java.util.Set;

public interface Authentication {

    Object getUsername();
    
    Object getPassword();
    
    byte[] getCredentialsSalt();
	
    Set<String> getRoles();

    Set<String> getStringPermissions();
    
    boolean isLocked();
    
}
