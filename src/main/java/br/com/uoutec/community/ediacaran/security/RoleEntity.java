package br.com.uoutec.community.ediacaran.security;

import java.util.Locale;

import br.com.uoutec.community.ediacaran.system.i18n.PluginLanguageUtils;

public class RoleEntity {

	private final String id;
	
	private final String name;
	
	private final String description;
	
	private final String resourceBundle;
	
	private final String nameTemplate;
	
	private final String descriptionTemplate;

	public RoleEntity(String id, String name, String description) {
		this(id, name, description, null, null, null);
	}

	public RoleEntity(String id, String name, String description, 
			String nameTemplate, String descriptionTemplate, String resourceBundle) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.resourceBundle = resourceBundle;
		this.nameTemplate = nameTemplate;
		this.descriptionTemplate = descriptionTemplate;
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
		RoleEntity other = (RoleEntity) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
}
