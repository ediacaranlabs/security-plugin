package br.com.uoutec.community.ediacaran.security.pub;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.brandao.brutos.ResourceAction;
import org.brandao.brutos.annotation.Intercepts;
import org.brandao.brutos.annotation.InterceptsStack;
import org.brandao.brutos.interceptor.AbstractInterceptor;
import org.brandao.brutos.interceptor.InterceptedException;
import org.brandao.brutos.interceptor.InterceptorHandler;
import org.brandao.brutos.interceptor.InterceptorStack;
import org.brandao.brutos.mapping.Action;
import org.brandao.brutos.mapping.Controller;
import org.brandao.brutos.web.WebMvcRequest;

import br.com.uoutec.community.ediacaran.VarParser;
import br.com.uoutec.community.ediacaran.plugins.EntityContextPlugin;
import br.com.uoutec.community.ediacaran.plugins.PluginData;
import br.com.uoutec.community.ediacaran.plugins.PublicType;

@Singleton
@Intercepts(isDefault=false)
@InterceptsStack(name="securityStack", isdefault=true)
public class SecurityInterceptor 
	extends AbstractInterceptor
	implements PublicType {

	public static final String ADM_CONTEXT = "${plugins.adm.context}";
	
	@Inject
	private SecurityConfig securityConfig;
	
	@Inject
	protected VarParser varParser;
	
	public void intercepted(InterceptorStack stack, InterceptorHandler handler)
			throws InterceptedException {
		
		ResourceAction resourceAction         = handler.getRequest().getResourceAction();
		Controller controller                 = resourceAction.getController();
		Action action                         = resourceAction.getMethodForm();
		SecurityAccess securityAccess         = securityConfig.getSecurityAccess();
		if(securityAccess.accept(action, controller, handler.getResponse(), handler.getContext())) {
			stack.next(handler);
		}
		
	}

	public boolean accept(InterceptorHandler handler) {
		//PluginData pd     = EntityContextPlugin.getEntity(PluginData.class);
		//String path       = pd.getPath();
		String admContext = varParser.getValue(ADM_CONTEXT);
		//String prefix     = path + admContext;
		WebMvcRequest request = (WebMvcRequest) handler.getRequest();

		String requestID = request.getRequestId();
		
		return requestID.startsWith(admContext);
		//return requestID.startsWith(prefix);
	}
	
}
