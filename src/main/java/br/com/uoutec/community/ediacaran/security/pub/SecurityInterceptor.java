package br.com.uoutec.community.ediacaran.security.pub;

import java.lang.reflect.Method;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.brandao.brutos.ResourceAction;
import org.brandao.brutos.annotation.Intercepts;
import org.brandao.brutos.annotation.InterceptsStack;
import org.brandao.brutos.interceptor.AbstractInterceptor;
import org.brandao.brutos.interceptor.InterceptedException;
import org.brandao.brutos.interceptor.InterceptorHandler;
import org.brandao.brutos.interceptor.InterceptorStack;
import org.brandao.brutos.mapping.Controller;

import br.com.uoutec.community.ediacaran.core.security.GuaranteedAccessTo;
import br.com.uoutec.community.ediacaran.plugins.PublicType;

@InterceptsStack(name="securityStack", isdefault=true)
@Intercepts(isDefault=false)
@Singleton
public class SecurityInterceptor 
	extends AbstractInterceptor
	implements PublicType {

	@Inject
	private SecurityConfig securityConfig;
	
	public void intercepted(InterceptorStack stack, InterceptorHandler handler)
			throws InterceptedException {
		
	}

	public boolean accept(InterceptorHandler handler) {
		
		ResourceAction resourceAction = handler.getResourceAction();
		Method method                 = resourceAction == null? null : resourceAction.getMethod();
		Controller controller         = resourceAction.getController();
		
		if(method != null) {
			return method.isAnnotationPresent(GuaranteedAccessTo.class) || 
				controller.getClassType().isAnnotationPresent(GuaranteedAccessTo.class);
		}
		
		return controller.getClassType().isAnnotationPresent(GuaranteedAccessTo.class);
	}
	
}
