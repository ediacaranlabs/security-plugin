package br.com.uoutec.community.ediacaran.security;

public interface SecurityCallback {

	void applySecurityConfig(Object value);
	
	void destroySecurityConfig(Object value);
	
}
