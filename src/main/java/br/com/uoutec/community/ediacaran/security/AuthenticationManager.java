package br.com.uoutec.community.ediacaran.security;

import java.util.List;

public interface AuthenticationManager {

	void registerAuthenticationProvider(String name, AuthenticationProvider value);

	void unregisterAuthenticationProvider(String name);
	
	List<String> getAuthenticationProviders();
	
	LoginModule getLoginModule();
	
	Subject getSubject();
	
	
}
