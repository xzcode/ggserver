package xzcode.ggserver.core.common.handler;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import xzcode.ggserver.core.common.config.GGConfig;
import xzcode.ggserver.core.common.handler.common.InboundCommonHandler;
import xzcode.ggserver.core.common.handler.common.OutboundCommonHandler;
import xzcode.ggserver.core.common.handler.tcp.TcpInboundHandler;
import xzcode.ggserver.core.common.handler.tcp.TcpOutboundHandler;
import xzcode.ggserver.core.common.handler.web.WebSocketInboundFrameHandler;
import xzcode.ggserver.core.common.handler.web.WebSocketOutboundFrameHandler;

/**
 * socket类型选择处理器
 * 
 * @author zai
 * 2019-06-15 14:25:54
 */
public class SocketSelectHandler extends ByteToMessageDecoder {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SocketSelectHandler.class);
	
	protected GGConfig config;
	
	public SocketSelectHandler(GGConfig config) {
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
		   	pipeline.addLast(new HttpObjectAggregator(config.getMaxDataLength()));
		   	pipeline.addLast(new WebSocketInboundFrameHandler(this.config));
		   	pipeline.addLast(new InboundCommonHandler(this.config));
		   	
		   	
		   	//OUT
		   	pipeline.addLast(new WebSocketOutboundFrameHandler(this.config ));
		   	pipeline.addLast(new OutboundCommonHandler(this.config));
		   	
			in.resetReaderIndex();
			
		}else {
			//IN
			pipeline.addLast(new TcpInboundHandler(this.config));
			pipeline.addLast(new InboundCommonHandler(this.config));
	        
			//OUT
			pipeline.addLast(new TcpOutboundHandler(this.config));
			pipeline.addLast(new OutboundCommonHandler(this.config));
			
		}
		pipeline.remove(SocketSelectHandler.class);
		super.channelActive(ctx);
	}

}
