package com.xzcode.ggserver.core.common.compression.impl;

import com.xzcode.ggserver.core.common.compression.ICompressor;

/**
 * 
 * 无用的压缩器
 * 
 * @author zai
 * 2019-11-10 21:58:29
 */
public class NoneCompressor implements ICompressor{
	
	@Override
	public byte[] compress(byte[] data) {
		return data;
	}

	@Override
	public byte[] uncompress(byte[] data) {
		return data;
	}
	

}
