package com.xzcode.ggserver.docs.core.config;

/**
 * 文档配置
 * 
 * 
 * @author zai
 * 2019-11-30 13:18:10
 */
public class GGDocsConfig {
	
	//扫描的包路径
	private String[] scanPackages;
	
	//排除扫描的包路径
	private String[] excludedPackages = {};
	
	//命名空间
	private String namespace = "";
	
	//命名空间说明
	private String namespaceDesc = "";
	
	private String actionIdPrefix = "";
	
	private String messageModelPrefix = "";

	public String[] getScanPackages() {
		return scanPackages;
	}

	public void setScanPackages(String[] scanPackages) {
		this.scanPackages = scanPackages;
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

	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	public String getNamespaceDesc() {
		return namespaceDesc;
	}

	public void setNamespaceDesc(String namespaceDesc) {
		this.namespaceDesc = namespaceDesc;
	}

	public String[] getExcludedPackages() {
		return excludedPackages;
	}

	public void setExcludedPackages(String[] excludedPackages) {
		this.excludedPackages = excludedPackages;
	}
	
	
	
	
}
