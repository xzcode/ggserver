package com.xzcode.socket.core.handler.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.xzcode.socket.core.channel.DefaultAttributeKeys;
import com.xzcode.socket.core.config.SocketServerConfig;
import com.xzcode.socket.core.executor.SocketRunnableTask;
import com.xzcode.socket.core.executor.SocketServerTaskExecutor;
import com.xzcode.socket.core.sender.SendModel;
import com.xzcode.socket.core.serializer.ISerializer;
import com.xzcode.socket.core.session.SocketSessionUtil;
import com.xzcode.socket.core.session.imp.SocketSession;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;

/**
 * websocket 消息发送处理器
 * 
 * @author zai
 * 2018-12-29 14:01:59
 */
public class WebSocketOutboundFrameHandler extends ChannelOutboundHandlerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketOutboundFrameHandler.class);
    
    private SocketServerConfig config;
    
    private static final Gson GSON = new GsonBuilder()
    		.serializeNulls()
    		.create();
    
    public WebSocketOutboundFrameHandler() {
    	
	}
    
    
    

	public WebSocketOutboundFrameHandler(SocketServerConfig config) {
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
		if (msg instanceof SendModel) {
			SendModel sendModel = (SendModel) msg;
			
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("\nSending message ---> \ntag:{}\nmessage:{}", sendModel.getSendTag(), GSON.toJson(sendModel));
			}
			
			
			byte[] tagBytes = sendModel.getSendTag().getBytes();
			
			ByteBuf out = null;
			
			//如果有消息体
			if (sendModel.getMessage() != null) {
				
				byte[] bodyBytes = config.getSerializer().serialize(sendModel.getMessage());
				
				out = ctx.alloc().buffer(2 + tagBytes.length + bodyBytes.length);
				
				out.writeShort(tagBytes.length);
				out.writeBytes(tagBytes);
				out.writeBytes(bodyBytes);
			} else {
			
				//如果没消息体
				
				out = ctx.alloc().buffer(2 + tagBytes.length);
				
				out.writeShort(tagBytes.length);
				out.writeBytes(tagBytes);
				
			}
			
			ChannelFuture channelFuture = null;
			if(channel.isWritable()){
				channelFuture =  ctx.writeAndFlush(new BinaryWebSocketFrame(out));
			}else {
				try {
					if (LOGGER.isInfoEnabled()) {
                    	LOGGER.info("Channel is not writable, change to sync mode! \nchannel:{}", channel);
                    }
					channelFuture = channel.writeAndFlush(out).sync();
                    if (LOGGER.isInfoEnabled()) {
                    	LOGGER.info("Sync message sended. \nchannel:{}\nmessage:{}", channel, GSON.toJson(msg));
                    }
                } catch (InterruptedException e) {
                	if (LOGGER.isInfoEnabled()) {
                		LOGGER.info("write and flush msg exception. msg:[{}]", GSON.toJson(msg), e);
                	}
                }
			}
			//添加回调
			if (channelFuture != null) {
				channelFuture.addListener(future -> {
					if (future.isSuccess()) {
						if (sendModel.getCallback() != null) {
							config.getTaskExecutor().submit(new SocketRunnableTask(sendModel.getCallback(), channel.attr(DefaultAttributeKeys.SESSION).get()));
	    				}
					}
				});
			}
			
			
		}else if(msg instanceof DefaultFullHttpResponse){
			
			super.write(ctx, msg, promise);
			
		}else{
			ctx.channel().writeAndFlush("Unsupported outbound data !");
			ctx.channel().close();
		}
	}

    
    
}
