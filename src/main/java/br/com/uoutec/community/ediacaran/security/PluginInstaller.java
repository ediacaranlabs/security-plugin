package br.com.uoutec.community.ediacaran.security;

import br.com.uoutec.community.ediacaran.security.file.FileLoginModuleProvider;
import br.com.uoutec.ediacaran.core.AbstractPlugin;
import br.com.uoutec.ediacaran.core.plugins.EntityContextPlugin;

public class PluginInstaller extends AbstractPlugin{
	
	public static final String USE_DEFAULT_AUTHENTICATION_PROVIDER_PROPERTY = "default_authentication";

	public void install() throws Throwable{
		applySecurityConfiguration();
	}

	private void applySecurityConfiguration() throws SecurityRegistryException {
		
		SecurityRegistry securityRegistry = EntityContextPlugin.getEntity(SecurityRegistry.class);
		
		securityRegistry.registerRole(new Role("manager","Manager","Application Manger", null, null, null));
		securityRegistry.registerRole(new Role("user","User","Authenticated user", null, null, null));
		
		/*
		PluginType pluginType = EntityContextPlugin.getEntity(PluginType.class);

		boolean userDefaultAuthenticationProvider = 
				pluginType.getConfiguration().getBoolean(USE_DEFAULT_AUTHENTICATION_PROVIDER_PROPERTY);
		*/
		
		FileLoginModuleProvider flmp = EntityContextPlugin.getEntity(FileLoginModuleProvider.class);
		LoginModuleManager lmm = EntityContextPlugin.getEntity(LoginModuleManager.class);
		lmm.registerLoginModule("file", flmp);
		
	}

	@Override
	public void uninstall() throws Throwable {
		LoginModuleManager lmm = EntityContextPlugin.getEntity(LoginModuleManager.class);
		lmm.unregisterLoginModule("file");
	}
	
	
}
