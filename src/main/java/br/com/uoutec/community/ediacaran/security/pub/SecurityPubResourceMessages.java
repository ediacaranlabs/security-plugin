package br.com.uoutec.community.ediacaran.security.pub;

import br.com.uoutec.community.ediacaran.core.system.i18n.PluginMessageBundleUtils;

public class SecurityPubResourceMessages {

	public static final String RESOURCE_BUNDLE = 
			PluginMessageBundleUtils
				.getPluginResourceBundle(SecurityPubResource.class);

	public final class login{
		
		public final class error{
			
			public static final String invalid_data      = "login.error.invalid_data";
			
			public static final String invalid_user_pass = "login.error.invalid_user_pass";
			
		}
		
	}
	
}
