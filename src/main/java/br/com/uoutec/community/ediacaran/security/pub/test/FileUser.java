package br.com.uoutec.community.ediacaran.security.pub.test;

import java.util.Set;

import br.com.uoutec.community.ediacaran.core.security.Authorization;

public class FileUser {

	private String name;
	
	private String password;
	
    private Set<String> roles;

    private Set<String> stringPermissions;

    private transient Set<Authorization> permissions;
    
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<String> getRoles() {
		return roles;
	}

	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}

	public Set<String> getStringPermissions() {
		return stringPermissions;
	}

	public void setStringPermissions(Set<String> stringPermissions) {
		this.stringPermissions = stringPermissions;
	}

	public Set<Authorization> getPermissions() {
		return permissions;
	}

	public void setPermissions(Set<Authorization> permissions) {
		this.permissions = permissions;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FileUser other = (FileUser) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
}
