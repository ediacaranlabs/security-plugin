package br.com.uoutec.community.ediacaran.security.pub;

public interface SecurityConstraintBuilder extends SecurityBuilder{
	
	SecurityConstraintBuilder addRole(String value);
	
	SecurityConstraintBuilder addRole(String value, String description);
	
	SecurityConstraintBuilder get();
	
	SecurityConstraintBuilder head();

	SecurityConstraintBuilder post();
	
	SecurityConstraintBuilder put();
	
	SecurityConstraintBuilder delete();
	
	SecurityConstraintBuilder options();
	
	SecurityConstraintBuilder trace();
	
	SecurityConstraintBuilder patch();

	SecurityConstraintBuilder connect();
	
	SecurityConstraintBuilder method(String value);
	
	SecurityConstraintBuilder setUserConstraint(String value);
	
}
