package xzcode.ggserver.core.handler.serializer.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.netty.util.CharsetUtil;
import xzcode.ggserver.core.handler.serializer.ISerializer;

/**
 * json序列化与反序列化工具
 * 
 * 
 * @author zai
 * 2017-07-28
 */
public class JsonSerializer implements ISerializer {

	private static final Gson gson = new GsonBuilder().serializeNulls().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

	@Override
	public byte[] serialize(Object message) throws Exception {
		return gson.toJson(message).getBytes(CharsetUtil.UTF_8);
	}

	@Override
	public <T> T deserialize(byte[] bytes, Class<T> t) throws Exception {
		return gson.fromJson(new String(bytes, CharsetUtil.UTF_8), t);
	}

}
