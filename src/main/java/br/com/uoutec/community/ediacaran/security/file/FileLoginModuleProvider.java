package br.com.uoutec.community.ediacaran.security.file;

import javax.inject.Inject;

import br.com.uoutec.community.ediacaran.security.AuthenticatedSubject;
import br.com.uoutec.community.ediacaran.security.LoginModule;
import br.com.uoutec.community.ediacaran.security.LoginModuleProvider;
import br.com.uoutec.community.ediacaran.security.Principal;
import br.com.uoutec.community.ediacaran.security.Subject;
import br.com.uoutec.community.ediacaran.security.jaas.UserPrincipal;
import br.com.uoutec.ediacaran.core.SecurityProvider;
import br.com.uoutec.ediacaran.core.plugins.EntityContextPlugin;

public class FileLoginModuleProvider implements LoginModuleProvider {

	private FileUserRepository fileUserRepository;
	
	public FileLoginModuleProvider() {
	}
	
	@Inject
	public FileLoginModuleProvider(FileUserRepository fileUserRepository) {
		this.fileUserRepository = fileUserRepository;
	}
	
	@Override
	public LoginModule getLoginModule() {
		return new FileLoginModule(fileUserRepository);
	}

	@Override
	public Subject getSubject() {
		SecurityProvider securityProvider = 
				EntityContextPlugin.getEntity(SecurityProvider.class);
		
		UserPrincipal userPrincipal = (UserPrincipal)securityProvider.getUserPrincipal();
		
		if(userPrincipal instanceof Principal) {
			return new AuthenticatedSubject(userPrincipal);
		}
		
		return null;
	}
	
}
