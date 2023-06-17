package br.com.uoutec.community.ediacaran.security;

public class LockedAccountException extends AuthorizationException{

	private static final long serialVersionUID = 5917738942147361909L;

	public LockedAccountException() {
		super();
	}

	public LockedAccountException(String message, Throwable cause) {
		super(message, cause);
	}

	public LockedAccountException(String message) {
		super(message);
	}

	public LockedAccountException(Throwable cause) {
		super(cause);
	}

}
