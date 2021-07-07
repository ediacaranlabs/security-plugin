package br.com.uoutec.community.ediacaran.security.pub;

import br.com.uoutec.community.ediacaran.ContextManager;
import br.com.uoutec.community.ediacaran.plugins.PublicBean;

public interface SecurityManager extends PublicBean{

	void applySecurityConfig(SecurityConfig value, ContextManager contextManager);
	
	void destroySecurityConfig(ContextManager contextManager);

	void registerAuthenticationProvider(AuthenticationProvider value);
	
	AuthenticationProvider getCurrentAuthenticationProvider();
	
	SecurityAccess getSecurityAccess();
	
}
