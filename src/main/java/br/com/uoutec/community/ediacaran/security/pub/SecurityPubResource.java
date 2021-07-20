package br.com.uoutec.community.ediacaran.security.pub;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.brandao.brutos.annotation.Action;
import org.brandao.brutos.annotation.ActionStrategy;
import org.brandao.brutos.annotation.Basic;
import org.brandao.brutos.annotation.Controller;
import org.brandao.brutos.annotation.MappingTypes;
import org.brandao.brutos.annotation.Result;
import org.brandao.brutos.annotation.Transient;
import org.brandao.brutos.annotation.View;
import org.brandao.brutos.annotation.web.RequestMethod;
import org.brandao.brutos.annotation.web.RequestMethodTypes;
import org.brandao.brutos.annotation.web.ResponseErrors;
import org.brandao.brutos.annotation.web.WebActionStrategyType;
import org.brandao.brutos.web.WebFlowController;

import br.com.uoutec.community.ediacaran.VarParser;
import br.com.uoutec.community.ediacaran.core.system.i18n.PluginMessageBundleUtils;
import br.com.uoutec.i18n.MessageLocale;
import br.com.uoutec.pub.entity.InvalidRequestException;

@Controller
@ActionStrategy(WebActionStrategyType.DETACHED)
@Action(value="/login", view=@View("/${plugins.ediacaran.security.template}/admin/login"))
public class SecurityPubResource {

	@Transient
	@Inject
	protected VarParser varParser;
	
	@Action("/login")
	@RequestMethod(RequestMethodTypes.POST)
	@View("/${plugins.ediacaran.security.template}/admin/login_result")
	@ResponseErrors(rendered=false, name="exception")
	@Result("refererResource")
	public String login(
			@Basic(bean="username")
			String username,
			@Basic(bean="password")
			String password,
			@Basic(bean="redirectTo")
			String redirectTo,
			@Basic(mappingType=MappingTypes.VALUE)
			HttpServletRequest request) throws InvalidRequestException {
		
		try{
			request.login(username, password);
		}
		catch(Throwable ex){
			String error = PluginMessageBundleUtils
					.getMessageResourceString(
							SecurityPubResourceMessages.RESOURCE_BUNDLE,
							SecurityPubResourceMessages.login.error.invalid_data, 
							MessageLocale.getLocale());
			throw new InvalidRequestException(error, ex);
		}
		

		String referrer = request.getHeader("referer");
		
		return redirectTo == null? referrer : redirectTo;
	}
	
	@Action("/logout")
	@View("/${plugins.ediacaran.security.template}/admin/logout")
	public void logout(
			@Basic(mappingType=MappingTypes.VALUE)
			HttpServletRequest request) throws ServletException {
		try {
			request.logout();
		}
		finally {
			WebFlowController.redirectTo(varParser.getValue("${plugins.ediacaran.front.login_page}"));
		}
	}
	
}
