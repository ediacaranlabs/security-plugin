package br.com.uoutec.community.ediacaran.security;

import java.util.List;

import br.com.uoutec.ediacaran.core.plugins.PublicBean;

public interface AuthenticationManager extends PublicBean{

	void registerAuthenticationProvider(String name, AuthenticationProvider value);

	void unregisterAuthenticationProvider(String name);
	
	List<String> getAuthenticationProviders();
	
	LoginModule getLoginModule();
	
	Subject getSubject();
	
}
