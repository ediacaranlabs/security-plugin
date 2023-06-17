package br.com.uoutec.community.ediacaran.security;

public class AuthenticationException extends AuthorizationException{

	private static final long serialVersionUID = 3459224174350433L;

	public AuthenticationException() {
		super();
	}

	public AuthenticationException(String message, Throwable cause) {
		super(message, cause);
	}

	public AuthenticationException(String message) {
		super(message);
	}

	public AuthenticationException(Throwable cause) {
		super(cause);
	}

}
