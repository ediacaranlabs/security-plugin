package br.com.uoutec.community.ediacaran.security;

public class IncorrectCredentialsException extends AuthorizationException{

	private static final long serialVersionUID = -5577114202405283217L;

	public IncorrectCredentialsException() {
		super();
	}

	public IncorrectCredentialsException(String message, Throwable cause) {
		super(message, cause);
	}

	public IncorrectCredentialsException(String message) {
		super(message);
	}

	public IncorrectCredentialsException(Throwable cause) {
		super(cause);
	}

}
