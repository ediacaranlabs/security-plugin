package br.com.uoutec.community.ediacaran.security;

import javax.inject.Singleton;

import br.com.uoutec.ediacaran.core.SecurityProvider;
import br.com.uoutec.ediacaran.core.plugins.EntityContextPlugin;

@Singleton
public class SubjectProviderImp 
	implements SubjectProvider{

	@Override
	public Subject getSubject() {
		
		SecurityProvider securityProvider = 
				EntityContextPlugin.getEntity(SecurityProvider.class);
		
		Object userPrincipal = securityProvider.getUserPrincipal();
		
		if(!(userPrincipal instanceof br.com.uoutec.community.ediacaran.security.jaas.UserPrincipal)) {
			return null;
		}
		
		userPrincipal = 
				((br.com.uoutec.community.ediacaran.security.jaas.UserPrincipal)userPrincipal)
				.getUserPrincipal();
		
		if(userPrincipal instanceof Principal) {
			return new AuthenticatedSubject((Principal)userPrincipal);
		}
		
		return null;
	}

}
