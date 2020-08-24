package br.com.uoutec.community.ediacaran.security.pub;

import br.com.uoutec.community.ediacaran.core.security.GuaranteedAccessTo;
import br.com.uoutec.community.ediacaran.core.security.Privilege;

public interface SecurityAccess {

	boolean accept(GuaranteedAccessTo guaranteedAccessTo, Class<? extends Privilege> privilege);
	
}
