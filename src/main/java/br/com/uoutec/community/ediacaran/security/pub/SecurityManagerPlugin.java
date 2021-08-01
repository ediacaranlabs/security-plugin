package br.com.uoutec.community.ediacaran.security.pub;

public interface SecurityManagerPlugin 
	extends SecurityBuilder{

	void applySecurityConfig(SecurityCallback callback);
	
	void destroySecurityConfig(SecurityCallback callback);
	
}
