package br.com.uoutec.community.ediacaran.security;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class AuthorizationParser {

	public Set<Authorization> toAuthorization(String ... value) {
		return toAuthorization(Arrays.stream(value).collect(Collectors.toSet()));
	}
	
	public Set<Authorization> toAuthorization(Set<String> value){
		
		AuthorizationImp root = new AuthorizationImp("*", "*", "*");
		
		if(value != null) {
			for(String str: value) {
				String[] vals = str.split("\\:+");
				String[] groups;
				AuthorizationImp authorization;
				
				if(vals.length > 1) {
					groups = Arrays.copyOf(vals, vals.length - 1);
					authorization = new AuthorizationImp(vals[vals.length - 1], vals[vals.length - 1], vals[vals.length - 1]);
				}
				else {
					groups = new String[0];
					authorization = new AuthorizationImp(vals[0], vals[0], vals[0]);
				}
				
				root.put(authorization, true, groups);
				
			}
		}
		
		return root.getChilds();	
	}
	
}
