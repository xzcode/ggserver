package com.xzcode.ggserver.core.common.compression.impl;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xzcode.ggserver.core.common.compression.ICompressor;

import net.jpountz.lz4.LZ4Compressor;
import net.jpountz.lz4.LZ4Factory;
import net.jpountz.lz4.LZ4SafeDecompressor;

/**
 * 
 * lz4快速压缩器
 * 
 * @author zai
 * 2019-11-10 21:58:29
 */
public class LZ4FastCompressor implements ICompressor{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LZ4FastCompressor.class);
	private static final LZ4Factory factory = LZ4Factory.fastestInstance();
	
	@Override
	public byte[] compress(byte[] data) {
		LZ4Compressor fastCompressor = factory.fastCompressor();
		return fastCompressor.compress(data);
	}

	@Override
	public byte[] uncompress(byte[] data) {
		if (data == null) {
			return data;
		}
		LZ4SafeDecompressor safeDecompressor = factory.safeDecompressor();
		byte[] dist = null;
		int count = 1 ;
		int times = 3 ;
		while (true) {
			try {
				dist = new byte[data.length * times];
				safeDecompressor.decompress(data, dist);
				break;
			} catch (Exception e) {
				count++;
				times *= 10;
				if (count >3) {
					throw new RuntimeException(e);
				}
			}
		}
		return dist;
	}
	
	
	
	public static void main(String[] args) {
		ICompressor  compressor = new LZ4FastCompressor();
		String str = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAJ0ZUE4ZlcJsJZlSJ5F541F8o2qS13EqsYJim95xl5FUQrkPLPHgzLgtibAuIggh/8MTH3PRTKgVdDzCByfVuc5/Ub9pP6c6EHoOMQoGtjeTQ99mc4xLiEDwL6rq48Lo2+N1TNA6wH0SZfGr2rl40BLrJy8GjUMeArCoZCiTY5DTAgMBAAECgYAVAXx+ZOdkbsd8P8Lwrcaab7r+FhJenGHN2FeVH8Uvdn/RdNkCopddDSG7AAAm7DzhnZ34A4VneC7prNv+FJLadroeqztwsVICNWlRlDdoWEEE7gA5OWge07BsLTUtvxiWpZ2KDPdnq/pCIYN7LH4oW6PHUZFS0QOYalZG5qfY+QJBAN6EfRKgSoZcYphFF2WZWsGEzZOPLbpK+4+gcvm3Ihz5CtMvWCUl1OtvgqagDvMoBGfjnjCtQncJiCrNiElrlY0CQQC0vNvRK8P6wpDyxArGApVBa3vXAbj+on1ir0yT6ee/osI/5KD/gVbPWkh3ByvhnJR+LT9oGajEd5Uq7CgxdzffAkEA0iidnB7p5BaTRC9VFq8NKWLNaoU68gzppNAsZy8Qt/56u9SmUod1nls2MTtQg1UTPC+dc2ngMV8+TPbLtlQ27QJAEPA6MapGXbPqXbYdxFztnAn0uzvAGK0lzx/ar2oWfBFG3zIQHKIEfr5ZWD5l0GkaSZD4BkuRU4hZhIJJaglgRQJBAIbnjBHd4SUGaqGROx5XXc4Z5luNLVDWr0cvd+eHkeDBxQUn7OqZboJGS3PafsJLO48EuGo6UsGiHCFtVt2xO1s=";
		byte[] data = str.getBytes();
		
		System.out.println("source data: " + Arrays.toString(data));
		System.out.println("source size: " + data.length);
		System.out.println();
		byte[] compressedData = compressor.compress(data);
		System.out.println("compressed data: " + Arrays.toString(compressedData));
		System.out.println("compressed size: " + compressedData.length);
		System.out.println();
		byte[] uncompressedData = compressor.uncompress(compressedData);
		System.out.println("uncompressed data: " + Arrays.toString(uncompressedData));
		System.out.println("uncompressed size: " + uncompressedData.length);
		
		int times = 100 * 10000;
		long start = 0L;
		long end = 0L;
		start = System.currentTimeMillis();
		for (int i = 0; i < times; i++) {
			compressor.compress(data);			
		}
		end = System.currentTimeMillis();
		System.out.println("compress "+ times +" times, spended " + (end - start) + " ms");
		
		start = System.currentTimeMillis();
		for (int i = 0; i < times; i++) {
			compressor.uncompress(compressedData);			
		}
		end = System.currentTimeMillis();
		System.out.println("uncompress "+ times +" times, spended " + (end - start) + " ms");
		
	}

}
