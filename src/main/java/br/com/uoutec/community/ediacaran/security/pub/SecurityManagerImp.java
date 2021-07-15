package br.com.uoutec.community.ediacaran.security.pub;

import javax.inject.Singleton;

import br.com.uoutec.community.ediacaran.ContextManager;

@Singleton
public class SecurityManagerImp 
	implements SecurityManager{

	private AuthenticationProvider authenticationProvider;
	
	@Override
	public void registerAuthenticationProvider(AuthenticationProvider value) {
		//TODO: security
		this.authenticationProvider = value;
	}

	@Override
	public AuthenticationProvider getCurrentAuthenticationProvider() {
		return authenticationProvider;
	}
	
	@Override
	public void applySecurityConfig(SecurityConfig value, ContextManager contextManager) {
		SecurityContextConfigurerListener ctxc = new SecurityContextConfigurerListener(value);
		contextManager.registerListener(ctxc);
	}

	@Override
	public void destroySecurityConfig(ContextManager contextManager) {
		contextManager.unregisterListeners();
	}

	@Override
	public SecurityAccess getSecurityAccess() {
		return null;
	}

}
