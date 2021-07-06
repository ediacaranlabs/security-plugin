package br.com.uoutec.community.ediacaran.security.pub.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import br.com.uoutec.community.ediacaran.security.pub.Authentication;
import br.com.uoutec.community.ediacaran.security.pub.AuthenticationProvider;

public class EdiacaranRealm extends AuthorizingRealm{

	private AuthenticationProvider authenticationProvider;
	
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		
		Object principal = principals.getPrimaryPrincipal();

        Authentication authentication = authenticationProvider.get(principal);
		
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        
        authorizationInfo.setRoles(authentication.getRoles());
        authorizationInfo.setStringPermissions(authentication.getStringPermissions());
        
        return authorizationInfo;	
    }

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		
        Object principal = token.getPrincipal();
        
        Authentication authentication = authenticationProvider.get(principal);
        
        if(authentication == null) {
            throw new UnknownAccountException();
        }

        if(authentication.isLocked()) {
            throw new LockedAccountException();
        }

        SimpleAuthenticationInfo authenticationInfo = 
        		authentication.getCredentialsSalt() != null?
        				new SimpleAuthenticationInfo(
        						authentication.getUsername(),
        						authentication.getPassword(),
        						ByteSource.Util.bytes(authentication.getCredentialsSalt()),
        						authenticationProvider.getRealmName()
        				) :
        				new SimpleAuthenticationInfo(
        						authentication.getUsername(),
        						authentication.getPassword(),
        						authenticationProvider.getRealmName()
        				);
        					
        return authenticationInfo;
        
	}

}
