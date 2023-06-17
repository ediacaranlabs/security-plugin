package br.com.uoutec.community.ediacaran.security;

import br.com.uoutec.entity.registry.RegistryException;

public class SecurityRegistryException extends RegistryException{

	private static final long serialVersionUID = -7031582070976392259L;

	public SecurityRegistryException() {
		super();
	}

	public SecurityRegistryException(String message, Throwable cause) {
		super(message, cause);
	}

	public SecurityRegistryException(String message) {
		super(message);
	}

	public SecurityRegistryException(Throwable cause) {
		super(cause);
	}

}
