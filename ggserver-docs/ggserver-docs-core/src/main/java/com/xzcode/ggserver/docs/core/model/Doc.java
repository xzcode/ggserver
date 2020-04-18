package com.xzcode.ggserver.docs.core.model;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 扫描到的注解的模型
 * 
 * @author zai
 * 2018-12-30 11:48:07
 */
public class Doc {
	
	private Map<String, Namespace> namespaces = new ConcurrentHashMap<>();
	
	private String actionIdPrefix = "";
	
	private String messageModelPrefix = "";
	
	private String auth;
	
	/**
	 * 获取命名空间
	 * 
	 * @param name
	 * @author zai
	 * 2019-12-17 15:50:53
	 */
	public Namespace getNamespace(String name) {
		return namespaces.get(name);
	}
	
	public Namespace addNamespace(Namespace namespace) {
		return namespaces.put(namespace.getName(), namespace);
	}
	
	public Map<String, Namespace> getNamespaces() {
		return namespaces;
	}

	public String getActionIdPrefix() {
		return actionIdPrefix;
	}

	public void setActionIdPrefix(String actionIdPrefix) {
		this.actionIdPrefix = actionIdPrefix;
	}

	public String getMessageModelPrefix() {
		return messageModelPrefix;
	}

	public void setMessageModelPrefix(String messageModelPrefix) {
		this.messageModelPrefix = messageModelPrefix;
	}

	public void setNamespaces(Map<String, Namespace> namespaces) {
		this.namespaces = namespaces;
	}
	public String getAuth() {
		return auth;
	}
	
	public void setAuth(String auth) {
		this.auth = auth;
	}
	
	
}
