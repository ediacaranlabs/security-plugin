package br.com.uoutec.community.ediacaran.security;

import org.brandao.brutos.RequestProvider;
import org.brandao.brutos.web.WebMvcRequest;

import br.com.uoutec.ediacaran.core.UserPrincipalProvider;

public class WebUserPrincipalProvider 
	implements UserPrincipalProvider {

	public java.security.Principal getUserPrincipal() {
		WebMvcRequest request = (WebMvcRequest)RequestProvider.getRequest();
		return request == null? null : request.getUserPrincipal();
	}
}
