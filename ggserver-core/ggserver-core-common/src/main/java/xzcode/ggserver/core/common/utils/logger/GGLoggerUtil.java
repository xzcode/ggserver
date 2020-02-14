package xzcode.ggserver.core.common.utils.logger;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.Channel;
import xzcode.ggserver.core.common.constant.ProtocolTypeConstants;
import xzcode.ggserver.core.common.message.Pack;
import xzcode.ggserver.core.common.prefebs.pingpong.model.GGPing;
import xzcode.ggserver.core.common.prefebs.pingpong.model.GGPong;

public class GGLoggerUtil {
	private static final Logger LOGGER = LoggerFactory.getLogger(GGLoggerUtil.class);
	
	
	public static Logger getLogger() {
		return LOGGER;
		 
	}
	public static Logger getLogger(Object object) {
		return LoggerFactory.getLogger(object.getClass());
		
	}
	
	/**
	 * 记录包信息日志
	 * 
	 * @param pack
	 * @param packOperType
	 * @param channel
	 * @author zai
	 * 2020-02-06 11:00:28
	 */
	public static void logPack(Pack pack, int packOperType, Channel channel) {
		logPack(pack, packOperType, channel, false);
	}
	
	
	/**
	 * 记录包信息日志
	 * 
	 * @param pack
	 * @param packOperType
	 * @param channel
	 * @return
	 * @author zai
	 * 2019-12-18 15:20:37
	 */
	public static void logPack(Pack pack, int packOperType, Channel channel, boolean showPingPong) {
		
		String protocalType = pack.getProtocolType();
		
		StringBuilder sb = new StringBuilder(256);
		int packLen = 0;
		if (ProtocolTypeConstants.TCP.equals(protocalType)) {
			packLen = 4;
		}
		
		String actionString = pack.getActionString();
		
		if (!showPingPong) {
			if (actionString.equals(GGPing.ACTION_ID) || actionString.equals(GGPong.ACTION_ID)) {
				return;
			}			
		}

		byte[] metadata = pack.getMetadata();
		byte[] action = pack.getAction();
		byte[] message = pack.getMessage();
		
		if (metadata != null) {
			packLen += 2 + metadata.length;
		}
		
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
		if (metadata != null) {
			fillByteList(totalBytes, shortToBytes((short)metadata.length));
			fillByteList(totalBytes, metadata);
		}else {
			fillByteList(totalBytes, shortToBytes((short)0));
		}
		if (action != null) {
			fillByteList(totalBytes, new byte[] {(byte) action.length});
			fillByteList(totalBytes, action);
		}
		if (message != null) {
			fillByteList(totalBytes, message);
		}
		
		if (packOperType == Pack.OperType.REQUEST) {
			sb.append("\nReceived Pack  <----");
		}
		else if (packOperType == Pack.OperType.RESPONSE) {
			sb.append("\nSended Pack  ---->");
		}
		
		sb
		.append("\nthread: {}")
		.append("\nchannel: {}")
		.append("\ntag: {}")
		.append("\npack-length: {}")
		.append("\npack-data: {}")
		.append("\n")
		;
		
		
		GGLoggerUtil.getLogger().info(
				sb.toString(),
				Thread.currentThread().getName(),
				channel,
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
