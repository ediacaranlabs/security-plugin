package br.com.uoutec.community.ediacaran.security.file;

import java.io.File;
import java.io.InputStreamReader;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import br.com.uoutec.community.ediacaran.io.FileSystem;
import br.com.uoutec.community.ediacaran.plugins.PluginConfigurationMetadata;
import br.com.uoutec.community.ediacaran.plugins.PluginType;
import br.com.uoutec.community.ediacaran.security.AuthorizationException;
import br.com.uoutec.community.ediacaran.security.Principal;
import br.com.uoutec.community.ediacaran.security.SecurityRegistry;
import br.com.uoutec.community.ediacaran.security.jaas.Authentication;
import br.com.uoutec.community.ediacaran.security.jaas.UsernamePasswordAuthentication;

public class FileUserRepository {

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
	
	public FileUserRepository() {
	}
	
	@Inject
	public FileUserRepository(PluginType pluginType) {
		this.pluginType = pluginType;
	}

	public Principal login(Authentication value) {
		
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
			
		try(InputStreamReader in = new InputStreamReader( FileSystem.getInputStream(userFile), "UTF-8")){
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
