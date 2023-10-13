package br.com.uoutec.community.ediacaran.security.file;

import javax.inject.Inject;

import br.com.uoutec.community.ediacaran.security.LoginModule;
import br.com.uoutec.community.ediacaran.security.LoginModuleProvider;

public class FileLoginModuleProvider implements LoginModuleProvider {

	private FileUserRepository fileUserRepository;
	
	@Inject
	public FileLoginModuleProvider(FileUserRepository fileUserRepository) {
		this.fileUserRepository = fileUserRepository;
	}
	
	@Override
	public LoginModule getLoginModule() {
		return new FileLoginModule(fileUserRepository);
	}

}
