package br.com.uoutec.community.ediacaran.security;

import java.io.Serializable;

import javax.annotation.Priority;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import br.com.uoutec.ediacaran.core.plugins.EntityContextPlugin;

@Interceptor
@RequiresRole("")
@Priority(Interceptor.Priority.LIBRARY_BEFORE)
public class RequiresRoleInterceptor implements Serializable{

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
    	
    	Subject subject = getSubject();
    	
    	if(subject == null) {
    		throw new SecurityException("subject not found");
    	}
    	
    	subject.checkRoles(values);
    	
        return p_invocationContext.proceed();
    }
    
    private String[] getRequiresPermissions(InvocationContext p_invocationContext) {
    	RequiresRole requiresRole = 
    			p_invocationContext.getMethod()
    				.getAnnotation(RequiresRole.class);
    	
    	if(requiresRole == null) {
    		requiresRole = 
    				p_invocationContext.getTarget().getClass()
    					.getAnnotation(RequiresRole.class);
    	}
    	
    	return requiresRole.value();
    }
}
