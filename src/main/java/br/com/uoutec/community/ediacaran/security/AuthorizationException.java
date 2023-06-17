package br.com.uoutec.community.ediacaran.security;

public class AuthorizationException extends RuntimeException{

	private static final long serialVersionUID = 3459224174350433L;

	public AuthorizationException() {
		super();
	}

	public AuthorizationException(String message, Throwable cause) {
		super(message, cause);
	}

	public AuthorizationException(String message) {
		super(message);
	}

	public AuthorizationException(Throwable cause) {
		super(cause);
	}

}
