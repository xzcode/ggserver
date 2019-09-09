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

	public <T> byte[] serialize(T object) throws Exception {
		@SuppressWarnings("unchecked")
		Schema<T> schema = (Schema<T>) RuntimeSchema.getSchema(object.getClass());  
        return ProtostuffIOUtil.toByteArray(object, schema, LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE)); 
	}

	@Override
	public <T> T deserialize(byte[] bytes, Class<T> clazz) throws Exception {
		T obj = clazz.newInstance();
		Schema<T> schema = (Schema<T>) RuntimeSchema.getSchema(clazz);  
		ProtostuffIOUtil.mergeFrom(bytes, obj, schema);
        return obj;  
	}
}
