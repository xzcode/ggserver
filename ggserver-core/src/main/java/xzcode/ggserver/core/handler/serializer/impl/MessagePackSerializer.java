package xzcode.ggserver.core.handler.serializer.impl;

import org.msgpack.core.MessageBufferPacker;
import org.msgpack.core.MessagePack;
import org.msgpack.core.MessageUnpacker;
import org.msgpack.jackson.dataformat.MessagePackFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import xzcode.ggserver.core.handler.serializer.ISerializer;

/**
 * 基于MessagePack序列化库的实现
 * 
 * 
 * @author zai
 * 2017-07-28
 */
public class MessagePackSerializer implements ISerializer {
	
	private static final ObjectMapper objectMapper = new ObjectMapper(new MessagePackFactory());
	

	@Override
	public byte[] serialize(Object object) throws Exception {
		if (object instanceof String) {
			String string =  (String) object;  
			MessageBufferPacker packer = new MessagePack.PackerConfig()
					.withSmallStringOptimizationThreshold(256) // String
					.withBufferSize(256)
		            .newBufferPacker();
            packer.packString(string);
            packer.close();
            return packer.toByteArray(); 
		}
	    return objectMapper.writeValueAsBytes(object);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T deserialize(byte[] bytes, Class<T> t) throws Exception {
			if (bytes == null) {
				return null;
			}
			if (t == null) {
				return null;
			}
			if (t == String.class) {
				MessageUnpacker unpacker = new MessagePack.UnpackerConfig()
			            .withStringDecoderBufferSize(1024) // If your data contains many large strings (the default is 8k)
			            .newUnpacker(bytes);
	            String string =  unpacker.unpackString();  
            	unpacker.close();
            	return (T) string;
			}
			return objectMapper.readValue(bytes, t);
	}
	

}
