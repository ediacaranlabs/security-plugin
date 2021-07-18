package br.com.uoutec.community.ediacaran.security.pub;

import javax.inject.Singleton;

import br.com.uoutec.community.ediacaran.ContextManager;
import br.com.uoutec.community.ediacaran.core.security.SecurityConfig;
import br.com.uoutec.community.ediacaran.core.security.SecurityManager;
import br.com.uoutec.community.ediacaran.core.security.jaas.Subject;
import br.com.uoutec.community.ediacaran.security.tomcat.ContextConfigurerListener;

@Singleton
public class SecurityManagerImp 
	implements SecurityManager{

	@Override
	public void applySecurityConfig(SecurityConfig value, ContextManager contextManager) {
		ContextConfigurerListener ctxc = new ContextConfigurerListener(value);
		contextManager.registerListener(ctxc);
	}

	@Override
	public void destroySecurityConfig(ContextManager contextManager) {
		contextManager.unregisterListeners();
	}

	@Override
	public Subject getSubject() {
		return null;
	}

}
