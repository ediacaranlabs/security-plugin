package br.com.uoutec.community.ediacaran.security.pub;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.brandao.brutos.RequestProvider;
import org.brandao.brutos.web.WebMvcRequest;

import br.com.uoutec.community.ediacaran.ContextManager;
import br.com.uoutec.community.ediacaran.core.security.AuthenticationException;
import br.com.uoutec.community.ediacaran.core.security.AuthenticationProvider;
import br.com.uoutec.community.ediacaran.core.security.Principal;
import br.com.uoutec.community.ediacaran.core.security.SecurityConfig;
import br.com.uoutec.community.ediacaran.core.security.SecurityManager;
import br.com.uoutec.community.ediacaran.core.security.Subject;
import br.com.uoutec.community.ediacaran.plugins.PublicBean;
import br.com.uoutec.community.ediacaran.security.tomcat.ContextConfigurerListener;

@Singleton
public class SecurityManagerImp 
	implements SecurityManager{

	private AuthenticationProviderRepository authenticationProviderRepository;
	
	@Inject
	public SecurityManagerImp(AuthenticationProviderRepository authenticationProviderRepository) {
		this.authenticationProviderRepository = authenticationProviderRepository;
	}
	
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
		
		WebMvcRequest request = (WebMvcRequest)RequestProvider.getRequest();
		Object userPrincipal = request.getUserPrincipal();

		if(userPrincipal == null) {
			return authenticationProviderRepository
					.getAuthenticationProvider().createSubject();
		}
		
		if(!(userPrincipal instanceof br.com.uoutec.community.ediacaran.core.security.jaas.UserPrincipal)) {
			return null;
		}
		
		userPrincipal = 
				((br.com.uoutec.community.ediacaran.core.security.jaas.UserPrincipal)userPrincipal)
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

	@Singleton
	public static class AuthenticationProviderRepository implements PublicBean{

		private AuthenticationProvider authenticationProvider;

		public AuthenticationProvider getAuthenticationProvider() {
			return authenticationProvider;
		}

		public void setAuthenticationProvider(AuthenticationProvider authenticationProvider) {
			//TODO security
			this.authenticationProvider = authenticationProvider;
		}
		
	}
	
}
