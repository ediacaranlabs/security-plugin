package br.com.uoutec.community.ediacaran.security.file;

import java.util.Arrays;
import java.util.List;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.TextInputCallback;

import br.com.uoutec.community.ediacaran.plugins.PublicBean;
import br.com.uoutec.community.ediacaran.security.AbstractLoginModule;

public class FileLoginModule 
	extends AbstractLoginModule
	implements PublicBean {

	private static final List<Callback> list = 
			Arrays.asList(
					new NameCallback("Username: "), 
					new PasswordCallback("Password: ", false), 
					new TextInputCallback("authMethod")
			);
	
	public FileLoginModule(FileUserRepository fileUserRepository) {
		super(new FileSubject(fileUserRepository), list);
	}

}
