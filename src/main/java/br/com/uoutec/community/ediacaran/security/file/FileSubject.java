package br.com.uoutec.community.ediacaran.security.file;

import br.com.uoutec.community.ediacaran.security.AbstractSubject;
import br.com.uoutec.community.ediacaran.security.AuthorizationException;
import br.com.uoutec.community.ediacaran.security.Principal;
import br.com.uoutec.community.ediacaran.security.jaas.Authentication;

public class FileSubject extends AbstractSubject{

	private FileAuthenticationProvider fileAuthenticationProvider;
	
	private Principal user;
	
	public FileSubject(FileAuthenticationProvider fileAuthenticationProvider) {
		this.fileAuthenticationProvider = fileAuthenticationProvider;
	}

	@Override
	public Principal getPrincipal() {
		return user;
	}

	@Override
	public void login(Authentication token) throws AuthorizationException {
		this.user = fileAuthenticationProvider.login(token);
	}

	@Override
	public void logout() {
		this.user = null;
	}

}
