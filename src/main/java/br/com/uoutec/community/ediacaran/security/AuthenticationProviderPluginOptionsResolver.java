package br.com.uoutec.community.ediacaran.security;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import br.com.uoutec.ediacaran.core.plugins.PluginOptionsResolver;
import br.com.uoutec.ediacaran.core.plugins.PluginPropertyOption;

@Singleton
public class AuthenticationProviderPluginOptionsResolver 
	implements PluginOptionsResolver{

	@Inject
	private AuthenticationManager loginModuleManager;
	
	@Override
	public List<PluginPropertyOption> getOptions() {
		
		List<String> list = loginModuleManager.getAuthenticationProviders();
		
		List<PluginPropertyOption> result = new ArrayList<>();
		
		for(String r: list) {
			result.add(new PluginPropertyOption(r, r));
		}
		
		return result;
	}

}
