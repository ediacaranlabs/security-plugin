package br.com.uoutec.community.ediacaran.security.pub;

import br.com.uoutec.community.ediacaran.plugins.PublicBean;

public interface AuthenticationProvider extends PublicBean{

	Authentication get(Object id);
	
	String getRealmName();
	
}
