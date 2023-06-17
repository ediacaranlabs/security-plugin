package br.com.uoutec.community.ediacaran.security;

import java.util.HashSet;
import java.util.Set;

public class SecurityConstraint {

	private String pattern;
	
	private Set<String> roles;
	
	private Set<String> methods;
	
	private String userConstraint;
	
	public SecurityConstraint(String pattern) {
		this.pattern = pattern;
		this.roles = new HashSet<String>();
		this.methods = new HashSet<String>();
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public Set<String> getRoles() {
		return roles;
	}

	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}

	public Set<String> getMethods() {
		return methods;
	}

	public void setMethods(Set<String> methods) {
		this.methods = methods;
	}
	
	public String getUserConstraint() {
		return userConstraint;
	}

	public void setUserConstraint(String userConstraint) {
		this.userConstraint = userConstraint;
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
