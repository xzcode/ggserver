package xzcode.ggserver.docs.core.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 扫描到的注解的模型
 * 
 * @author zai
 * 2018-12-30 11:48:07
 */
public class Model {
	
	private String actionId;
	private String desc;
	private String namespace;
	
	private List<ModelProperty> properties;
	
	/**
	 * 添加属性
	 * 
	 * @param property
	 * @author zai
	 * 2018-12-30 11:53:51
	 */
	public void addProperty(ModelProperty property) {
		
		//如果为空,创建新list
		if (this.properties == null) {
			this.properties = new ArrayList<>();			
		}
		this.properties.add(property);
	}
	
	public List<ModelProperty> getProperties() {
		return properties;
	}
	
	public void setProperties(List<ModelProperty> properties) {
		this.properties = properties;
	}
	
	public String getActionId() {
		return actionId;
	}
	public void setActionId(String actionId) {
		this.actionId = actionId;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getNamespace() {
		return namespace;
	}
	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}
	
	
}
