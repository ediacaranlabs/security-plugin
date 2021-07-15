package br.com.uoutec.community.ediacaran.security;

import br.com.uoutec.community.ediacaran.plugins.EntityContextPlugin;
import br.com.uoutec.community.ediacaran.security.pub.SecurityManagerPlugin;
import br.com.uoutec.community.ediacaran.system.AbstractWebPluginInstaller;

public class PluginInstaller extends AbstractWebPluginInstaller{
	
	public void install() throws Throwable{
		
		SecurityManagerPlugin smp = EntityContextPlugin.getEntity(SecurityManagerPlugin.class);
		
		smp
			.addConstraint("/admin/manager/*")
				.addRole("manager")
				.addRole("user")
			.addConstraint("/admin/*")
				.addRole("user")
			.form("/login", "/login?error=true");
		
		super.install();
	}	
}
