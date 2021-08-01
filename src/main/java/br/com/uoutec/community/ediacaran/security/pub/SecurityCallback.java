package br.com.uoutec.community.ediacaran.security.pub;

import br.com.uoutec.community.ediacaran.core.security.SecurityConfig;

public interface SecurityCallback {

	void applySecurityConfig(SecurityConfig value);
	
	void destroySecurityConfig(SecurityConfig value);
	
}
