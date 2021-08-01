package br.com.uoutec.community.ediacaran.security.pub;

import br.com.uoutec.community.ediacaran.core.security.AbstractSecurityManagerPlugin;

public class WebSecurityManagerPlugin 
	extends AbstractSecurityManagerPlugin
	implements SecurityBuilder{

	private SecurityConfig securityConfig;

	private SecurityBuilder builder;
	
	public WebSecurityManagerPlugin(){
		this.securityConfig = new SecurityConfig();
		this.builder = new SecurityBuilderImp(securityConfig);
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
