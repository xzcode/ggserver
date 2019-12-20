package xzcode.ggserver.core.common.handler.tcp;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import xzcode.ggserver.core.common.config.GGConfig;
import xzcode.ggserver.core.common.message.Pack;

/**
 *  
 * @author Administrator
 *
 */
public class TcpOutboundHandler extends ChannelOutboundHandlerAdapter {

	private static final Logger LOGGER = LoggerFactory.getLogger(TcpOutboundHandler.class);
	    

	private GGConfig config;
	/*
	private static final Gson GSON = new GsonBuilder()
    		.serializeNulls()
    		.create();
	*/
	
	public TcpOutboundHandler() {
	}
	
	
	public TcpOutboundHandler(GGConfig config) {
		super();
		this.config = config;
	}

	
	
	@Override
	public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
		
		Channel channel = ctx.channel();
		if (!channel.isActive()) {
    		if(LOGGER.isDebugEnabled()){
        		LOGGER.debug("\nWrite channel:{} is inActive...", ctx.channel());        		
        	}
			return;
		}
		
		ByteBuf out = null;
		if (msg instanceof byte[]) {
			byte[] bytes = (byte[]) msg;
			out = ctx.alloc().buffer(bytes.length);
			out.writeBytes(bytes);
			ctx.writeAndFlush(out);
		}
		else {
		
			//调用编码处理器
			out = config.getEncodeHandler().handle(ctx, (Pack) msg);
			
			if(channel.isWritable()){
				ctx.writeAndFlush(out);
			}else {
				if (LOGGER.isInfoEnabled()) {
            		LOGGER.error("Write And Flush msg ERROR!");
            	}
			}
		}
		
		
		
	}

	

}
