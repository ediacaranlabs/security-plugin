package br.com.uoutec.community.ediacaran.security;

@Deprecated
public interface AuthorizationManagerPlugin {

	void applySecurityConfig(SecurityCallback callback);
	
	void destroySecurityConfig(SecurityCallback callback);
	
}
