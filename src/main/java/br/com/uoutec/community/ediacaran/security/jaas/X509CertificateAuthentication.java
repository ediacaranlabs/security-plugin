package br.com.uoutec.community.ediacaran.security.jaas;

import java.security.cert.X509Certificate;
import java.util.Set;

public class X509CertificateAuthentication implements Authentication{

	private Set<X509Certificate> certificates;
	
	public X509CertificateAuthentication(Set<X509Certificate> certificates) {
		this.certificates = certificates;
	}

	public Set<X509Certificate> getCertificates() {
		return certificates;
	}

	@Override
	public Object getPrincipal() {
		return getCertificates();
	}

	@Override
	public Object getCredentials() {
		return null;
	}

}
