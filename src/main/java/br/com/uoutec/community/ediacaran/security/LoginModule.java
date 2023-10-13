package br.com.uoutec.community.ediacaran.security;

import java.util.Map;

import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.LoginException;

public interface LoginModule {

	Subject getSubject();
	
	void initialize(javax.security.auth.Subject subject, CallbackHandler callbackHandler, Map<String, ?> sharedState, Map<String, ?> options);
	
    boolean login() throws LoginException;
	
    boolean commit() throws LoginException;

    boolean abort() throws LoginException;

    boolean logout() throws LoginException;
    
}
