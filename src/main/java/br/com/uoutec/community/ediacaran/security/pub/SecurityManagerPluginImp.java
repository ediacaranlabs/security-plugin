package br.com.uoutec.community.ediacaran.security.pub;

import javax.inject.Inject;
import javax.inject.Singleton;

import br.com.uoutec.community.ediacaran.ContextManager;

@Singleton
public class SecurityManagerPluginImp
	extends SecurityBuilderImp
	implements SecurityManagerPlugin{

	@Inject
	public SecurityManagerPluginImp(SecurityManager securityManager, ContextManager contextManager) {
		super(new SecurityInfo(), securityManager, contextManager);
	}

}
