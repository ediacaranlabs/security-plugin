package br.com.uoutec.community.ediacaran.security;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Singleton;

import br.com.uoutec.application.security.ContextSystemSecurityCheck;
import br.com.uoutec.application.security.RuntimeSecurityPermission;
import br.com.uoutec.ediacaran.core.plugins.PluginType;

@Singleton
public class SystemUserLoginModuleManager implements LoginModuleManager{

	public static final String LOGIN_MODULE_PROPERTY = "authentication_provider";
	
	public static final RuntimeSecurityPermission LIST_MODULE = new RuntimeSecurityPermission("app.security.module.list");

	public static final RuntimeSecurityPermission GET_MODULE = new RuntimeSecurityPermission("app.security.module.get");
	
	public static final RuntimeSecurityPermission REGISTER_MODULE = new RuntimeSecurityPermission("app.security.module.register");
	
	public static final RuntimeSecurityPermission UNREGISTER_MODULE = new RuntimeSecurityPermission("app.security.module.unregister");
	
	private ConcurrentMap<String, LoginModuleProvider> modules;
	
	private PluginType pluginType;
	
	public SystemUserLoginModuleManager() {
	}
	
	@Inject
	public SystemUserLoginModuleManager(PluginType pluginType) {
		this.modules = new ConcurrentHashMap<>();
		this.pluginType = pluginType;
	}
	
	@Override
	public void registerLoginModule(String name, LoginModuleProvider value) {
		
		ContextSystemSecurityCheck.checkPermission(REGISTER_MODULE);
		
		if(modules.putIfAbsent(name, value) != null) {
			throw new IllegalStateException("LoginModuleProvider");
		}
		
	}

	@Override
	public List<String> getLoginModules() {
		
		//ContextSystemSecurityCheck.checkPermission(GET_MODULE);
		
		return modules.keySet().stream().collect(Collectors.toList());
	}

	@Override
	public void unregisterLoginModule(String name) {

		ContextSystemSecurityCheck.checkPermission(UNREGISTER_MODULE);
		
		modules.remove(name);
	}

	@Override
	public LoginModule getLoginModule() {
		
		String value = pluginType.getConfiguration().getString(LOGIN_MODULE_PROPERTY);
		
		LoginModuleProvider lmp = modules.get(value);
		
		if(lmp == null) {
			throw new NullPointerException("modules");
		}
		
		LoginModule lm = lmp.getLoginModule();
		
		if(lm == null) {
			throw new NullPointerException("LoginModule");
		}
		
		return lm;
	}

}
