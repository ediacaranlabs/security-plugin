package br.com.uoutec.community.ediacaran.security.file;

import javax.inject.Inject;

import br.com.uoutec.community.ediacaran.security.AuthenticatedSubject;
import br.com.uoutec.community.ediacaran.security.AuthenticationProvider;
import br.com.uoutec.community.ediacaran.security.LoginModule;
import br.com.uoutec.community.ediacaran.security.Principal;
import br.com.uoutec.community.ediacaran.security.Subject;
import br.com.uoutec.ediacaran.core.UserPrincipalProvider;

public class FileAuthenticationProvider implements AuthenticationProvider {

	private FileUserRepository fileUserRepository;
	
	private UserPrincipalProvider securityProvider;
	
	public FileAuthenticationProvider() {
	}
	
	@Inject
	public FileAuthenticationProvider(FileUserRepository fileUserRepository,
			UserPrincipalProvider securityProvider) {
		this.fileUserRepository = fileUserRepository;
		this.securityProvider = securityProvider;
	}
	
	@Override
	public LoginModule getLoginModule() {
		return new FileLoginModule(fileUserRepository);
	}

	@Override
	public Subject getSubject() {
		Principal userPrincipal = (Principal)securityProvider.getUserPrincipal();
		return new AuthenticatedSubject(userPrincipal);
	}
	
}
