package br.com.uoutec.community.ediacaran.security.file;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.TextInputCallback;

import br.com.uoutec.community.ediacaran.security.AbstractLoginModule;
import br.com.uoutec.community.ediacaran.security.Subject;
import br.com.uoutec.ediacaran.core.plugins.PublicBean;

public class FileLoginModule 
	extends AbstractLoginModule
	implements PublicBean {

	private static final List<Callback> list = 
			Arrays.asList(
					new NameCallback("Username: "), 
					new PasswordCallback("Password: ", false), 
					new TextInputCallback("authMethod")
			);
	
	private Subject subject;
	
	private FileUserRepository fileUserRepository;
	
	public FileLoginModule(FileUserRepository fileUserRepository) {
		super(list);
		this.fileUserRepository = fileUserRepository;
	}

	public void initialize(javax.security.auth.Subject subject, 
			CallbackHandler callbackHandler, Map<String, ?> sharedState, Map<String, ?> options) {
		this.subject = new FileSubject(fileUserRepository);
		super.initialize(subject, callbackHandler, sharedState, options);
	}
	
	public Subject getSubject() {
		return subject;
	}
	
}
