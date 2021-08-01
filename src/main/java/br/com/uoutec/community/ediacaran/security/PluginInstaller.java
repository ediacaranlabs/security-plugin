package br.com.uoutec.community.ediacaran.security;

import br.com.uoutec.application.ClassUtil;
import br.com.uoutec.community.ediacaran.AbstractPlugin;
import br.com.uoutec.community.ediacaran.EdiacaranEventListener;
import br.com.uoutec.community.ediacaran.EdiacaranListenerManager;
import br.com.uoutec.community.ediacaran.plugins.EntityContextPlugin;
import br.com.uoutec.community.ediacaran.plugins.PluginConfiguration;
import br.com.uoutec.community.ediacaran.plugins.PluginException;
import br.com.uoutec.community.ediacaran.security.pub.WebSecurityManagerPlugin;

public class PluginInstaller extends AbstractPlugin{
	
	public static final String CONFIGURATION_LISTENER = "listener";

	private EdiacaranEventListener configurationListener;
	
	public void install() throws Throwable{
		registerListeners();
		applySecurityConfiguration();
	}

	private void applySecurityConfiguration() {
		WebSecurityManagerPlugin webSecurityManagerPlugin = 
				EntityContextPlugin.getEntity(WebSecurityManagerPlugin.class);
	
		webSecurityManagerPlugin
			.addConstraint("/admin/manager/*")
				.addRole("manager")
				.addRole("user")
			.addConstraint("/admin/*")
				.addRole("user")
			.form("/login", "/login?error=true");
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
