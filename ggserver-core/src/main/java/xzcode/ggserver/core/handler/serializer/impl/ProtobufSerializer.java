package xzcode.ggserver.core.handler.serializer.impl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;
import xzcode.ggserver.core.handler.serializer.ISerializer;
public class ProtobufSerializer  implements ISerializer {

	private static final Logger log = LoggerFactory.getLogger(ProtobufSerializer.class);

	/**
	 * 反序列化对象
	 */
	public <T> T deserialize(byte[] paramArrayOfByte, Class<T> targetClass) {
		if (paramArrayOfByte == null || paramArrayOfByte.length == 0) {
			log.error("Failed to deserialize, byte is empty");
			throw new RuntimeException("Failed to deserialize");
		}

		T instance;
		try {
			instance = targetClass.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new RuntimeException("Failed to deserialize");
		}
		Schema<T> schema = RuntimeSchema.getSchema(targetClass);
		ProtostuffIOUtil.mergeFrom(paramArrayOfByte, instance, schema);
		return instance;
	}

	/**
	 * 序列化对象
	 */
	@SuppressWarnings("unchecked")
	public <T> byte[] serialize(T obj) {
		if (obj == null) {
			throw new RuntimeException("Failed to serializer");
		}
		Schema<Object> schema = (Schema<Object>)RuntimeSchema.getSchema(obj.getClass());
		LinkedBuffer buffer = LinkedBuffer.allocate(1024 * 1024);
		byte[] protoStuff;
		try {
			protoStuff = ProtostuffIOUtil.toByteArray(obj, schema, buffer);
		} catch (Exception e) {
			log.error("Failed to serializer, obj:{}", obj, e);
			throw new RuntimeException("Failed to serializer");
		} finally {
			buffer.clear();
		}
		return protoStuff;
	}
}