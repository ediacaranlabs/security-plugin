package br.com.uoutec.community.ediacaran.security;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SubjectProviderImp 
	implements SubjectProvider{

	@Inject
	private LoginModuleManager loginModuleManager;
			
	@Override
	public Subject getSubject() {
		LoginModule loginModule = loginModuleManager.getLoginModule();
		return loginModule.getSubject();
	}

}
