package br.com.uoutec.community.ediacaran.security;

import br.com.uoutec.community.ediacaran.AbstractPlugin;
import br.com.uoutec.community.ediacaran.plugins.EntityContextPlugin;
import br.com.uoutec.community.ediacaran.security.pub.WebSecurityManagerPlugin;

public class PluginInstaller extends AbstractPlugin{
	
	public void install() throws Throwable{
		
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

	@Override
	public void uninstall() throws Throwable {
		// TODO Auto-generated method stub
		
	}	
}
