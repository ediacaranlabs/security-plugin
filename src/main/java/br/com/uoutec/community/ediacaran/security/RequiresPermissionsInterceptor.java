package br.com.uoutec.community.ediacaran.security;

import java.io.Serializable;

import javax.annotation.Priority;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import br.com.uoutec.ediacaran.core.plugins.EntityContextPlugin;

@Interceptor
@RequiresPermissions("")
@Priority(Interceptor.Priority.LIBRARY_BEFORE)
public class RequiresPermissionsInterceptor implements Serializable{

	private static final long serialVersionUID = 653866956804466834L;

	private Subject getSubject() {
		return getSubjectProvider().getSubject();
	}
	
	private volatile SubjectProvider subjectProvider;

	private SubjectProvider getSubjectProvider() {
		if(subjectProvider == null) {
			synchronized (this) {
				subjectProvider = EntityContextPlugin.getEntity(SubjectProvider.class);
			}
		}
		return subjectProvider;
	}
	
    @AroundInvoke
    public Object activateRequestContext(final InvocationContext p_invocationContext) throws Exception {
    	
    	String[] values = getRequiresPermissions(p_invocationContext);
    	
    	Subject subject = getSubject();//securityManager.getSubject();
    	
    	if(subject == null) {
    		throw new SecurityException("subject not found");
    	}
    	
    	subject.checkPermissions(values);
    	
        return p_invocationContext.proceed();
    }
    
    private String[] getRequiresPermissions(InvocationContext p_invocationContext) {
    	RequiresPermissions requiresPermissions = 
    			p_invocationContext.getMethod()
    				.getAnnotation(RequiresPermissions.class);
    	
    	if(requiresPermissions == null) {
    		requiresPermissions = 
    				p_invocationContext.getTarget().getClass()
    					.getAnnotation(RequiresPermissions.class);
    	}
    	
    	return requiresPermissions.value();
    }
}
