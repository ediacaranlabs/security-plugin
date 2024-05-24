package br.com.uoutec.community.ediacaran.security;

import java.util.Set;

public interface Authorization {

	String getId();
	
	String[] getPath();
	
	String getNodeID();

	Set<Authorization> getChilds();
	
	boolean isPermitted(Object value);
	
}
