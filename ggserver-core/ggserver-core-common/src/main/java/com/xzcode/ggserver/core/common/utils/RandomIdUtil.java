package com.xzcode.ggserver.core.common.utils;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 随机id工具
 *
 * @author zai
 * 2020-04-09 11:58:25
 */
public class RandomIdUtil {
	
	private static final String RAND_STRINGS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	
	/**
	 * 新uuid
	 *
	 * @return
	 * @author zai
	 * 2020-04-09 11:58:34
	 */
	public static String newUUID() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	
	/**
	 * 产生24位严格大小写的数字+子母字符串
	 *
	 * @return
	 * @author zai
	 * 2020-04-09 12:09:16
	 */
	public static String newRandomStringId24() {
		ThreadLocalRandom random = ThreadLocalRandom.current();
		StringBuilder sb = new StringBuilder(24);
		for (int i = 0; i < 24; i++) {
			sb.append(RAND_STRINGS.charAt(random.nextInt(RAND_STRINGS.length())));
		}
		return sb.toString();
	}
	
	/**
	 * 产生16位严格大小写的数字+子母字符串
	 *
	 * @return
	 * @author zai
	 * 2020-04-09 12:09:16
	 */
	public static String newRandomStringId16() {
		ThreadLocalRandom random = ThreadLocalRandom.current();
		StringBuilder sb = new StringBuilder(16);
		for (int i = 0; i < 16; i++) {
			sb.append(RAND_STRINGS.charAt(random.nextInt(RAND_STRINGS.length())));
		}
		return sb.toString();
	}
	
	
}
