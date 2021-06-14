package br.com.uoutec.community.ediacaran.security.pub;

public class SecurityConstraintBuilderImp 
	extends SecurityBuilderImp 
	implements SecurityConstraintBuilder {

	public static final String GET = "GET";
	
	public static final String HEAD = "HEAD";
	
	public static final String POST = "POST";
	
	public static final String PUT = "PUT";
	
	public static final String DELETE = "DELETE";
	
	public static final String CONNECT = "CONNECT";
	
	public static final String OPTIONS = "OPTIONS";
	
	public static final String TRACE = "TRACE";
	
	public static final String PATCH = "PATCH";
	
	private SecurityConstraint sc;
	
	public SecurityConstraintBuilderImp(SecurityConstraint sc, SecurityInfo si) {
		super(si);
		this.sc = sc;
	}
	
	public SecurityConstraintBuilderImp addRole(String value) {
		sc.getRoles().add(new Role(value.toUpperCase(), null));
		return this;
	}
	
	public SecurityConstraintBuilderImp addRole(String value, String description) {
		sc.getRoles().add(new Role(value.toUpperCase(), description));
		return this;
	}
	
	public SecurityConstraintBuilderImp get() {
		sc.getMethods().add(GET);
		return this;
	}
	
	public SecurityConstraintBuilderImp head() {
		sc.getMethods().add(HEAD);
		return this;
	}

	public SecurityConstraintBuilderImp post() {
		sc.getMethods().add(POST);
		return this;
	}
	
	public SecurityConstraintBuilderImp put() {
		sc.getMethods().add(PUT);
		return this;
	}
	
	public SecurityConstraintBuilderImp delete() {
		sc.getMethods().add(DELETE);
		return this;
	}
	
	public SecurityConstraintBuilderImp options() {
		sc.getMethods().add(OPTIONS);
		return this;
	}
	
	public SecurityConstraintBuilderImp trace() {
		sc.getMethods().add(TRACE);
		return this;
	}
	
	public SecurityConstraintBuilderImp patch() {
		sc.getMethods().add(PATCH);
		return this;
	}

	public SecurityConstraintBuilderImp connect() {
		sc.getMethods().add(CONNECT);
		return this;
	}
	
	public SecurityConstraintBuilderImp method(String value) {
		sc.getMethods().add(value.toUpperCase());
		return this;
	}
	
	public SecurityConstraintBuilder setTransportQuarantee(String value) {
		sc.setTransportQuarantee(value);
		return this;
	}
	
}
