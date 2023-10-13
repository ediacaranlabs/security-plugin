package br.com.uoutec.community.ediacaran.security.file;

import javax.inject.Inject;

import br.com.uoutec.community.ediacaran.security.AbstractSubject;
import br.com.uoutec.community.ediacaran.security.AuthorizationException;
import br.com.uoutec.community.ediacaran.security.Principal;
import br.com.uoutec.community.ediacaran.security.jaas.Authentication;

public class FileSubject extends AbstractSubject{

	private FileUserRepository fileUserRepository;
	
	private Principal user;
	
	@Inject
	public FileSubject(FileUserRepository fileUserRepository) {
		this.fileUserRepository = fileUserRepository;
	}

	@Override
	public Principal getPrincipal() {
		return user;
	}

	@Override
	public void login(Authentication token) throws AuthorizationException {
		this.user = fileUserRepository.login(token);
	}

	@Override
	public void logout() {
		this.user = null;
	}

}
