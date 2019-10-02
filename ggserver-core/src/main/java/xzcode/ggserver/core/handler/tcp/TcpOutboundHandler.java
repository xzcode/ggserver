package xzcode.ggserver.core.handler.tcp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import xzcode.ggserver.core.channel.DefaultChannelAttributeKeys;
import xzcode.ggserver.core.config.GGConfig;
import xzcode.ggserver.core.executor.task.ExectionTask;
import xzcode.ggserver.core.message.PackModel;

/**
 *  
 * @author Administrator
 *
 */
public class TcpOutboundHandler extends ChannelOutboundHandlerAdapter {

	private static final Logger LOGGER = LoggerFactory.getLogger(TcpOutboundHandler.class);
	    

	private GGConfig config;
	
	private static final Gson GSON = new GsonBuilder()
    		.serializeNulls()
    		.create();
	
	
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
			out = config.getEncodeHandler().handle(ctx, msg,  promise);
			
			if(channel.isWritable()){
				ctx.writeAndFlush(out);
			}else {
				try {
					if (LOGGER.isInfoEnabled()) {
	                	LOGGER.info("Channel is not writable, change to sync mode! \nchannel:{}", channel);
	                }
					channel.writeAndFlush(out).sync();
	                if (LOGGER.isInfoEnabled()) {
	                	LOGGER.info("Sync message sended. \nchannel:{}\nmessage:{}", channel, GSON.toJson(msg));
	                }
	            } catch (Exception e) {
	            	if (LOGGER.isInfoEnabled()) {
	            		LOGGER.info("write and flush msg exception. msg:[{}]", GSON.toJson(msg), e);
	            	}
	            }
			}
		}
		
		
		
	}

	

}
