package br.com.uoutec.community.ediacaran.security.pub;

import java.io.IOException;
import java.security.Principal;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;

public class EdiacaranLoginModule implements LoginModule {

	private static final Logger logger = Logger.getLogger(LoginModule.class);
	
	protected Set<Principal> principals = new HashSet<>();
	
	private CallbackHandler handler;
	
	private Subject subject;
	
	private String user;
	
	private boolean authenticated = false;
	
	@Override
	public void initialize(Subject subject, 
			CallbackHandler callbackHandler, Map<String, ?> sharedState, Map<String, ?> options) {
		this.handler = callbackHandler;
	    this.subject = subject;		
	}

	@Override
	public boolean login() throws LoginException {
		
        if (handler == null) {
            throw new LoginException("No CallbackHandler found");
        }

        Callback[] callbacks = new Callback[2];

        callbacks[0] = new NameCallback("Username: ");
        callbacks[1] = new PasswordCallback("Password: ", false);
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

        // user callback get value
        if (((NameCallback) callbacks[0]).getName() == null) {
            throw new LoginException("Username can not be null");
        }
	        
        user = ((NameCallback) callbacks[0]).getName();

        // password callback get value
        if (((PasswordCallback) callbacks[1]).getPassword() == null) {
            throw new LoginException("Password can not be null");
        }
	        
        String password = new String(((PasswordCallback) callbacks[1]).getPassword());

        org.apache.shiro.subject.Subject subject = SecurityUtils.getSubject();

        if (!subject.isAuthenticated()) {
        	
        	  UsernamePasswordToken token = new UsernamePasswordToken(user, password);
        	  
        	  token.setRememberMe(true);
        	  
        	  try{
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
        
        return authenticated;
		
	}

	@Override
	public boolean commit() throws LoginException {
		subject.getPrincipals().addAll(principals);
        return authenticated;
    }

	@Override
	public boolean abort() throws LoginException {
		user = null;
		principals.clear();
		user = null;
		return true;	
	}

	@Override
	public boolean logout() throws LoginException {
		user = null;
		subject.getPrincipals().removeAll(principals);
		principals.clear();
		return true;
	}

}
