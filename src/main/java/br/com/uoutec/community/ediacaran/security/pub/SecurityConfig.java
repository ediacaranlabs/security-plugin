package br.com.uoutec.community.ediacaran.security.pub;

import java.util.HashSet;
import java.util.Set;

import br.com.uoutec.community.ediacaran.core.security.SecurityConstraint;

public class SecurityConfig {
	
	private Set<SecurityConstraint> constraints;
	
	private String method;
	
	private String realmName;

	private String loginPage;
	
	private String errorPage;
	
	public SecurityConfig() {
		this.constraints = new HashSet<SecurityConstraint>();
	}
	
	public Set<SecurityConstraint> getConstraints() {
		return constraints;
	}

	public void setConstraints(Set<SecurityConstraint> constraints) {
		this.constraints = constraints;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getRealmName() {
		return realmName;
	}

	public void setRealmName(String realmName) {
		this.realmName = realmName;
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

}
