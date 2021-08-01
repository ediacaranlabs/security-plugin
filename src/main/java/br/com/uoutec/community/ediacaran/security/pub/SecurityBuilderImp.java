package br.com.uoutec.community.ediacaran.security.pub;

import br.com.uoutec.community.ediacaran.core.security.SecurityConfig;
import br.com.uoutec.community.ediacaran.core.security.SecurityConstraint;

public class SecurityBuilderImp implements SecurityBuilder{

	private SecurityConfig securityConfig;
	
	public SecurityBuilderImp(SecurityBuilderImp builder) {
		this.securityConfig = builder.securityConfig;
	}
	
	public SecurityBuilderImp(SecurityConfig value) {
		this.securityConfig = value;
	}
	
	protected SecurityConfig getSecurityConfig() {
		return securityConfig;
	}
	
	public SecurityConstraintBuilder addConstraint(String value) {
		SecurityConstraint sc = new SecurityConstraint(value);
		securityConfig.getConstraints().add(sc);
		return new SecurityConstraintBuilderImp(sc, this);
	}
	
	public SecurityBuilder setRealmName(String value) {
		securityConfig.setRealmName(value);
		return this;
	}
	
	public void basic() {
		securityConfig.setMethod(BASIC);
	}
	
	public void digest() {
		securityConfig.setMethod(DIGEST);
	}
	
	public void cert() {
		securityConfig.setMethod(CERT);
	}
	
	public void form(String loginPage) {
		securityConfig.setMethod(FORM);
		securityConfig.setLoginPage(loginPage);
	}
	
	public void form(String loginPage, String errorPage) {
		securityConfig.setMethod(FORM);
		securityConfig.setLoginPage(loginPage);
		securityConfig.setErrorPage(errorPage);
	}
	
}
