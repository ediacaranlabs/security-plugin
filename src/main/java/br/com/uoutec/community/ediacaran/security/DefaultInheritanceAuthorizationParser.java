package br.com.uoutec.community.ediacaran.security;

public class DefaultInheritanceAuthorizationParser 
	implements InheritanceAuthorizationParser {

	@Override
	public String[] toInheritance(String value) {
		return value.split("\\:");
	}

}
