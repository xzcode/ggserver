package xzcode.ggserver.core.handler.serializer.impl;

import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;
import xzcode.ggserver.core.handler.serializer.ISerializer;

/**
 * ProtoStuff序列化与反序列化工具
 * 
 * 
 * @author zai
 * 2019-09-09 10:51:42
 */
public class ProtoStuffSerializer implements ISerializer {

	@SuppressWarnings("unchecked")
	public <T> byte[] serialize(T object) throws Exception {
        return ProtostuffIOUtil.toByteArray(object, (Schema<T>) RuntimeSchema.getSchema(object.getClass()), LinkedBuffer.allocate(512));
	}

	@Override
	public <T> T deserialize(byte[] bytes, Class<T> clazz) throws Exception {
		T obj = clazz.newInstance();
		ProtostuffIOUtil.mergeFrom(bytes, obj, RuntimeSchema.getSchema(clazz));
        return obj;  
	}
}
