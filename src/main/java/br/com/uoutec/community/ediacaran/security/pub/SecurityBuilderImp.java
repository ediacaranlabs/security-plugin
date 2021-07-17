package br.com.uoutec.community.ediacaran.security.pub;

import br.com.uoutec.community.ediacaran.ContextManager;
import br.com.uoutec.community.ediacaran.core.security.SecurityConfig;
import br.com.uoutec.community.ediacaran.core.security.SecurityConstraint;
import br.com.uoutec.community.ediacaran.core.security.SecurityManager;

public class SecurityBuilderImp implements SecurityBuilder{

	private SecurityConfig securityConfig;
	
	private SecurityManager securityManager;
	
	private ContextManager contextManager;

	public SecurityBuilderImp(SecurityBuilderImp builder) {
		this.securityConfig = builder.securityConfig;
		this.securityManager = builder.securityManager;
		this.contextManager = builder.contextManager;
	}
	
	public SecurityBuilderImp(SecurityConfig value, SecurityManager securityManager, ContextManager contextManager) {
		this.securityConfig = value;
		this.securityManager = securityManager;
		this.contextManager = contextManager;
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
		securityManager.applySecurityConfig(securityConfig, contextManager);
	}
	
	public void digest() {
		securityConfig.setMethod(DIGEST);
		securityManager.applySecurityConfig(securityConfig, contextManager);
	}
	
	public void cert() {
		securityConfig.setMethod(CERT);
		securityManager.applySecurityConfig(securityConfig, contextManager);
	}
	
	public void form(String loginPage) {
		securityConfig.setMethod(FORM);
		securityConfig.setLoginPage(loginPage);
		securityManager.applySecurityConfig(securityConfig, contextManager);
	}
	
	public void form(String loginPage, String errorPage) {
		securityConfig.setMethod(FORM);
		securityConfig.setLoginPage(loginPage);
		securityConfig.setErrorPage(errorPage);
		securityManager.applySecurityConfig(securityConfig, contextManager);
	}
	
}
