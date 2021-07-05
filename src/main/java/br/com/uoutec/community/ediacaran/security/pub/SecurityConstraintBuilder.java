package br.com.uoutec.community.ediacaran.security.pub;

public interface SecurityConstraintBuilder extends SecurityBuilder{

	public static final String GET = "GET";
	
	public static final String HEAD = "HEAD";
	
	public static final String POST = "POST";
	
	public static final String PUT = "PUT";
	
	public static final String DELETE = "DELETE";
	
	public static final String CONNECT = "CONNECT";
	
	public static final String OPTIONS = "OPTIONS";
	
	public static final String TRACE = "TRACE";
	
	public static final String PATCH = "PATCH";
	
	public static final String NONE = "NONE";

	public static final String INTEGRAL = "INTEGRAL";

	public static final String CONFIDENTIAL = "CONFIDENTIAL";
	
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
