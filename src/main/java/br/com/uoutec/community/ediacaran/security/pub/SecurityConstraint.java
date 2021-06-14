package br.com.uoutec.community.ediacaran.security.pub;

import java.util.HashSet;
import java.util.Set;

public class SecurityConstraint {

	private String pattern;
	
	private Set<Role> roles;
	
	private Set<String> methods;
	
	private String loginConfig;
	
	private String loginPage;
	
	private String errorPage;
	
	private String transportQuarantee;
	
	public SecurityConstraint(String pattern) {
		this.pattern = pattern;
		this.roles = new HashSet<Role>();
		this.methods = new HashSet<String>();
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public Set<String> getMethods() {
		return methods;
	}

	public void setMethods(Set<String> methods) {
		this.methods = methods;
	}

	public String getLoginConfig() {
		return loginConfig;
	}

	public void setLoginConfig(String loginConfig) {
		this.loginConfig = loginConfig;
	}

	public String getLoginPage() {
		return loginPage;
	}

	public void setLoginPage(String loginPage) {
		this.loginPage = loginPage;
	}

	public String getErrorPage() {
		return errorPage;
	}

	public void setErrorPage(String errorPage) {
		this.errorPage = errorPage;
	}

	public String getTransportQuarantee() {
		return transportQuarantee;
	}

	public void setTransportQuarantee(String transportQuarantee) {
		this.transportQuarantee = transportQuarantee;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((pattern == null) ? 0 : pattern.hashCode());
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
		SecurityConstraint other = (SecurityConstraint) obj;
		if (pattern == null) {
			if (other.pattern != null)
				return false;
		} else if (!pattern.equals(other.pattern))
			return false;
		return true;
	}
	
	
}
