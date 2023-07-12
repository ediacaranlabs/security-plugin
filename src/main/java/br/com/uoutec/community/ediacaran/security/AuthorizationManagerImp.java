package br.com.uoutec.community.ediacaran.security;

import javax.inject.Inject;
import javax.inject.Singleton;

import br.com.uoutec.community.ediacaran.SecurityProvider;
import br.com.uoutec.community.ediacaran.plugins.EntityContextPlugin;
import br.com.uoutec.community.ediacaran.plugins.PublicBean;
import br.com.uoutec.community.ediacaran.plugins.SecurityUtil;

@Singleton
public class AuthorizationManagerImp 
	implements AuthorizationManager{

	private AuthenticationProviderRepository authenticationProviderRepository;
	
	@Inject
	public AuthorizationManagerImp(AuthenticationProviderRepository authenticationProviderRepository) {
		this.authenticationProviderRepository = authenticationProviderRepository;
	}
	
	
	@Override
	public Subject getSubject() {

		SecurityUtil.checkPermission(GET_SUBJECT);
		
		SecurityProvider securityProvider = 
				EntityContextPlugin.getEntity(SecurityProvider.class);
		
		Object userPrincipal = securityProvider.getUserPrincipal();
		
		if(userPrincipal == null) {
			AuthenticationProvider ap = authenticationProviderRepository.getAuthenticationProvider();
			return ap.createSubject();
		}
		
		if(!(userPrincipal instanceof br.com.uoutec.community.ediacaran.security.jaas.UserPrincipal)) {
			return null;
		}
		
		userPrincipal = 
				((br.com.uoutec.community.ediacaran.security.jaas.UserPrincipal)userPrincipal)
				.getUserPrincipal();
		
		if(userPrincipal instanceof Principal) {
			return new AuthenticatedSubject((Principal)userPrincipal);
		}
		
		throw new AuthenticationException();
	}

	@Override
	public void registerAuthenticationProvider(AuthenticationProvider value) {
		this.authenticationProviderRepository.setAuthenticationProvider(value);
	}

	@Override
	public void unregisterAuthenticationProvider() {
	}
	
	@Singleton
	public static class AuthenticationProviderRepository implements PublicBean{

		private AuthenticationProvider authenticationProvider;

		public AuthenticationProvider getAuthenticationProvider() {
			return authenticationProvider;
		}

		public void setAuthenticationProvider(AuthenticationProvider authenticationProvider) {

			SecurityUtil.checkPermission(REGISTER_AUTHENTICATION_PROVIDER);
			
			this.authenticationProvider = authenticationProvider;
		}
		
	}
	
}
