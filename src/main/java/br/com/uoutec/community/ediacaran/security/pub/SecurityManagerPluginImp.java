package br.com.uoutec.community.ediacaran.security.pub;

import javax.inject.Inject;
import javax.inject.Singleton;

import br.com.uoutec.community.ediacaran.ContextManager;
import br.com.uoutec.community.ediacaran.core.security.SecurityConfig;
import br.com.uoutec.community.ediacaran.core.security.SecurityManager;

@Singleton
public class SecurityManagerPluginImp
	extends SecurityBuilderImp
	implements SecurityManagerPlugin{

	@Inject
	public SecurityManagerPluginImp(SecurityManager securityManager, ContextManager contextManager) {
		super(new SecurityConfig());
	}

	public void applySecurityConfig(SecurityCallback callback) {
		callback.applySecurityConfig(getSecurityConfig());
	}
	
	public void destroySecurityConfig(SecurityCallback callback) {
		callback.destroySecurityConfig(getSecurityConfig());
	}
	
}
