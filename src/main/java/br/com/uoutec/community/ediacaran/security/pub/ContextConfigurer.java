package br.com.uoutec.community.ediacaran.security.pub;

import java.util.EventListener;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;

public interface ContextConfigurer {
	
	void addApplicationListener(String className);

	void addApplicationListener(EventListener listener);
	
	void addFilter(String name, String filterClass, List<String> urls, List<String> dispatches, 
			Map<String,String> params);

	void addFilter(String name, Filter filter, List<String> urls, List<String> dispatches, 
			Map<String,String> params);
	
	void addSecurityCollection(String name, String description, List<String> patterns, List<String> methods);
	
	void addSecurityConstraint(String name, String userConstraint, List<String> rules, 
			List<String> securityCollections);
	
	void addRole(String value, String description);
	
	void setLoginConfig(String authMethod, String realmName, String loginPage, String errorPage);
	
}
