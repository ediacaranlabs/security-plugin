package br.com.uoutec.community.ediacaran.security.jaas;

import java.util.Map;

import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.LoginException;

import br.com.uoutec.community.ediacaran.plugins.EntityContextPlugin;
import br.com.uoutec.community.ediacaran.security.LoginModuleManager;

public class LoginModule implements javax.security.auth.spi.LoginModule {

	private br.com.uoutec.community.ediacaran.security.LoginModule delegateLoginModule;
	
	@Override
	public void initialize(Subject subject, 
			CallbackHandler callbackHandler, Map<String, ?> sharedState, Map<String, ?> options) {
	    
	    LoginModuleManager lmm = EntityContextPlugin.getEntity(LoginModuleManager.class);
	    this.delegateLoginModule = lmm.getLoginModule();
	    this.delegateLoginModule.initialize(subject, callbackHandler, sharedState, options);
	}

	@Override
	public boolean login() throws LoginException {
        return delegateLoginModule.login();
	}
	
	@Override
	public boolean commit() throws LoginException {
        return delegateLoginModule.commit();
    }

	@Override
	public boolean abort() throws LoginException {
		return delegateLoginModule.abort();	
	}

	@Override
	public boolean logout() throws LoginException {
		return delegateLoginModule.logout();
	}
	
}
