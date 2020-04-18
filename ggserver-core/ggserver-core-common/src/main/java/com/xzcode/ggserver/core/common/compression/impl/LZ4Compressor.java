package com.xzcode.ggserver.core.common.compression.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xzcode.ggserver.core.common.compression.ICompressor;

import net.jpountz.lz4.LZ4BlockInputStream;
import net.jpountz.lz4.LZ4BlockOutputStream;

/**
 * 
 * lz4流压缩器
 * 
 * @author zai
 * 2019-11-10 21:58:29
 */
public class LZ4Compressor implements ICompressor{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LZ4Compressor.class);

	@Override
	public byte[] compress(byte[] data) {
		try(
				ByteArrayOutputStream out = new ByteArrayOutputStream(data.length);
				LZ4BlockOutputStream outputStream = new LZ4BlockOutputStream(out);
			) {
			
			outputStream.write(data);
			outputStream.close();
            return out.toByteArray();
		} catch (Exception e) {
			LOGGER.error("LZ4 compress ERROR!", e);
		}
		return null;
	}

	@Override
	public byte[] uncompress(byte[] data) {
		try(
				ByteArrayInputStream in = new ByteArrayInputStream(data);
				ByteArrayOutputStream out = new ByteArrayOutputStream(data.length);
				LZ4BlockInputStream  inputStream = new LZ4BlockInputStream (in);	
			) {
			byte[] buffer = new byte[data.length];
			int n = 0;
            while ((n = inputStream.read(buffer)) >= 0) {
            	out.write(buffer, 0 , n);
            }
            inputStream.close();
            return out.toByteArray();
		} catch (Exception e) {
			LOGGER.error("LZ4 decompress ERROR!", e);
		}
		return null;
	}
	
	
	
	public static void main(String[] args) {
		ICompressor  compressor = new LZ4Compressor();
		String str = "LZ4Factory factory = LZ4Factory.fastestInstance();\n" +
				"byte[] data = \"12345345234572\".getBytes(\"UTF-8\");\n" +
				"final int decompressedLength = data.length;\n" +
				"compress data\n" +
				"LZ4Compressor compressor = factory.fastCompressor();\n" +
				"int maxCompressedLength = compressor.maxCompressedLength(decompressedLength);\n" +
				"byte[] compressed = new byte[maxCompressedLength];\n" +
				"int compressedLength = compressor.compress(data, 0, decompressedLength, compressed, 0, maxCompressedLength);\n" +
				"decompress data\n" +
				"method 1: when the decompressed length is known\n" +
				"LZ4FastDecompressor decompressor = factory.fastDecompressor();\n" +
				"byte[] restored = new byte[decompressedLength];\n" +
				"int compressedLength2 = decompressor.decompress(compressed, 0, restored, 0, decompressedLength);\n" +
				"compressedLength == compressedLength2\n" +
				"method 2: when the compressed length is known (a little slower)\n" +
				"the destination buffer needs to be over-sized\n" +
				"LZ4SafeDecompressor decompressor2 = factory.safeDecompressor();\n" +
				"int decompressedLength2 = decompressor2.decompress(compressed, 0, compressedLength, restored, 0);\n" +
				"decompressedLength == decompressedLength2";
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
		System.out.println("compressed "+ times +" times, spended " + (end - start) + " ms");
	}

}
