package br.com.uoutec.community.ediacaran.security.pub;

import br.com.uoutec.community.ediacaran.ContextManager;

public interface SecurityManager {

	void applySecurityConfig(SecurityInfo value, ContextManager contextManager);
	
	void destroySecurityConfig(ContextManager contextManager);

	SecurityAccess getSecurityAccess();
	
}
