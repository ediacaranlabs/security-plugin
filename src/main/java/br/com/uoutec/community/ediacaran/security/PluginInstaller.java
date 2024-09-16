package br.com.uoutec.community.ediacaran.security;

import br.com.uoutec.application.SystemProperties;
import br.com.uoutec.community.ediacaran.security.file.FileAuthenticationProvider;
import br.com.uoutec.ediacaran.core.AbstractPlugin;
import br.com.uoutec.ediacaran.core.plugins.EntityContextPlugin;

public class PluginInstaller extends AbstractPlugin{
	
	public static final String USE_DEFAULT_AUTHENTICATION_PROVIDER_PROPERTY = "default_authentication";

	public void install() throws Throwable{
		applySecurityConfiguration();
	}

	private void applySecurityConfiguration() throws SecurityRegistryException {
		
		AuthorizationManager am = EntityContextPlugin.getEntity(AuthorizationManager.class);
		
		am.registerRole(BasicRoles.NOT_AUTHENTICATED,	"Not Authenticated","Not Authenticated", null);
		am.registerRole(BasicRoles.MANAGER,				"Manager",          "Application manger", null);
		am.registerRole(BasicRoles.USER,				"User",             "Authenticated user", null);
		am.registerRole(BasicRoles.CLIENT,				"Client",           "Authenticated client", null);
		
		FileAuthenticationProvider flmp = EntityContextPlugin.getEntity(FileAuthenticationProvider.class);
		AuthenticationManager lmm = EntityContextPlugin.getEntity(AuthenticationManager.class);
		lmm.registerAuthenticationProvider("file", flmp);
		
		String configPath = SystemProperties.getProperty("app.config");
		configPath = configPath + "/jaas.config";
		
		SystemProperties.setProperty("java.security.auth.login.config", configPath);
	}

	@Override
	public void uninstall() throws Throwable {
		AuthenticationManager lmm = EntityContextPlugin.getEntity(AuthenticationManager.class);
		lmm.unregisterAuthenticationProvider("file");
		
		SystemProperties.setProperty("java.security.auth.login.config", "");
		
	}
	
	
}
