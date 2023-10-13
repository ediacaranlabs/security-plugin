package br.com.uoutec.community.ediacaran.security;

import java.util.List;

import br.com.uoutec.community.ediacaran.plugins.PublicBean;

public interface LoginModuleManager extends PublicBean{

	void registerLoginModule(String name, LoginModuleProvider value);

	List<String> getLoginModules();
	
	LoginModule getLoginModule();
	
	void unregisterLoginModule(String name);
	
}
