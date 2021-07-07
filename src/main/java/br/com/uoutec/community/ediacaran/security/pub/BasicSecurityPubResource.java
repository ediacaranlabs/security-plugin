package br.com.uoutec.community.ediacaran.security.pub;

import org.brandao.brutos.annotation.Action;
import org.brandao.brutos.annotation.ActionStrategy;
import org.brandao.brutos.annotation.Controller;
import org.brandao.brutos.annotation.View;
import org.brandao.brutos.annotation.web.WebActionStrategyType;

@Controller
@ActionStrategy(WebActionStrategyType.DETACHED)
@Action(value="/login", view=@View("/${plugins.ediacaran.security.template}/admin/login"))
public class BasicSecurityPubResource {

}
