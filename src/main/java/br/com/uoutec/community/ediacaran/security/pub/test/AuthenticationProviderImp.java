package br.com.uoutec.community.ediacaran.security.pub.test;

import javax.inject.Singleton;

import br.com.uoutec.community.ediacaran.security.pub.Authentication;
import br.com.uoutec.community.ediacaran.security.pub.AuthenticationProvider;

@Singleton
public class AuthenticationProviderImp 
	implements AuthenticationProvider {

	@Override
	public Authentication get(Object id){
		return new AuthenticationImp("teste", "teste", null, null, null, true);
	}

	@Override
	public String getRealmName() {
		return "Ediacaran";
	}
	
}
