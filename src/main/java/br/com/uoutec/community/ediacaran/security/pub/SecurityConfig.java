package br.com.uoutec.community.ediacaran.security.pub;

import javax.inject.Singleton;

import br.com.uoutec.community.ediacaran.plugins.PublicBean;

@Singleton
public class SecurityConfig implements PublicBean{

	private SecurityAccess securityAccess;
	
	public void setConfigureAccess(SecurityAccess value) {
		//TODO: security
		this.securityAccess = value;
	}
	
	public SecurityAccess getSecurityAccess() {
		return securityAccess;
	}
	
}
