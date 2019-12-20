package xzcode.ggserver.docs.core.model;

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
	
	
}
