package br.com.uoutec.community.ediacaran.security.pub.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Singleton;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import br.com.uoutec.community.ediacaran.core.security.AuthenticationProvider;
import br.com.uoutec.community.ediacaran.core.security.AuthorizationException;
import br.com.uoutec.community.ediacaran.core.security.Principal;
import br.com.uoutec.community.ediacaran.core.security.SecurityRegistry;
import br.com.uoutec.community.ediacaran.core.security.Subject;
import br.com.uoutec.community.ediacaran.core.security.jaas.Authentication;
import br.com.uoutec.community.ediacaran.core.security.jaas.UsernamePasswordAuthentication;
import br.com.uoutec.community.ediacaran.plugins.PluginConfigurationMetadata;
import br.com.uoutec.community.ediacaran.plugins.PluginType;

@Singleton
public class FileAuthenticationProvider 
	implements AuthenticationProvider {

	private static Gson gson;

	private static final Type typeOfHashMap = new TypeToken<HashSet<FileUser>>(){}.getType();	
	
	static{
		gson = new GsonBuilder()
        .excludeFieldsWithModifiers(Modifier.TRANSIENT | Modifier.STATIC)
        .serializeNulls()
        .create();		
	}
	
	private static final String USERS_FILE = "users.json";
	
	private Map<String,FileUser> usersMap;
	
	private PluginType pluginType;

	@Inject
	private SecurityRegistry securityRegistry;
	
	public FileAuthenticationProvider() {
	}
	
	@Inject
	public FileAuthenticationProvider(PluginType pluginType) {
		this.pluginType = pluginType;
	}

	@Override
	public Subject createSubject() {
		return new FileSubject(this);
	}

	Principal login(Authentication value) {
		
		if(value instanceof UsernamePasswordAuthentication) {
			return login((UsernamePasswordAuthentication)value);
		}
		
		throw new AuthorizationException("invalid user or password");
		
	}
	
	private Principal login(UsernamePasswordAuthentication value) {
		
		String user = value.getUsername();
		
		String password = value.getPassword() == null? null : new String(value.getPassword());
		
		if(user == null || user.trim().isEmpty()) {
			throw new AuthorizationException("invalid user or password");
		}

		if(password == null || password.trim().isEmpty()) {
			throw new AuthorizationException("invalid user or password");
		}
		
		FileUser fUser = usersMap.get(user);
		
		if(fUser == null || !password.equals(fUser.getPassword())) {
			throw new AuthorizationException("invalid user or password");
		}
		
		return new FilePrincipal(fUser);
	}
	
	@PostConstruct
	private void loadUsers() {
		
		this.usersMap = new HashMap<String,FileUser>();
		
		PluginConfigurationMetadata pcmd = pluginType.getConfiguration().getMetadata();
		
		File basePath = pcmd.getPath().getBase();
		File userFile = new File(basePath, USERS_FILE);
		
		if(!userFile.exists()) {
			return;
		}

		Set<FileUser> data;
			
		try(InputStreamReader in = new InputStreamReader(new FileInputStream(userFile), "UTF-8")){
			data = gson.fromJson(in, typeOfHashMap);
		}
		catch(Throwable e) {
			throw new IllegalStateException(e);
		}
		
		for(FileUser e: data) {
			e.setPermissions(securityRegistry.toAuthorization(e.getStringPermissions()));
			usersMap.put(e.getName(), e);
		}
		
	}
}
