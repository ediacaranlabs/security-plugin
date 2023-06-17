package br.com.uoutec.community.ediacaran.security.jaas;

import java.io.IOException;
import java.security.Principal;
import java.security.cert.X509Certificate;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.TextInputCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.LoginException;

import org.apache.log4j.Logger;

import br.com.uoutec.community.ediacaran.plugins.EntityContextPlugin;
import br.com.uoutec.community.ediacaran.security.AuthenticationException;
import br.com.uoutec.community.ediacaran.security.AuthorizationManager;
import br.com.uoutec.community.ediacaran.security.IncorrectCredentialsException;
import br.com.uoutec.community.ediacaran.security.LockedAccountException;
import br.com.uoutec.community.ediacaran.security.UnknownAccountException;

public class LoginModule implements javax.security.auth.spi.LoginModule {

	private static final Logger logger = Logger.getLogger(LoginModule.class);
	
	protected Set<Principal> principals;
	
	private CallbackHandler handler;
	
	private Subject subject;
	
	private boolean authenticated;
	
	private br.com.uoutec.community.ediacaran.security.Principal jaasPrincipal;
	
	private br.com.uoutec.community.ediacaran.security.Subject jaasSubject;
	
	@Override
	public void initialize(Subject subject, 
			CallbackHandler callbackHandler, Map<String, ?> sharedState, Map<String, ?> options) {
		this.handler = callbackHandler;
	    this.subject = subject;
	    this.authenticated = false;
	    this.principals = new HashSet<Principal>();
	}

	@Override
	public boolean login() throws LoginException {

        if (handler == null) {
            throw new LoginException("No CallbackHandler found");
        }

        Callback[] callbacks = new Callback[3];

        callbacks[0] = new NameCallback("Username: ");
        callbacks[1] = new PasswordCallback("Password: ", false);
        callbacks[2] = new TextInputCallback("authMethod");

        if (handler != null) {
            try {
            	handler.handle(callbacks);
            }
            catch (IOException ioe) {
                throw new LoginException(ioe.getMessage());
            }
            catch (UnsupportedCallbackException uce) {
                throw new LoginException(uce.getMessage() + " not available to obtain information from user");
            }
        }

        AuthorizationManager securityManager = EntityContextPlugin.getEntity(AuthorizationManager.class);
        jaasSubject = securityManager.getSubject();

        if(jaasSubject.isAuthenticated()) {
        	return true;
        }
        
        Set<X509Certificate> certificates = subject.getPublicCredentials(X509Certificate.class);
        
        if(certificates.isEmpty()) {
			loginUsernamePassword(callbacks, jaasSubject);
        }
        else {
			loginCert(certificates, jaasSubject);
        }
        
        jaasPrincipal = jaasSubject.getPrincipal();

        return authenticated;
		
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
			subject.login(token);
			authenticated = true;
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
		
		principals.clear();
		
		principals.add(new UserPrincipal(jaasPrincipal));
		
	    Set<String> roles = jaasPrincipal.getRoles();
	    
	    if(roles != null && !roles.isEmpty()) {
	    	for(String roleName: roles) {
		        RolePrincipal role = new RolePrincipal(roleName);
		        principals.add(role);
	    	}
	    }
	    
	    subject.getPrincipals().addAll(principals);
	    
        return authenticated;
    }

	@Override
	public boolean abort() throws LoginException {
		jaasPrincipal = null;
		jaasSubject = null;
		principals.clear();
		return true;	
	}

	@Override
	public boolean logout() throws LoginException {
		subject.getPrincipals().removeAll(principals);
		principals.clear();
		jaasSubject.logout();
		jaasPrincipal = null;
		jaasSubject = null;
		return true;
	}
	
}
