package br.com.uoutec.community.ediacaran.security;

import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

public class AuthorizationImp implements Authorization {

	private final String id;
	
	private final String nodeID;
	
	private final InheritanceAuthorizationParser parser;
	
	private final String[] path;
	
	private final ConcurrentMap<String, AuthorizationImp> groups;

	public AuthorizationImp(String id) {
		this(id, new DefaultInheritanceAuthorizationParser(), null);
	}
	
	public AuthorizationImp(String id, InheritanceAuthorizationParser parser, 
			Set<AuthorizationImp> groups) {
		this.parser = parser;
		this.id = id;
		
		String[] pathTMP = this.parser.toInheritance(id);
		
		if(pathTMP.length > 1) {
			this.nodeID = pathTMP[pathTMP.length - 1];
			this.path = Arrays.copyOf(pathTMP, pathTMP.length - 1);
		}
		else {
			this.nodeID = pathTMP[0];
			this.path = new String[0];
		}
		
		this.groups = new ConcurrentHashMap<String, AuthorizationImp>();
		
		if(groups != null) {
			groups.stream().forEach(e->this.groups.put(e.getNodeID(), e));
		}
	}

	public String[] getPath() {
		return this.path;
	}
	
	public String getId() {
		return id;
	}
	
	public String getNodeID() {
		return nodeID;
	}

	public Set<Authorization> getChilds(){
		return groups.values().stream().collect(Collectors.toSet());		
	}
	
	public boolean isPermitted(Object authorization) {

		String value = authorization == null? null : String.valueOf(authorization);
		
		if("*".equals(getNodeID())) {
			return true;
		}
		
		value = value == null? null : value.trim();
		
		if(value == null || value.length() == 0) {
			return false;
		}
		
		String[] parts = value.split("\\:+");
		
		return accept(parts, 0);
	}

	private boolean accept(String[] parts, int idx) {
		
		String p = parts[idx];
		
		if(p.equals(getNodeID())) {
			
			idx++;
			
			if(idx < parts.length) {
				
				AuthorizationImp authorization = groups.get(parts[idx]);
				
				if(authorization == null) {
					return false;
				}
				
				return authorization.accept(parts, idx);
			}
			
			return true;
		}
		
		return false;
		
	}
	
	void put(AuthorizationImp value, boolean create, String ... groups) {
		put(value, groups, create, 0);
	}
	
	private void put(AuthorizationImp value, String[] parts, boolean create, int idx) {

		if(idx >= parts.length) {
			groups.put(value.getNodeID(), value);
			return;
		}
		
		String p = parts[idx];
		
		AuthorizationImp authorization = groups.get(p);
		
		if(authorization == null) {
			if(create) {
				authorization = new AuthorizationImp(p, parser, null);
				groups.put(p, authorization);
			}
			else {
				throw new IllegalStateException("not found: " + p);
			}
		}

		authorization.put(value, parts, create, idx + 1);
	}

	AuthorizationImp get(String ... groups) {
		return get(groups, 0);
	}
	
	private AuthorizationImp get(String[] parts, int idx) {

		if(idx >= parts.length) {
			return this;
		}
		
		String p = parts[idx];
		
		AuthorizationImp authorization = groups.get(p);
		
		if(authorization == null) {
			return null;
		}

		return authorization.get(parts, idx + 1);
	}

	boolean remove(String ... groups) {
		return groups.length == 0? false : remove(groups, 0);
	}
	
	private boolean remove(String[] parts, int idx) {

		String p = parts[idx];
		
		AuthorizationImp authorization = groups.get(p);
		
		if(authorization == null) {
			return false;
		}

		if(idx + 1 >= parts.length) {
			return groups.remove(parts[idx]) != null;
		}
		else {
			return authorization.remove(parts, idx + 1);
		}
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getNodeID() == null) ? 0 : getNodeID().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AuthorizationImp other = (AuthorizationImp) obj;
		if (getNodeID() == null) {
			if (other.getNodeID() != null)
				return false;
		} else if (!getNodeID().equals(other.getNodeID()))
			return false;
		return true;
	}
	
}
