package br.com.uoutec.community.ediacaran.security.pub;

import br.com.uoutec.community.ediacaran.core.security.SecurityManager;
import br.com.uoutec.community.ediacaran.plugins.PublicBean;

public interface SecurityManagerRegister extends PublicBean{

	void registrySecurityManager(SecurityManager value);
	
	void unregistrySecurityManager();
	
	SecurityManager getSecurityManager();
	
}
