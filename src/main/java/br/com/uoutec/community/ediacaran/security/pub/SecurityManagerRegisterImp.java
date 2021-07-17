package br.com.uoutec.community.ediacaran.security.pub;

import javax.inject.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.uoutec.community.ediacaran.core.security.SecurityManager;

@Singleton
public class SecurityManagerRegisterImp 
	implements SecurityManagerRegister{

	private static final Logger logger = LoggerFactory.getLogger(SecurityManagerRegister.class);
	
	private SecurityManager securityManager;
	
	@Override
	public void registrySecurityManager(SecurityManager value) {
		//TODO: security
		this.securityManager = value;
		logger.trace("registered security manager: " + value);
	}

	@Override
	public void unregistrySecurityManager() {
		this.securityManager = null;
		logger.trace("unregistered security manager");
	}

	@Override
	public SecurityManager getSecurityManager() {
		return securityManager;
	}

}
