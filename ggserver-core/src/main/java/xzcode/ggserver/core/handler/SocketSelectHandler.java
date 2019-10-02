package xzcode.ggserver.core.handler;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import xzcode.ggserver.core.config.GGConfig;
import xzcode.ggserver.core.handler.codec.impl.DefaultEncodeHandler;
import xzcode.ggserver.core.handler.common.InboundCommonHandler;
import xzcode.ggserver.core.handler.common.OutboundCommonHandler;
import xzcode.ggserver.core.handler.tcp.TcpInboundHandler;
import xzcode.ggserver.core.handler.tcp.TcpOutboundHandler;
import xzcode.ggserver.core.handler.web.WebSocketInboundFrameHandler;
import xzcode.ggserver.core.handler.web.WebSocketOutboundFrameHandler;

/**
 * socket类型选择处理器
 * 
 * @author zai
 * 2019-06-15 14:25:54
 */
public class SocketSelectHandler extends ByteToMessageDecoder {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SocketSelectHandler.class);
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
	
	private GGConfig config;
	
	public SocketSelectHandler() {
		
	}
	
	

	public SocketSelectHandler(GGConfig config) {
		super();
		this.config = config;
	}



	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		
		int readableBytes = in.readableBytes();
		in.markReaderIndex();
		if (readableBytes < 3) {
			return;
		}
		byte[] bytes = new byte[3];
		in.readBytes(bytes);
		String tag = new String(bytes, config.getCharset());
		ChannelPipeline pipeline = ctx.pipeline();
		if (tag.equalsIgnoreCase("GET")) {
			
			//IN
			pipeline.addLast(new HttpServerCodec());
		   	pipeline.addLast(new HttpObjectAggregator(config.getHttpMaxContentLength()));
		   	pipeline.addLast(new WebSocketInboundFrameHandler(this.config));
		   	pipeline.addLast(new InboundCommonHandler(this.config));
		   	
		   	
		   	//OUT
		   	pipeline.addLast(new WebSocketOutboundFrameHandler(this.config ));
	        pipeline.addLast(new OutboundCommonHandler());
			
			
			in.resetReaderIndex();
			
		}else {
			//IN
			pipeline.addLast(new TcpInboundHandler(this.config));
			pipeline.addLast(new InboundCommonHandler(this.config));
	        
			//OUT
			pipeline.addLast(new TcpOutboundHandler(this.config));
			pipeline.addLast(new OutboundCommonHandler());
			
			
			
			
			if (!"tcp".equalsIgnoreCase(tag)) {
				in.resetReaderIndex();
			}else {
				ctx.channel().writeAndFlush("tcp".getBytes());
			}
		}
		
		pipeline.remove("TcpSocketSelectHandler");
		
	}


}
