package com.xzcode.ggserver.core.common.handler.serializer.factory;

import com.xzcode.ggserver.core.common.handler.serializer.ISerializer;
import com.xzcode.ggserver.core.common.handler.serializer.impl.JsonSerializer;
import com.xzcode.ggserver.core.common.handler.serializer.impl.ProtoStuffSerializer;

/**
 * 序列化器工厂类
 *
 * @author zai
 * 2017-08-12 13:49:53
 */
public class SerializerFactory {
	
	private static final ISerializer JSON_SERIALIZER = new JsonSerializer();
	
	private static final ISerializer PROTO_STUFF_SERIALIZER = new ProtoStuffSerializer();
	
	/**
	 * 序列化器类型定义
	 *
	 * @author zai
	 * 2017-08-12 13:47:25
	 */
	public static interface SerializerType{
		
		String JSON = "json";
		
		String PROTO_STUFF = "protostuff";
		
	}
	
	/**
	 * 获取序列化器
	 * @return 返回指定的序列化器实现，默认为json序列化器
	 * @author zai
	 * 2017-08-12 13:50:07
	 */
	public static ISerializer geSerializer(String serializerType) {
		
		switch (serializerType) {
		
		case SerializerType.JSON:
			return JSON_SERIALIZER;
			
		case SerializerType.PROTO_STUFF:
			return PROTO_STUFF_SERIALIZER;
			
		default:
			return JSON_SERIALIZER;
		}
		
	}

}
