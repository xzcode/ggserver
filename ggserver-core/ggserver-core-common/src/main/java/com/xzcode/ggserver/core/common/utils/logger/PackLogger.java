package com.xzcode.ggserver.core.common.utils.logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xzcode.ggserver.core.common.config.GGConfig;
import com.xzcode.ggserver.core.common.constant.ProtocolTypeConstants;
import com.xzcode.ggserver.core.common.message.Pack;
import com.xzcode.ggserver.core.common.utils.logger.filter.PackLogFilter;

/**
 * 数据包日志工具类
 *
 * @author zai
 * 2020-04-11 16:00:05
 */
public class PackLogger {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PackLogger.class);
	
	protected GGConfig config;
	
	/**
	 * 包日志过滤器
	 */
	protected List<PackLogFilter> filters = new CopyOnWriteArrayList<PackLogFilter>();
	
	
	public PackLogger(GGConfig config) {
		super();
		this.config = config;
	}

	/**
	 * 添加包日志过滤器
	 *
	 * @param filter
	 * @author zai
	 * 2020-04-11 16:33:26
	 */
	public void addPackLogFilter(PackLogFilter filter) {
		this.filters.add(filter);
	}

	/**
	 * 记录包信息日志
	 *
	 * @param pack
	 * @author zai
	 * 2020-04-11 16:27:56
	 */
	public void logPack(Pack pack) {
		
		if (!LOGGER.isInfoEnabled()) {
			return;
		}
		
		if (!this.config.isEnablePackLogger()) {
			return;
		}
		
		if (this.filters.size() > 0) {
			for (PackLogFilter filter : filters) {
				if (!filter.filter(pack)) {
					return;
				}
			}
		}
		
		String protocalType = pack.getProtocolType();
		
		StringBuilder sb = new StringBuilder(256);
		int packLen = 0;
		if (ProtocolTypeConstants.TCP.equals(protocalType)) {
			packLen = 4;
		}
		
		byte[] action = pack.getAction();
		byte[] message = pack.getMessage();
		
		
		if (action != null) {
			packLen += 1 + action.length;
		}
		
		if (message != null) {
			packLen += message.length;
		}
		
		List<Byte> totalBytes = new ArrayList<Byte>(packLen);
		
		if (protocalType.equals(ProtocolTypeConstants.TCP)) {
			fillByteList(totalBytes, intToBytes(packLen));
		}
		
		if (action != null) {
			fillByteList(totalBytes, new byte[] {(byte) action.length});
			fillByteList(totalBytes, action);
		}
		if (message != null) {
			fillByteList(totalBytes, message);
		}
		int packOperType = pack.getOperType();
		if (packOperType == Pack.OperType.REQUEST) {
			sb.append("\nReceived Pack  <----");
		}
		else if (packOperType == Pack.OperType.RESPONSE) {
			sb.append("\nSended Pack  ---->");
		}
		
		sb
		.append("\nthread: {}")
		.append("\nchannel: {}")
		.append("\naction: {}")
		.append("\npack-length: {}")
		.append("\npack-data: {}")
		.append("\n")
		;
		
		
		LOGGER.info(
				sb.toString(),
				Thread.currentThread().getName(),
				pack.getChannel(),
				new String(action),
				packLen,
				totalBytes
			);
	}
	

	
	
	private static void fillByteList(List<Byte> list, byte[] arr) {
		if (arr == null) {
			return;
		}
		for (byte b : arr) {
			list.add(b);
		}
	}
	
	private static byte[] intToBytes(int n) {  
		  byte[] b = new byte[4];  
		  b[3] = (byte) (n & 0xff);  
		  b[2] = (byte) (n >> 8 & 0xff);  
		  b[1] = (byte) (n >> 16 & 0xff);  
		  b[0] = (byte) (n >> 24 & 0xff);  
		  return b;  
	}
	
	public static byte[] shortToBytes(short number){ 
        int temp = number; 
        byte[] b = new byte[2]; 
        for(int i =0; i < b.length; i++){ 
            b[i]=new Integer(temp &0xff).byteValue();// 
            temp = temp >>8;// 向右移8位 
        } 
        return b; 
    }

}
