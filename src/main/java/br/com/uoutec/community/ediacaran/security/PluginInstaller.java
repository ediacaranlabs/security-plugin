package br.com.uoutec.community.ediacaran.security;

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
		
		am.registerRole("notauthenticated","Not Authenticated","Not Authenticated", null);
		am.registerRole("manager","Manager","Application Manger", null);
		am.registerRole("user","User","Authenticated user", null);
		
		FileAuthenticationProvider flmp = EntityContextPlugin.getEntity(FileAuthenticationProvider.class);
		AuthenticationManager lmm = EntityContextPlugin.getEntity(AuthenticationManager.class);
		lmm.registerAuthenticationProvider("file", flmp);
		
	}

	@Override
	public void uninstall() throws Throwable {
		AuthenticationManager lmm = EntityContextPlugin.getEntity(AuthenticationManager.class);
		lmm.unregisterAuthenticationProvider("file");
	}
	
	
}
