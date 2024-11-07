package br.com.uoutec.community.ediacaran.security;

public class AuthenticationRequiredException extends SecurityException{

	private static final long serialVersionUID = 3459224174350433L;

	public AuthenticationRequiredException() {
		super();
	}

	public AuthenticationRequiredException(String message, Throwable cause) {
		super(message, cause);
	}

	public AuthenticationRequiredException(String message) {
		super(message);
	}

	public AuthenticationRequiredException(Throwable cause) {
		super(cause);
	}

}
