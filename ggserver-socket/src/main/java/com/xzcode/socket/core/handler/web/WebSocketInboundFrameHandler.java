package com.xzcode.socket.core.handler.web;

import java.nio.charset.Charset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xzcode.socket.core.channel.DefaultAttributeKeys;
import com.xzcode.socket.core.executor.SocketServerTaskExecutor;
import com.xzcode.socket.core.filter.MessageFilterManager;
import com.xzcode.socket.core.message.MessageInvokerManager;
import com.xzcode.socket.core.message.SocketRequestTask;
import com.xzcode.socket.core.message.invoker.IMessageInvoker;
import com.xzcode.socket.core.message.invoker.MethodInvoker;
import com.xzcode.socket.core.serializer.ISerializer;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;

/**
 * websocket 消息接收处理器
 * 
 * @author zai
 * 2018-12-29 14:01:13
 */
public class WebSocketInboundFrameHandler extends SimpleChannelInboundHandler<WebSocketFrame> {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketInboundFrameHandler.class);
    
    private ISerializer serializer;
    
    private SocketServerTaskExecutor executor;
    
    
    private MessageInvokerManager messageInvokerManager;
    
    private MessageFilterManager messageFilterManager;
    
    public void setMessageMethodInvokeMapper(MessageInvokerManager messageInvokerManager) {
		this.messageInvokerManager = messageInvokerManager;
	}
    
    public WebSocketInboundFrameHandler() {
	}

	public WebSocketInboundFrameHandler(ISerializer serializer, SocketServerTaskExecutor executor, MessageInvokerManager messageInvokerManager, MessageFilterManager messageFilterManager) {
		super();
		this.serializer = serializer;
		this.executor = executor;
		this.messageInvokerManager = messageInvokerManager;
		this.messageFilterManager = messageFilterManager;
	}



	@Override
    protected void channelRead0(ChannelHandlerContext ctx, WebSocketFrame frame) throws Exception {
    	if (!ctx.channel().isActive()) {
    		if(LOGGER.isDebugEnabled()){
        		LOGGER.debug("\nRead channel:{} is inActive...", ctx.channel());        		
        	}
			return;
		}
        if (frame instanceof BinaryWebSocketFrame) {
            ByteBuf content = ((BinaryWebSocketFrame) frame).content();
            
            Integer readUnsignedShort = content.readUnsignedShort();
            
            byte[] tagBytes = new byte[readUnsignedShort];
            content.readBytes(tagBytes);
            
            String tag = new String(tagBytes, Charset.forName("utf-8"));
            
            //如果没有数据体
            if (content.readableBytes() == 0) {            	
            	
            	executor.submit(new SocketRequestTask(tag, ctx.channel().attr(DefaultAttributeKeys.SESSION).get(), null ,this.messageInvokerManager,this.messageFilterManager));
            	if(LOGGER.isDebugEnabled()){
                	LOGGER.debug("\nReceived no-data-body binary message <----, \nchannel:{}\ntag:{}", ctx.channel(), tag);
                }
            	return;
			}
            
            
          //如果有数据体
            
            byte[] bytes = new byte[content.readableBytes()];
            content.readBytes(bytes);
            
            IMessageInvoker invoker = messageInvokerManager.get(tag);
            
            if (invoker == null) {
            	LOGGER.warn("\nUnsupported data!!\nchannel:{}\ntag:{}\ndata:{}", ctx.channel(), tag, new String(bytes));
            	return;
			}
            
            Object message = this.serializer.deserialize(bytes, invoker.getRequestMessageClass());
            
            //反序列化 并且 提交任务
            executor.submit(new SocketRequestTask(tag, ctx.channel().attr(DefaultAttributeKeys.SESSION).get(), message, this.messageInvokerManager,this.messageFilterManager));
            
            if(LOGGER.isDebugEnabled()){
            	LOGGER.debug("\nReceived binary message  <----,\nchannel:{}\ntag:{}\nbytes-length:{}", ctx.channel(), tag, bytes.length);
            }
            
        }
        else if (frame instanceof CloseWebSocketFrame){
        	//ctx.close();
        }
    	else if (frame instanceof TextWebSocketFrame){
        	TextWebSocketFrame text = (TextWebSocketFrame) frame;
        	//ctx.fireUserEventTriggered("hello--fireUserEventTriggered");
			
        	if(LOGGER.isDebugEnabled()){
        		LOGGER.debug("\nReceived string message:\nchannel{}\ntext:{} ; drop...", ctx.channel(), text.text());        		
        	}
        	
        }else{
        	throw new UnsupportedOperationException("\nUnsupported inbound data !");
        }
    }
	
	
    
    @Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
    	/*
    	if (evt instanceof HandshakeComplete) {
    		//HandshakeComplete handshakeComplete = (HandshakeComplete) evt;
    		//System.out.println("HandshakeComplete -------------->");
    		
    		//ctx.pipeline().addAfter("WebSocketInboundFrameHandler", "WebSocketOutboundFrameHandler",new WebSocketOutboundFrameHandler(serializer));
		}
		*/
		super.userEventTriggered(ctx, evt);
	}

	public SocketServerTaskExecutor getExecutor() {
		return executor;
	}
    
    public void setExecutor(SocketServerTaskExecutor executor) {
		this.executor = executor;
	}
    
}
