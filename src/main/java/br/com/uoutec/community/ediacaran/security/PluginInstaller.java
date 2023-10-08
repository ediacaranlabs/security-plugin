package br.com.uoutec.community.ediacaran.security;

import br.com.uoutec.community.ediacaran.AbstractPlugin;
import br.com.uoutec.community.ediacaran.plugins.EntityContextPlugin;
import br.com.uoutec.community.ediacaran.plugins.PluginType;
import br.com.uoutec.community.ediacaran.security.file.FileAuthenticationProvider;

public class PluginInstaller extends AbstractPlugin{
	
	public static final String USE_DEFAULT_AUTHENTICATION_PROVIDER_PROPERTY = "default_authentication";

	public void install() throws Throwable{
		applySecurityConfiguration();
	}

	private void applySecurityConfiguration() throws SecurityRegistryException {
		
		SecurityRegistry securityRegistry = EntityContextPlugin.getEntity(SecurityRegistry.class);
		
		securityRegistry.registerRole(new Role("manager","Manager","Application Manger",null,null,null));
		securityRegistry.registerRole(new Role("user","User","Authenticated user",null,null,null));
		
		PluginType pluginType = EntityContextPlugin.getEntity(PluginType.class);
		
		boolean userDefaultAuthenticationProvider = 
				pluginType.getConfiguration().getBoolean(USE_DEFAULT_AUTHENTICATION_PROVIDER_PROPERTY);
		
		if(userDefaultAuthenticationProvider) {
			FileAuthenticationProvider fup = EntityContextPlugin.getEntity(FileAuthenticationProvider.class);
			AuthorizationManager sm = EntityContextPlugin.getEntity(AuthorizationManager.class);
			sm.registerAuthenticationProvider(fup);
		}
		
	}

	@Override
	public void uninstall() throws Throwable {
		
		AuthorizationManager sm = EntityContextPlugin.getEntity(AuthorizationManager.class);
		sm.unregisterAuthenticationProvider();
		
	}
	
	
}
