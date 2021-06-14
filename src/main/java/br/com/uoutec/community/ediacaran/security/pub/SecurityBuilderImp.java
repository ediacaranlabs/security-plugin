package br.com.uoutec.community.ediacaran.security.pub;

import br.com.uoutec.community.ediacaran.ContextManager;

public class SecurityBuilderImp implements SecurityBuilder{

	private SecurityInfo securityInfo;
	
	private SecurityManager securityManager;
	
	private ContextManager contextManager;
	
	public SecurityBuilderImp(SecurityInfo value, SecurityManager securityManager, ContextManager contextManager) {
		this.securityInfo = value;
		this.securityManager = securityManager;
		this.contextManager = contextManager;
	}
	
	public SecurityConstraintBuilder addConstraint(String value) {
		SecurityConstraint sc = new SecurityConstraint(value);
		securityInfo.getConstraints().add(sc);
		return new SecurityConstraintBuilderImp(sc, securityInfo);
	}
	
	public SecurityBuilder setRealmName(String value) {
		securityInfo.setRealmName(value);
		return this;
	}
	
	public void basic() {
		securityInfo.setMethod(BASIC);
		securityManager.applySecurityConfig(securityInfo, contextManager);
	}
	
	public void digest() {
		securityInfo.setMethod(DIGEST);
		securityManager.applySecurityConfig(securityInfo, contextManager);
	}
	
	public void cert() {
		securityInfo.setMethod(CERT);
		securityManager.applySecurityConfig(securityInfo, contextManager);
	}
	
	public void form(String loginPage) {
		securityInfo.setMethod(FORM);
		securityInfo.setLoginPage(loginPage);
		securityManager.applySecurityConfig(securityInfo, contextManager);
	}
	
	public void form(String loginPage, String errorPage) {
		securityInfo.setMethod(FORM);
		securityInfo.setLoginPage(loginPage);
		securityInfo.setErrorPage(errorPage);
		securityManager.applySecurityConfig(securityInfo, contextManager);
	}
	
}
