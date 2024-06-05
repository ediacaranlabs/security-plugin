package br.com.uoutec.community.ediacaran.security;

import br.com.uoutec.ediacaran.core.plugins.PublicBean;

public interface AuthenticationProvider 
	extends PublicBean {

	LoginModule getLoginModule();
	
}
