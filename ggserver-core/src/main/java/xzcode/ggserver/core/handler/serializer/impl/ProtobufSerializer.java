package xzcode.ggserver.core.handler.serializer.impl;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.protobuf.Descriptors;
import com.google.protobuf.Message;

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
		T instance = null;
		// 反射
		try {
			Method method = targetClass.getMethod("newBuilder");
			Object obj = method.invoke(null, new Object[] {});
			Message.Builder msgBuilder = (Message.Builder) obj;
			Map<String, String> map=new HashMap<>();
			map.put("token", "eeee");
			map.put("gameId", "11");
			instance=(T)buildMessage(msgBuilder,map);
		} catch (Exception e) {
			e.printStackTrace();
		}
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
	public static Message buildMessage(Message.Builder msgBuilder,Map<String, String> map) {
		Descriptors.Descriptor descriptor = msgBuilder.getDescriptorForType();
		for (Map.Entry<String, String> entry : map.entrySet()) {
			Descriptors.FieldDescriptor filedDescriptor = descriptor.findFieldByName(entry.getKey());
			if (filedDescriptor == null) {
				continue;
			}
			boolean isRepeated = filedDescriptor.isRepeated();
			Descriptors.FieldDescriptor.JavaType type = filedDescriptor.getJavaType();
			if (isRepeated) {
				String value = entry.getValue();
				String[] strArray = value.split(",");
				for (int i = 0; i < strArray.length; ++i) {
					Object valueObject = getObject(strArray[i], type); // getObject
					if (valueObject == null) {
						continue;
					}
					msgBuilder.addRepeatedField(filedDescriptor, valueObject);
				}
			} else {
				Object valueObject = getObject(entry.getValue(), type);
				if (valueObject == null) {
					continue;
				}
				msgBuilder.setField(filedDescriptor,
						getObject(entry.getValue(), type));
			}
		}
		return msgBuilder.build();
	}

	private static Object getObject(String rawString,Descriptors.FieldDescriptor.JavaType type) {
		try {
			switch (type) {
			case INT:
				return Integer.valueOf(rawString);
			case LONG:
				return Long.valueOf(rawString);
			case FLOAT:
				return Float.valueOf(rawString);
			case DOUBLE:
				return Double.valueOf(rawString);
			case BOOLEAN:
				return Boolean.valueOf(rawString);
			case STRING:
				return rawString;
			default:
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}