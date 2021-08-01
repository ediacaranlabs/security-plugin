package br.com.uoutec.community.ediacaran.security.pub;

import br.com.uoutec.community.ediacaran.core.security.SecurityConfig;

public abstract class AbstractSecurityCallback 
	implements SecurityCallback{

	@Override
	public void applySecurityConfig(SecurityConfig value) {
	}

	@Override
	public void destroySecurityConfig(SecurityConfig value) {
	}

}
