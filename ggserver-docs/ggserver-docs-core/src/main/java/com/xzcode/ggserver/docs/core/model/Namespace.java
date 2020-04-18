package com.xzcode.ggserver.docs.core.model;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 扫描到的注解的模型
 * 
 * @author zai
 * 2018-12-30 11:48:07
 */
public class Namespace {
	
	private String name;
	
	private String description;
	
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



	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
