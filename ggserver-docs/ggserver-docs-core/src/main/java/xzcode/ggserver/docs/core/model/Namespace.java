package xzcode.ggserver.docs.core.model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 扫描到的注解的模型
 * 
 * @author zai
 * 2018-12-30 11:48:07
 */
public class Namespace {
	
	private String namespace;
	
	private List<Model> models = new CopyOnWriteArrayList<>();
	
	/**
	 * 添加属性
	 * 
	 * @param property
	 * @author zai
	 * 2018-12-30 11:53:51
	 */
	public void addModel(Model model) {
		this.models.add(model);
	}
	
	
	
	public List<Model> getModels() {
		return models;
	}

	public void setModels(List<Model> models) {
		this.models = models;
	}



	public String getNamespace() {
		return namespace;
	}
	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}
	
	
}
