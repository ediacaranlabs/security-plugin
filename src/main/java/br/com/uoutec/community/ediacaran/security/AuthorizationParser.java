package br.com.uoutec.community.ediacaran.security;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class AuthorizationParser {

	@Inject
	private AuthorizationManager authorizationManager;
	
	public Set<RoleEntity> toRoleEntity(String ... value) {
		return toRoleEntity(Arrays.stream(value).collect(Collectors.toSet()));
	}
	
	public Set<RoleEntity> toRoleEntity(Set<String> value){
		return authorizationManager.getRoles(value).stream().collect(Collectors.toSet());
	}

	public Set<AuthorizationEntity> toAutorizationEntity(String ... value) {
		return toAutorizationEntity(Arrays.stream(value).collect(Collectors.toSet()));
	}
	
	public Set<AuthorizationEntity> toAutorizationEntity(Set<String> value){
		return authorizationManager.getAuthorizations(value).stream().collect(Collectors.toSet());
	}
	
	public Set<Authorization> toAuthorization(String ... value) {
		return toAuthorization(Arrays.stream(value).collect(Collectors.toSet()));
	}

	public Set<Authorization> toAuthorization(List<String> value){
		return toAuthorization(value.stream().collect(Collectors.toSet()));
	}
	
	public Set<Authorization> toAuthorization(Set<String> value){
		
		AuthorizationImp root = new AuthorizationImp("*");
		if(value != null) {
			List<String> list = value.stream().collect(Collectors.toList());
			Collections.sort(list,(a1,b2)->a1.compareTo(b2));
			for(String e: list) {
				AuthorizationImp authorization = new AuthorizationImp(e);
				root.put(authorization, true, authorization.getPath());
			}
		}
		
		return root.getChilds();	
	}
	
}
