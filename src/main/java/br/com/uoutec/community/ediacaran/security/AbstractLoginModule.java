package br.com.uoutec.community.ediacaran.security;

import java.io.IOException;
import java.security.cert.X509Certificate;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.LoginException;

import org.apache.log4j.Logger;

import br.com.uoutec.community.ediacaran.security.jaas.RolePrincipal;
import br.com.uoutec.community.ediacaran.security.jaas.UserPrincipal;
import br.com.uoutec.community.ediacaran.security.jaas.UsernamePasswordAuthentication;
import br.com.uoutec.community.ediacaran.security.jaas.X509CertificateAuthentication;

public abstract class AbstractLoginModule implements LoginModule {

	private static final Logger logger = Logger.getLogger(LoginModule.class);
	
	protected CallbackHandler callbackHandler;
	
	protected Map<String, ?> sharedState;
	
	protected Map<String, ?> options;
	
	protected javax.security.auth.Subject subject;
	
	protected Set<java.security.Principal> principals;
	
	private List<Callback> callbacks;
	
	private Subject internalSubject;
	
	public AbstractLoginModule(Subject internalSubject, List<Callback> callbacks) {
		this.internalSubject = internalSubject;
		this.callbacks = callbacks;
	}
	
	public void initialize(javax.security.auth.Subject subject, 
			CallbackHandler callbackHandler, Map<String, ?> sharedState, Map<String, ?> options) {
		this.callbackHandler = callbackHandler;
		this.sharedState     = sharedState;
		this.options         = options;
	    this.principals      = new HashSet<java.security.Principal>();
	    this.subject         = subject;
	}
	
	public Subject getSubject() {
		return internalSubject;
	}

    public boolean login() throws LoginException{
    	
        if (callbackHandler == null) {
            throw new LoginException("No CallbackHandler found");
        }

        Callback[] callbacks = getCallbacks().stream().toArray(Callback[]::new);

        try {
        	callbackHandler.handle(callbacks);
        }
        catch (IOException ioe) {
            throw new LoginException(ioe.getMessage());
        }
        catch (UnsupportedCallbackException uce) {
            throw new LoginException(uce.getMessage() + " not available to obtain information from user");
        }

        if(getSubject().isAuthenticated()) {
        	return true;
        }
        
        Set<X509Certificate> certificates = subject.getPublicCredentials(X509Certificate.class);
        
        if(certificates.isEmpty()) {
			loginUsernamePassword(callbacks, getSubject());
        }
        else {
			loginCert(certificates, getSubject());
        }
        
        return getSubject().isAuthenticated();
    	
    }
	
	private void loginUsernamePassword(Callback[] callbacks,
			br.com.uoutec.community.ediacaran.security.Subject subject) throws LoginException {
		
        // user callback get value
        if (((NameCallback) callbacks[0]).getName() == null) {
            throw new LoginException("Username can not be null");
        }
	        
        String login = ((NameCallback) callbacks[0]).getName();

        // password callback get value
        if (((PasswordCallback) callbacks[1]).getPassword() == null) {
            throw new LoginException("Password can not be null");
        }
	        
        String password = new String(((PasswordCallback) callbacks[1]).getPassword());
		
		UsernamePasswordAuthentication token = new UsernamePasswordAuthentication(login, password);
		
		try {
			getSubject().login(token);
		} 
		catch (UnknownAccountException uae) {           
			logger.error("Username Not Found!", uae);        
		}
		catch (IncorrectCredentialsException ice) {     
			logger.error("Invalid Credentials!", ice);       
		}
		catch (LockedAccountException lae) {            
			logger.error("Your Account is Locked!", lae);    
		}
		catch (AuthenticationException ae) {            
			logger.error("Unexpected Error!", ae);           
		}
		
	}
	
	private void loginCert(Set<X509Certificate> certificates,
			br.com.uoutec.community.ediacaran.security.Subject subject) throws LoginException {
		
		X509CertificateAuthentication token = new X509CertificateAuthentication(certificates);

		try {
			subject.login(token);
		} 
		catch (UnknownAccountException uae) {           
			logger.error("Username Not Found!", uae);        
		}
		catch (IncorrectCredentialsException ice) {     
			logger.error("Invalid Credentials!", ice);       
		}
		catch (LockedAccountException lae) {            
			logger.error("Your Account is Locked!", lae);    
		}
		catch (AuthenticationException ae) {            
			logger.error("Unexpected Error!", ae);           
		}
		
	}
	
	@Override
	public boolean commit() throws LoginException {
		
		Principal internalPrincipal = getSubject().getPrincipal();
		
		principals.clear();
		
		principals.add(new UserPrincipal(internalPrincipal));
		
	    Set<String> roles = internalPrincipal.getRoles();
	    
	    if(roles != null && !roles.isEmpty()) {
	    	for(String roleName: roles) {
		        RolePrincipal role = new RolePrincipal(roleName);
		        principals.add(role);
	    	}
	    }
	    
	    subject.getPrincipals().addAll(principals);
	    
        return getSubject().isAuthenticated();
    }

	@Override
	public boolean abort() throws LoginException {
		
		if(principals != null) {
			principals.clear();
		}
		
		return true;	
	}

	@Override
	public boolean logout() throws LoginException {
		
		subject.getPrincipals().removeAll(principals);
		
		if(principals != null) {
			principals.clear();
		}
		
		return true;
	}

	protected List<Callback> getCallbacks(){
		return callbacks;
	}
	
}
