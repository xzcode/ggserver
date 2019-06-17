package xzcode.ggserver.client.handler.tcp;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.timeout.IdleStateHandler;
import xzcode.ggserver.client.config.GGClientConfig;
import xzcode.ggserver.client.constant.GGServerTypeConstants;
import xzcode.ggserver.client.event.GGClientEvents;
import xzcode.ggserver.client.event.GGEventTask;
import xzcode.ggserver.client.handler.common.InboundCommonHandler;
import xzcode.ggserver.client.handler.common.OutboundCommonHandler;
import xzcode.ggserver.client.handler.idle.IdleHandler;

/**
 * socket类型选择处理器
 * 
 * @author zai
 * 2019-06-15 14:25:54
 */
public class InitTcpHandler extends ByteToMessageDecoder {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(InitTcpHandler.class);
	/**
	 * 数据包长度标识 字节数
	 */
	public static final int PACKAGE_LENGTH_BYTES = 4;
	
	/**
	 * 每个数据包最大长度
	 */
	public static final int MAX_PACKAGE_LENGTH = 65536;
	
	/**
	 * 请求标识字符串长度单位 字节数
	 */
	public static final int REQUEST_TAG_LENGTH_BYTES = 2;
	
	/**
	 * 请求头 长度标识字节数
	 */
	public static final int HEADER_BYTES = PACKAGE_LENGTH_BYTES + REQUEST_TAG_LENGTH_BYTES;
	
	
	public InitTcpHandler() {
		
	}

	private GGClientConfig config;
	
	
	
	public InitTcpHandler(GGClientConfig config) {
		this.config = config;
	}

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		
		int readableBytes = in.readableBytes();
		in.markReaderIndex();
		if (readableBytes < GGServerTypeConstants.TCP.length()) {
			return;
		}
		byte[] bytes = new byte[GGServerTypeConstants.TCP.length()];
		in.readBytes(bytes);
		String tag = new String(bytes);
		if (tag.equals(GGServerTypeConstants.TCP)) {
			
			config.getWorkerGroup().submit(new GGEventTask(GGClientEvents.ConnectionState.SUCCESS, null, config));
		}
		ctx.pipeline().remove("InitTcpHandler");
		
		
		
	}


}
