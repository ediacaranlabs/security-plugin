package br.com.uoutec.community.ediacaran.security;

public interface AuthorizationManager {

	public static final RuntimePermission GET_SUBJECT = new RuntimePermission("app.security.subject.access");
	
	public static final RuntimePermission REGISTER_AUTHENTICATION_PROVIDER = new RuntimePermission("app.security.provider.register");
	
	void registerAuthenticationProvider(AuthenticationProvider value);

	void unregisterAuthenticationProvider();
	
	Subject getSubject();
	
}
