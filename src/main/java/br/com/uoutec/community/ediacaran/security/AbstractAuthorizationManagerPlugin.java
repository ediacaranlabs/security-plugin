package br.com.uoutec.community.ediacaran.security;

public abstract class AbstractAuthorizationManagerPlugin
	implements AuthorizationManagerPlugin{

	public void applySecurityConfig(SecurityCallback callback) {
		callback.applySecurityConfig(getSecurityConfig());
	}
	
	public void destroySecurityConfig(SecurityCallback callback) {
		callback.destroySecurityConfig(getSecurityConfig());
	}

	protected abstract Object getSecurityConfig();
	
}
