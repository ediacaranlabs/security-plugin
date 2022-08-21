package br.com.uoutec.community.ediacaran.security;

import br.com.uoutec.application.ClassUtil;
import br.com.uoutec.community.ediacaran.AbstractPlugin;
import br.com.uoutec.community.ediacaran.EdiacaranEventListener;
import br.com.uoutec.community.ediacaran.EdiacaranListenerManager;
import br.com.uoutec.community.ediacaran.core.security.AuthorizationManager;
import br.com.uoutec.community.ediacaran.core.security.Role;
import br.com.uoutec.community.ediacaran.core.security.SecurityRegistry;
import br.com.uoutec.community.ediacaran.core.security.SecurityRegistryException;
import br.com.uoutec.community.ediacaran.plugins.EntityContextPlugin;
import br.com.uoutec.community.ediacaran.plugins.PluginConfiguration;
import br.com.uoutec.community.ediacaran.plugins.PluginException;
import br.com.uoutec.community.ediacaran.plugins.PluginType;
import br.com.uoutec.community.ediacaran.security.pub.WebSecurityManagerPlugin;
import br.com.uoutec.community.ediacaran.security.pub.test.FileAuthenticationProvider;

public class PluginInstaller extends AbstractPlugin{
	
	public static final String CONFIGURATION_LISTENER = "listener";

	public static final String USE_DEFAULT_AUTHENTICATION_PROVIDER_PROPERTY = "default_authentication";

	private EdiacaranEventListener configurationListener;
	
	public void install() throws Throwable{
		registerListeners();
		applySecurityConfiguration();
	}

	private void applySecurityConfiguration() throws SecurityRegistryException {
		
		SecurityRegistry securityRegistry = EntityContextPlugin.getEntity(SecurityRegistry.class);
		
		securityRegistry.registerRole(new Role("manager","Manager","Application Manger",null,null,null));
		securityRegistry.registerRole(new Role("user","User","Authenticated user",null,null,null));
		
		WebSecurityManagerPlugin webSecurityManagerPlugin = 
				EntityContextPlugin.getEntity(WebSecurityManagerPlugin.class);
	
		webSecurityManagerPlugin
			.addConstraint("/admin/manager/*")
				.addRole("manager")
				.addRole("user")
			.addConstraint("/admin/*")
				.addRole("user")
			.form("/login", "/login?error=true");
		
		PluginType pluginType = EntityContextPlugin.getEntity(PluginType.class);
		
		boolean userDefaultAuthenticationProvider = 
				pluginType.getConfiguration().getBoolean(USE_DEFAULT_AUTHENTICATION_PROVIDER_PROPERTY);
		
		if(userDefaultAuthenticationProvider) {
			FileAuthenticationProvider fup = EntityContextPlugin.getEntity(FileAuthenticationProvider.class);
			AuthorizationManager sm = EntityContextPlugin.getEntity(AuthorizationManager.class);
			sm.registerAuthenticationProvider(fup);
		}
		
	}
	
	@SuppressWarnings("unchecked")
	private void registerListeners() {

		PluginConfiguration config = getConfiguration();
		
		String listenerClassName = config.getString(CONFIGURATION_LISTENER);
		
		Class<? extends EdiacaranEventListener> listenerClass;
		
		try {
			listenerClass = (Class<? extends EdiacaranEventListener>) ClassUtil.get(listenerClassName);
		}
		catch(Throwable ex) {
			throw new PluginException(ex);
		}

		EdiacaranListenerManager ediacaranListenerManager =
				EntityContextPlugin.getEntity(EdiacaranListenerManager.class);
		
		this.configurationListener = EntityContextPlugin.getEntity(listenerClass);
		
		if(this.configurationListener == null) {
			throw new PluginException("listener not found: " + listenerClass);
		}
		
		ediacaranListenerManager.addListener(this.configurationListener);
	}
	
	@Override
	public void uninstall() throws Throwable {
		removeListeners();
		
		PluginType pluginType = EntityContextPlugin.getEntity(PluginType.class);
		
		boolean userDefaultAuthenticationProvider = 
				pluginType.getConfiguration().getBoolean(USE_DEFAULT_AUTHENTICATION_PROVIDER_PROPERTY);
		
		if(userDefaultAuthenticationProvider) {
			AuthorizationManager sm = EntityContextPlugin.getEntity(AuthorizationManager.class);
			sm.registerAuthenticationProvider(null);
		}
		
	}
	
	private void removeListeners() {
		
		if(this.configurationListener != null) {
			EdiacaranListenerManager ediacaranListenerManager =
					EntityContextPlugin.getEntity(EdiacaranListenerManager.class);
			
			ediacaranListenerManager.removeListener(this.configurationListener);
			this.configurationListener = null;
		}
	}
	
}
