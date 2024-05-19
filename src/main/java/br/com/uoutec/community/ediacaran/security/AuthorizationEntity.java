package br.com.uoutec.community.ediacaran.security;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import br.com.uoutec.community.ediacaran.system.i18n.PluginLanguageUtils;

public class AuthorizationEntity implements Authorization{

	private final String id;
	
	private final String name;
	
	private final String description;
	
	private final String resourceBundle;
	
	private final String nameTemplate;
	
	private final String descriptionTemplate;

	private final ConcurrentMap<String, AuthorizationEntity> groups;
	
	public AuthorizationEntity(String id, String name, String description) {
		this(id, name, description, null, null, null, null);
	}
	
	public AuthorizationEntity(String id, String name, String description, Set<AuthorizationEntity> groups) {
		this(id, name, description, null, null, null, groups);
	}
	
	public AuthorizationEntity(String id, String name, String description, 
			String resourceBundle, String nameTemplate, String descriptionTemplate, Set<AuthorizationEntity> groups) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.resourceBundle = resourceBundle;
		this.nameTemplate = nameTemplate;
		this.descriptionTemplate = descriptionTemplate;
		this.groups = new ConcurrentHashMap<String, AuthorizationEntity>();
		
		if(groups != null) {
			groups.stream().forEach(e->this.groups.put(e.getId(), e));
		}
	}

	public String getId() {
		return id;
	}

	public String getName() {
		
		if(resourceBundle != null) {
			Locale locale = PluginLanguageUtils.getLocale();
			return PluginLanguageUtils.getMessageResourceString(this.resourceBundle, this.nameTemplate, locale);
		}
		
		return name;
	}

	public String getDescription() {
		
		if(resourceBundle != null) {
			Locale locale = PluginLanguageUtils.getLocale();
			return PluginLanguageUtils.getMessageResourceString(this.resourceBundle, this.descriptionTemplate, locale);
		}
		
		return description;
	}

	public String getResourceBundle() {
		return resourceBundle;
	}

	public String getNameTemplate() {
		return nameTemplate;
	}

	public String getDescriptionTemplate() {
		return descriptionTemplate;
	}

	public Set<Authorization> getChilds(){
		return new HashSet<Authorization>(groups.values());		
	}
	
	public boolean isPermitted(Object authorization) {

		String value = authorization == null? null : String.valueOf(authorization);
		
		if("*".equals(id)) {
			return true;
		}
		
		value = value == null? null : value.trim();
		
		if(value == null || value.length() == 0) {
			return false;
		}
		
		String[] parts = value.split("\\:+");
		
		return accept(parts, 0);
	}

	private boolean accept(String[] parts, int idx) {
		
		String p = parts[idx];
		
		if(p.equals(id)) {
			
			idx++;
			
			if(idx < parts.length) {
				
				AuthorizationEntity authorization = groups.get(parts[idx]);
				
				if(authorization == null) {
					return false;
				}
				
				return authorization.accept(parts, idx);
			}
			
			return true;
		}
		
		return false;
		
	}
	
	/*
	private boolean accept(String[] parts, int idx) {
		
		if(idx >= parts.length) {
			return groups.isEmpty();
		}
		
		String p = parts[idx];
		
		Authorization authorization = groups.get(p);
		
		if(authorization == null) {
			return groups.containsKey("*") && groups.size() == 1;
		}
		
		return authorization.accept(parts, idx + 1);
	}
    */
	
	void put(AuthorizationEntity value, boolean create, String ... groups) {
		put(value, groups, create, 0);
	}
	
	private void put(AuthorizationEntity value, String[] parts, boolean create, int idx) {

		if(idx >= parts.length) {
			groups.put(value.getId(), value);
			return;
		}
		
		String p = parts[idx];
		
		AuthorizationEntity authorization = groups.get(p);
		
		if(authorization == null) {
			if(create) {
				authorization = new AuthorizationEntity(p, p, p);
				groups.put(p, authorization);
			}
			else {
				throw new IllegalStateException("not found: " + p);
			}
		}

		authorization.put(value, parts, create, idx + 1);
	}

	AuthorizationEntity get(String ... groups) {
		return get(groups, 0);
	}
	
	private AuthorizationEntity get(String[] parts, int idx) {

		if(idx >= parts.length) {
			return this;
		}
		
		String p = parts[idx];
		
		AuthorizationEntity authorization = groups.get(p);
		
		if(authorization == null) {
			return null;
		}

		return authorization.get(parts, idx + 1);
	}

	boolean remove(String ... groups) {
		return groups.length == 0? false : remove(groups, 0);
	}
	
	private boolean remove(String[] parts, int idx) {

		String p = parts[idx];
		
		AuthorizationEntity authorization = groups.get(p);
		
		if(authorization == null) {
			return false;
		}

		if(idx + 1 >= parts.length) {
			return groups.remove(parts[idx]) != null;
		}
		else {
			return authorization.remove(parts, idx + 1);
		}
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		AuthorizationEntity other = (AuthorizationEntity) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
