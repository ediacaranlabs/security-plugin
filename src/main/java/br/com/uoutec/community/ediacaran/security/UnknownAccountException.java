package br.com.uoutec.community.ediacaran.security;

public class UnknownAccountException extends AuthorizationException{

	private static final long serialVersionUID = -3041655859579066950L;

	public UnknownAccountException() {
		super();
	}

	public UnknownAccountException(String message, Throwable cause) {
		super(message, cause);
	}

	public UnknownAccountException(String message) {
		super(message);
	}

	public UnknownAccountException(Throwable cause) {
		super(cause);
	}

}
