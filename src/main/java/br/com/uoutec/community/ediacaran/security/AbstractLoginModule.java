package br.com.uoutec.community.ediacaran.security;

import java.io.IOException;
import java.security.cert.X509Certificate;
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

import br.com.uoutec.community.ediacaran.security.jaas.UsernamePasswordAuthentication;
import br.com.uoutec.community.ediacaran.security.jaas.X509CertificateAuthentication;

public abstract class AbstractLoginModule implements LoginModule {

	private static final Logger logger = Logger.getLogger(LoginModule.class);
	
	protected CallbackHandler callbackHandler;
	
	protected Map<String, ?> sharedState;
	
	protected Map<String, ?> options;
	
	protected Subject subject;

	protected javax.security.auth.Subject jaaSubject;

	private List<Callback> callbacks;
	
	public AbstractLoginModule(Subject subject, List<Callback> callbacks) {
		this.callbacks = callbacks;
		this.subject = subject;
	}
	
	public void initialize(javax.security.auth.Subject subject, 
			CallbackHandler callbackHandler, Map<String, ?> sharedState, Map<String, ?> options) {
		this.callbackHandler = callbackHandler;
		this.sharedState     = sharedState;
		this.options         = options;
	    this.jaaSubject      = subject;
	}
	
	public Subject getSubject() {
		return subject;
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

        
        Set<X509Certificate> certificates = jaaSubject.getPublicCredentials(X509Certificate.class);
        
        if(certificates.isEmpty()) {
			loginUsernamePassword(callbacks, getSubject());
        }
        else {
			loginCert(certificates, getSubject());
        }
        
        return getSubject().isAuthenticated();
    	
    }
	
	private void loginUsernamePassword(Callback[] callbacks, Subject subject) throws LoginException {
		
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
	
	private void loginCert(Set<X509Certificate> certificates, Subject subject) throws LoginException {
		
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
		jaaSubject.getPrincipals().addAll(getSubject().getPrincipal().getPrincipals());
        return getSubject().isAuthenticated();
    }

	@Override
	public boolean abort() throws LoginException {
		return true;	
	}

	@Override
	public boolean logout() throws LoginException {
		jaaSubject.getPrincipals().removeAll(getSubject().getPrincipal().getPrincipals());
		getSubject().logout();
		return !getSubject().isAuthenticated();
	}

	protected List<Callback> getCallbacks(){
		return callbacks;
	}
	
}
