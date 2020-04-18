package com.xzcode.ggserver.core.common.handler.serializer;


import java.io.IOException;

/**
 * socket消息 序列化与反序列化接口
 * 
 * 
 * @author zai
 * 2017-07-28
 */
public interface ISerializer {

	/**
	 * 对象序列化成byte数组
	 * 
	 * @param object
	 * @return
	 * @throws IOException
	 * @throws Exception
	 */
	<T> byte[] serialize(T object) throws Exception;

	/**
	 * byte数组反序列化成为对象
	 * 
	 * @param bytes
	 * @param t
	 * @return
	 */
	 <T> T deserialize(byte[] bytes, Class<T> t) throws Exception;
}
