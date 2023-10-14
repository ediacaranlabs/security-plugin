package br.com.uoutec.community.ediacaran.security;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Singleton;

import br.com.uoutec.community.ediacaran.plugins.PluginType;
import br.com.uoutec.community.ediacaran.plugins.SecurityUtil;

@Singleton
public class LoginModuleManagerImp implements LoginModuleManager{

	public static final String LOGIN_MODULE_PROPERTY = "authentication_provider";
	
	public static final RuntimePermission LIST_MODULE = new RuntimePermission("app.security.module.list");

	public static final RuntimePermission GET_MODULE = new RuntimePermission("app.security.module.get");
	
	public static final RuntimePermission REGISTER_MODULE = new RuntimePermission("app.security.module.register");
	
	public static final RuntimePermission UNREGISTER_MODULE = new RuntimePermission("app.security.module.unregister");
	
	private ConcurrentMap<String, LoginModuleProvider> modules;
	
	private PluginType pluginType;
	
	public LoginModuleManagerImp() {
	}
	
	@Inject
	public LoginModuleManagerImp(PluginType pluginType) {
		this.modules = new ConcurrentHashMap<>();
		this.pluginType = pluginType;
	}
	
	@Override
	public void registerLoginModule(String name, LoginModuleProvider value) {
		
		SecurityUtil.checkPermission(REGISTER_MODULE);
		
		if(modules.putIfAbsent(name, value) != null) {
			throw new IllegalStateException("LoginModuleProvider");
		}
		
	}

	@Override
	public List<String> getLoginModules() {
		
		SecurityUtil.checkPermission(GET_MODULE);
		
		return modules.keySet().stream().collect(Collectors.toList());
	}

	@Override
	public void unregisterLoginModule(String name) {

		SecurityUtil.checkPermission(UNREGISTER_MODULE);
		
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
