package br.com.uoutec.community.ediacaran.security;

import javax.inject.Singleton;

import br.com.uoutec.ediacaran.core.UserPrincipalProvider;
import br.com.uoutec.ediacaran.core.plugins.EntityContextPlugin;

@Singleton
public class SubjectProviderImp 
	implements SubjectProvider{
			
	@Override
	public Subject getSubject() {
		UserPrincipalProvider userPrincipalProvider = getUserPrincipalProvider();
		return new AuthenticatedSubject((Principal)userPrincipalProvider.getUserPrincipal());
	}
	
	private volatile UserPrincipalProvider userPrincipalProvider;
	
	private UserPrincipalProvider getUserPrincipalProvider() {
		
		if(userPrincipalProvider == null) {
			synchronized (this) {
				userPrincipalProvider = EntityContextPlugin.getEntity(UserPrincipalProvider.class);
			}
		}
		
		return userPrincipalProvider;
	}
}
