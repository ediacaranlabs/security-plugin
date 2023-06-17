package br.com.uoutec.community.ediacaran.security;

import br.com.uoutec.community.ediacaran.plugins.PublicBean;

public interface AuthenticationProvider extends PublicBean{

	Subject createSubject();
	
}
