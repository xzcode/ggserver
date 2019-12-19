package xzcode.ggserver.core.common.handler;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.ByteToMessageDecoder;
import xzcode.ggserver.core.common.config.GGConfig;

/**
 * socket类型选择处理器
 * 
 * @author zai
 * 2019-06-15 14:25:54
 */
public class SocketSelectHandler extends ByteToMessageDecoder {
	
	//private static final Logger LOGGER = LoggerFactory.getLogger(SocketSelectHandler.class);
	
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
			pipeline.addLast(new WebSocketChannelInitializer(this.config));
			
		}else {
			pipeline.addLast(new TcpChannelInitializer(this.config));
		}
		
		in.resetReaderIndex();
		pipeline.remove(SocketSelectHandler.class);
		super.channelActive(ctx);
	}

}
