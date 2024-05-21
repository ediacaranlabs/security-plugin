package br.com.uoutec.community.ediacaran.security;

import java.util.Set;

public interface AuthorizationProvider {

	Set<Authorization> toAuthorization(String ... value);
	
	Set<Authorization> toAuthorization(Set<String> value);
	
}
