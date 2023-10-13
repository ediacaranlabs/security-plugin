package br.com.uoutec.community.ediacaran.security;

import br.com.uoutec.community.ediacaran.plugins.PublicBean;

public interface LoginModuleProvider extends PublicBean{

	LoginModule getLoginModule();
	
}
