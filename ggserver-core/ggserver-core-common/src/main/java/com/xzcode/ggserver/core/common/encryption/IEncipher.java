package com.xzcode.ggserver.core.common.encryption;

public interface IEncipher {
	
	/**
	 * 对字节数组进行加密并返回加密后的字节数组
	 * @param data 未加密的数据源
	 * @return
	 * 
	 * @author zai
	 * 2019-11-10 21:12:53
	 */
	byte[] encrypt(byte[] data);
	
	/**
	 * 对字节数组进行解密并返回解密后的字节数组
	 * @param data 已加密的字节数组
	 * @param encryptionType 已加密的字节数组
	 * @return
	 * 
	 * @author zai
	 * 2019-11-10 21:19:37
	 */
	byte[] decrypt(byte[] data);
}
