package br.com.uoutec.community.ediacaran.front.security.file;

import java.util.HashSet;
import java.util.Set;

import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;

import org.brandao.brutos.RequestProvider;

import br.com.uoutec.community.ediacaran.security.AbstractSubject;
import br.com.uoutec.community.ediacaran.security.AuthenticationProvider;
import br.com.uoutec.community.ediacaran.security.Authorization;
import br.com.uoutec.community.ediacaran.security.AuthorizationException;
import br.com.uoutec.community.ediacaran.security.Principal;
import br.com.uoutec.community.ediacaran.security.Subject;
import br.com.uoutec.community.ediacaran.security.jaas.Authentication;
import br.com.uoutec.community.ediacaran.security.jaas.UsernamePasswordAuthentication;

@Singleton
public class AuthenticationProviderImp 
	implements AuthenticationProvider {

	@Override
	public Subject createSubject() {
		HttpServletRequest request;
		if((request = (HttpServletRequest)RequestProvider.getRequest()) != null && 
		   request.getUserPrincipal() != null) {
			return new TestSubject("teste", "teste", true);
		}
		return new TestSubject("teste", "teste", false);
	}
	
	private static final class TestSubject extends AbstractSubject {

		private String user;
		
		private String pass;
		
		private boolean authenticated;
		
		public TestSubject(String user, String pass, boolean authenticated) {
			this.user = user;
			this.pass = pass;
			this.authenticated = authenticated;
		}
		
		@Override
		public Principal getPrincipal() {
			return new Principal() {

				@Override
				@SuppressWarnings("serial")
				public Set<String> getRoles() {
					return new HashSet<String>() {{
						add("manager");
						add("user");
					}};
				}

				@Override
				@SuppressWarnings("serial")
				public Set<String> getStringPermissions() {
					return new HashSet<String>() {{
						add("*");
					}};
				}

				@Override
				public Object getUserPrincipal() {
					return TestSubject.this.user;
				}

				@Override
				@SuppressWarnings("serial")
				public Set<Authorization> getPermissions() {
					return new HashSet<Authorization>() {{
						add(new Authorization("*", null, null));
					}};
				}
				
			};
		}

		/*
		@Override
		public boolean isPermitted(String permission) {
			return true;
		}

		@Override
		public boolean[] isPermitted(String... permissions) {
			boolean[] r = new boolean[permissions.length];
			for(int i=0;i<r.length;i++) {
				r[i] = true;
			}
			return r;
		}

		@Override
		public boolean isPermittedAll(String... permissions) {
			return true;
		}

		@Override
		public void checkPermission(String permission) throws AuthorizationException {
		}

		@Override
		public void checkPermissions(String... permissions) throws AuthorizationException {
		}

		@Override
		public boolean hasRole(String roleIdentifier) {
			return true;
		}

		@Override
		public boolean[] hasRoles(List<String> roleIdentifiers) {
			boolean[] r = new boolean[roleIdentifiers.size()];
			
			for(int i=0;i<r.length;i++) {
				r[i] = true;
			}
			return r;
		}

		@Override
		public boolean[] hasRoles(String... roleIdentifiers) {
			boolean[] r = new boolean[roleIdentifiers.length];
			
			for(int i=0;i<r.length;i++) {
				r[i] = true;
			}
			return r;
		}
		
		@Override
		public void checkRole(String roleIdentifier) throws AuthorizationException {
		}

		@Override
		public void checkRoles(Collection<String> roleIdentifiers) throws AuthorizationException {
		}

		@Override
		public void checkRoles(String... roleIdentifiers) throws AuthorizationException {
		}
		 */
		@Override
		public void login(Authentication token) throws AuthorizationException {
			if(token instanceof UsernamePasswordAuthentication) {
				UsernamePasswordAuthentication t = (UsernamePasswordAuthentication)token;
				if(user.equals(t.getUsername()) && pass.equals(new String(t.getPassword()))) {
					this.authenticated = true;
					return;
				}
			}

			throw new AuthorizationException();
		}

		@Override
		public boolean isAuthenticated() {
			return authenticated;
		}

		@Override
		public void logout() {
			this.authenticated = false;
		}
		
	}

}
