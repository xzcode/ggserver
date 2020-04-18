package com.xzcode.ggserver.core.common.compression;

/**
 * 加密器接口
 * 
 * 
 * @author zai
 * 2019-11-10 21:12:43
 */
public interface ICompressor {
	
	/**
	 * 对字节数组进行加密并返回加密后的字节数组
	 * @param data 未加密的数据源
	 * @return
	 * 
	 * @author zai
	 * 2019-11-10 21:12:53
	 */
	byte[] compress(byte[] data);
	
	/**
	 * 解压
	 * @param data
	 * @return
	 * 
	 * @author zai
	 * 2019-11-10 21:19:37
	 */
	byte[] uncompress(byte[] data);
	
}
