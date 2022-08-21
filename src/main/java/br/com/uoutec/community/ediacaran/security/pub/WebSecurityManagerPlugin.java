package br.com.uoutec.community.ediacaran.security.pub;

import javax.inject.Inject;
import javax.inject.Singleton;

import br.com.uoutec.community.ediacaran.core.security.AbstractAuthorizationManagerPlugin;
import br.com.uoutec.community.ediacaran.core.security.SecurityRegistry;

@Singleton
public class WebSecurityManagerPlugin 
	extends AbstractAuthorizationManagerPlugin
	implements SecurityBuilder{

	@Inject
	private SecurityRegistry securityRegistry;
	
	private SecurityConfig securityConfig;

	private SecurityBuilder builder;
	
	public WebSecurityManagerPlugin(){
		this.securityConfig = new SecurityConfig();
		this.builder = new SecurityBuilderImp(securityConfig, securityRegistry);
	}
	
	@Override
	protected Object getSecurityConfig() {
		return securityConfig;
	}

	@Override
	public SecurityConstraintBuilder addConstraint(String value) {
		return builder.addConstraint(value);
	}

	@Override
	public void basic() {
		builder.basic();
	}

	@Override
	public void digest() {
		builder.digest();
	}

	@Override
	public void cert() {
		builder.cert();
	}

	@Override
	public void form(String loginPage) {
		builder.form(loginPage);
	}

	@Override
	public void form(String loginPage, String errorPage) {
		builder.form(loginPage, errorPage);
	}
	
}
