package com.xzcode.ggserver.core.common.compression.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xzcode.ggserver.core.common.compression.ICompressor;

/**
 * 
 * gzip流压缩器
 * 
 * @author zai
 * 2019-11-10 21:58:29
 */
public class GZIPCompressor implements ICompressor{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(GZIPCompressor.class);

	@Override
	public byte[] compress(byte[] data) {
		try(
				ByteArrayOutputStream out = new ByteArrayOutputStream(data.length);
				GZIPOutputStream gzipOutputStream = new GZIPOutputStream(out);	
			) {
			gzipOutputStream.write(data);
			gzipOutputStream.close();
            return out.toByteArray();
		} catch (Exception e) {
			LOGGER.error("GZIP compress ERROR!", e);
		}
		return null;
	}

	@Override
	public byte[] uncompress(byte[] data) {
		try(
				ByteArrayInputStream in = new ByteArrayInputStream(data);
				GZIPInputStream gzipInputStream = new GZIPInputStream(in);	
				ByteArrayOutputStream out = new ByteArrayOutputStream(data.length);
			) {
			byte[] buffer = new byte[data.length];
			int n = 0;
            while ((n = gzipInputStream.read(buffer)) >= 0) {
            	out.write(buffer, 0 , n);
            }
            gzipInputStream.close();
            return out.toByteArray();
		} catch (Exception e) {
			LOGGER.error("GZIP decompress ERROR!", e);
		}
		return null;
	}

}
