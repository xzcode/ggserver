package xzcode.ggserver.docs.core.config;

/**
 * 文档配置
 * 
 * 
 * @author zai
 * 2019-11-30 13:18:10
 */
public class GGDocsConfig {
	
	//扫码的包路径
	private String[] scanPackages;
	
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
	
	
	
	
}
