package br.com.uoutec.community.ediacaran.security.jaas;

public class UsernamePasswordAuthentication implements Authentication {

    private String username;

    private char[] password;


	public UsernamePasswordAuthentication(String username, String password) {
		this(username, password != null? password.toCharArray() : null);
	}
    
	public UsernamePasswordAuthentication(String username, char[] password) {
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public char[] getPassword() {
		return password;
	}

	public void setPassword(char[] password) {
		this.password = password;
	}

	@Override
	public Object getPrincipal() {
		return getUsername();
	}

	@Override
	public Object getCredentials() {
		return getPassword();
	}

}
