package br.com.uoutec.community.ediacaran.security.pub;

import org.brandao.brutos.ApplicationContext;
import org.brandao.brutos.MvcResponse;
import org.brandao.brutos.mapping.Action;
import org.brandao.brutos.mapping.Controller;

public interface SecurityAccess {

	boolean accept(Action action, Controller controller, MvcResponse response, ApplicationContext context);
	
}
