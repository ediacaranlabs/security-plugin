package br.com.uoutec.community.ediacaran.security.pub;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.inject.Singleton;

import br.com.uoutec.community.ediacaran.ContextManager;
import br.com.uoutec.community.ediacaran.security.pub.Role;
import br.com.uoutec.community.ediacaran.security.pub.SecurityAccess;
import br.com.uoutec.community.ediacaran.security.pub.SecurityConfig;
import br.com.uoutec.community.ediacaran.security.pub.SecurityConstraint;
import br.com.uoutec.community.ediacaran.security.pub.SecurityManager;

@Singleton
public class SecurityManagerImp 
	implements SecurityManager{

	private AuthenticationProvider authenticationProvider;
	
	@Override
	public void registerAuthenticationProvider(AuthenticationProvider value) {
		//TODO: security
		this.authenticationProvider = value;
	}

	@Override
	public AuthenticationProvider getCurrentAuthenticationProvider() {
		return authenticationProvider;
	}
	
	@SuppressWarnings("serial")
	@Override
	public void applySecurityConfig(SecurityConfig value, ContextManager contextManager) {
		
		addSecurityConstraint(value.getConstraints(), contextManager);
		
		addRoles(value, contextManager);
		
		contextManager.addFilter(
				"ShiroFilter", 
				"org.apache.shiro.web.servlet.ShiroFilter", 
				Arrays.asList("/*"), 
				Arrays.asList("REQUEST", "FORWARD", "INCLUDE", "ERROR"),
				new HashMap<String,String>(){{
					put("configPath", "classpath:shiro.ini");
				}});
		
	}

	@Override
	public void destroySecurityConfig(ContextManager contextManager) {
	}

	@Override
	public SecurityAccess getSecurityAccess() {
		return null;
	}

	private void addRoles(SecurityConfig value, ContextManager contextManager) {
		
		Set<Role> allRoles = getAllRoles(value);
		for(Role r: allRoles) {
			contextManager.addRole(r.getName(), r.getDescription());
		}
		
	}
	
	private void addSecurityConstraint(Set<SecurityConstraint> value, ContextManager contextManager) {
		
		Map<SecurityConstraintGroup,Map<SecurityCollectionGroup,List<String>>> securityConstraintGroups = 
				createSecurityConstraintGroup(value);
		
		int scgID   = 0;
		int scolgID = 0;
		
		for(Entry<SecurityConstraintGroup,Map<SecurityCollectionGroup,List<String>>> sgcKey: securityConstraintGroups.entrySet()) {
			
			Map<SecurityCollectionGroup,List<String>> scolg = sgcKey.getValue();
			
			List<String> securityCollections = new ArrayList<String>();
			
			for(Entry<SecurityCollectionGroup,List<String>> scolgKey: scolg.entrySet()) {
				
				String scID = scgID + "-" + scolgID;
				
				contextManager.addSecurityCollection(
						scID, 
						null, 
						new ArrayList<String>(scolgKey.getValue()), 
						scolgKey.getKey().methods == null? null : new ArrayList<String>(scolgKey.getKey().methods)
				);
				
				securityCollections.add(scID);
				
				scolgID++;
			}
			
			contextManager.addSecurityConstraint(
					String.valueOf(scgID), 
					sgcKey.getKey().userConstraint, 
					new ArrayList<String>(sgcKey.getKey().roles), 
					securityCollections
			);
			
			scgID++;
		}
		
	}

	private Map<SecurityConstraintGroup,Map<SecurityCollectionGroup,List<String>>> createSecurityConstraintGroup(
			Set<SecurityConstraint> value) {
		
		Map<SecurityConstraintGroup,Map<SecurityCollectionGroup,List<String>>> securityConstraintGroups = 
				new HashMap<SecurityConstraintGroup,Map<SecurityCollectionGroup,List<String>>>();
		
		for(SecurityConstraint sc: value) {
			SecurityConstraintGroup scgKey = new SecurityConstraintGroup(null, sc.getUserConstraint(), sc.getRoles());
			
			Map<SecurityCollectionGroup,List<String>> scg = securityConstraintGroups.get(scgKey);
			
			if(scg == null) {
				scg = new HashMap<SecurityCollectionGroup,List<String>>();
				securityConstraintGroups.put(scgKey, scg);
			}
		
			SecurityCollectionGroup scolgKey = new SecurityCollectionGroup(sc.getMethods());
			
			List<String> uris = scg.get(scolgKey);
			
			if(uris == null) {
				uris = new ArrayList<String>();
				scg.put(scolgKey, uris);
			}
			
			uris.add(sc.getPattern());

		}
		
		return securityConstraintGroups;
		
	}
	
	private Set<Role> getAllRoles(SecurityConfig value){
		
		Set<Role> r = new HashSet<Role>();
		Set<SecurityConstraint> constraints = value.getConstraints();
		
		if(constraints != null) {
			
			for(SecurityConstraint sc: constraints) {
				
				Set<Role> roles = sc.getRoles();
				
				if(roles != null) {
					
					for(Role role: roles) {
						r.add(role);
					}
					
				}
				
			}
			
		}
		
		return r;
	}
	
	private static class SecurityCollectionGroup{
		
		public Set<String> methods;
		
		public SecurityCollectionGroup(Set<String> methods) {
			this.methods = methods;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((methods == null) ? 0 : methods.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			SecurityCollectionGroup other = (SecurityCollectionGroup) obj;
			if (methods == null) {
				if (other.methods != null)
					return false;
			} else if (!methods.equals(other.methods))
				return false;
			return true;
		}
		
	}
	
	private static class SecurityConstraintGroup{
		
		public String userConstraint;
		
		public Set<String> roles;
		
		public SecurityConstraintGroup(String name, String userConstraint, Set<Role> roles) {
			this.userConstraint = userConstraint;
			this.roles = null;
			
			if(roles != null) {
				this.roles = new HashSet<String>();
				for(Role r: roles) {
					this.roles.add(r.getName());
				}
			}
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((roles == null) ? 0 : roles.hashCode());
			result = prime * result + ((userConstraint == null) ? 0 : userConstraint.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			SecurityConstraintGroup other = (SecurityConstraintGroup) obj;
			if (roles == null) {
				if (other.roles != null)
					return false;
			} else if (!roles.equals(other.roles))
				return false;
			if (userConstraint == null) {
				if (other.userConstraint != null)
					return false;
			} else if (!userConstraint.equals(other.userConstraint))
				return false;
			return true;
		}

		
	}

}
