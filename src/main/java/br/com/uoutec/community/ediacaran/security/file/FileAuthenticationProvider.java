package br.com.uoutec.community.ediacaran.security.file;

import javax.inject.Inject;

import br.com.uoutec.community.ediacaran.security.AuthenticationProvider;
import br.com.uoutec.community.ediacaran.security.LoginModule;

public class FileAuthenticationProvider implements AuthenticationProvider {

	@Inject
	private FileUserRepository fileUserRepository;
	
	@Override
	public LoginModule getLoginModule() {
		return new FileLoginModule(fileUserRepository);
	}
	
}
