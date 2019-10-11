package xzcode.ggserver.core.common.handler.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import xzcode.ggserver.core.common.config.GGConfig;

/**
 * websocket 消息发送处理器
 * 
 * @author zai
 * 2018-12-29 14:01:59
 */
public class WebSocketOutboundFrameHandler extends ChannelOutboundHandlerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketOutboundFrameHandler.class);
    
    private GGConfig config;
    
    private static final Gson GSON = new GsonBuilder()
    		.serializeNulls()
    		.create();
    
    public WebSocketOutboundFrameHandler() {
    	
	}
    
	public WebSocketOutboundFrameHandler(GGConfig config) {
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
		
		/*
		if(msg instanceof DefaultFullHttpResponse){
			super.write(ctx, msg, promise);
		}
		*/
			
		//调用编码处理器
		ByteBuf out = config.getEncodeHandler().handle(ctx, msg,  promise);
		
		if(channel.isWritable()){
			ctx.writeAndFlush(new BinaryWebSocketFrame(out));
		}else {
			try {
				if (LOGGER.isInfoEnabled()) {
                	LOGGER.info("Channel is not writable, change to sync mode! \nchannel:{}", channel);
                }
				channel.writeAndFlush(new BinaryWebSocketFrame(out)).sync();
                if (LOGGER.isInfoEnabled()) {
                	LOGGER.info("Sync message sended. \nchannel:{}\nmessage:{}", channel, GSON.toJson(msg));
                }
            } catch (InterruptedException e) {
            	if (LOGGER.isInfoEnabled()) {
            		LOGGER.info("write and flush msg exception. msg:[{}]", GSON.toJson(msg), e);
            	}
            }
		}
		
	}

    
    
}
