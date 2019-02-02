/*
 * Copyright 2014 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package com.xzcode.socket.core.handler.web;

import static io.netty.handler.codec.http.HttpMethod.GET;
import static io.netty.handler.codec.http.HttpResponseStatus.BAD_REQUEST;
import static io.netty.handler.codec.http.HttpResponseStatus.FORBIDDEN;
import static io.netty.handler.codec.http.HttpResponseStatus.NOT_FOUND;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

import java.nio.charset.Charset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xzcode.socket.core.channel.DefaultAttributeKeys;
import com.xzcode.socket.core.config.SocketServerConfig;
import com.xzcode.socket.core.message.SocketRequestTask;
import com.xzcode.socket.core.message.invoker.IMessageInvoker;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshakerFactory;
import io.netty.util.CharsetUtil;

/**
 * Handles handshakes and messages
 */
public class WebSocketServerHandler extends SimpleChannelInboundHandler<Object> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketServerHandler.class);

    //private static final String WEBSOCKET_PATH = "/websocket";

    private WebSocketServerHandshaker handshaker;
    
    
    private SocketServerConfig config;
    
    

    public WebSocketServerHandler(SocketServerConfig config) {
		super();
		this.config = config;
	}

	@Override
    public void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof FullHttpRequest) {
            handleHttpRequest(ctx, (FullHttpRequest) msg);
        } else if (msg instanceof WebSocketFrame) {
            handleWebSocketFrame(ctx, (WebSocketFrame) msg);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    private void handleHttpRequest(ChannelHandlerContext ctx, FullHttpRequest req) throws Exception {
        // Handle a bad request.
        if (!req.decoderResult().isSuccess()) {
            sendHttpResponse(ctx, req, new DefaultFullHttpResponse(HTTP_1_1, BAD_REQUEST));
            return;
        }

        // Allow only GET methods.
        if (req.method() != GET) {
            sendHttpResponse(ctx, req, new DefaultFullHttpResponse(HTTP_1_1, FORBIDDEN));
            return;
        }

        // Send the demo page and favicon.ico
        if ("/".equals(req.uri())) {
            ByteBuf content = WebSocketServerBenchmarkPage.getContent(getWebSocketLocation(req));
            FullHttpResponse res = new DefaultFullHttpResponse(HTTP_1_1, OK, content);

            res.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/html; charset=UTF-8");
            HttpUtil.setContentLength(res, content.readableBytes());

            sendHttpResponse(ctx, req, res);
            return;
        }
        if ("/favicon.ico".equals(req.uri())) {
            FullHttpResponse res = new DefaultFullHttpResponse(HTTP_1_1, NOT_FOUND);
            sendHttpResponse(ctx, req, res);
            return;
        }

        // Handshake
        WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory(getWebSocketLocation(req), null, true, config.getMaxDataLength());
        handshaker = wsFactory.newHandshaker(req);
        if (handshaker == null) {
            WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
        } else {
            handshaker.handshake(ctx.channel(), req);
        }
    }

    private void handleWebSocketFrame(ChannelHandlerContext ctx, WebSocketFrame frame) throws Exception {
    	
    	if (frame instanceof BinaryWebSocketFrame) {
    		ByteBuf content = ((BinaryWebSocketFrame) frame).content();
            
            Integer readUShort = content.readUnsignedShort();
            
            byte[] tagBytes = new byte[readUShort];
            content.readBytes(tagBytes);
            
            String tag = new String(tagBytes, Charset.forName("utf-8"));
            
            //如果没有数据体
            if (content.readableBytes() == 0) {            	
            	
            	config.getTaskExecutor().submit(new SocketRequestTask(tag, ctx.channel().attr(DefaultAttributeKeys.SESSION).get(), null ,config.getMessageInvokerManager(),config.getMessageFilterManager()));
            	if(LOGGER.isDebugEnabled()){
                	LOGGER.debug("\nReceived no-data-body binary message <----, \nchannel:{}\ntag:{}", ctx.channel(), tag);
                }
            	return;
			}
            
            
          //如果有数据体
            
            byte[] bytes = new byte[content.readableBytes()];
            content.readBytes(bytes);
            
            IMessageInvoker invoker = config.getMessageInvokerManager().get(tag);
            
            if (invoker == null) {
            	LOGGER.warn("\nUnsupported data!!\nchannel:{}\ntag:{}\ndata:{}", ctx.channel(), tag, new String(bytes));
            	return;
			}
            
            Object message = config.getSerializer().deserialize(bytes, invoker.getRequestMessageClass());
            
            //反序列化 并且 提交任务
            config.getTaskExecutor().submit(new SocketRequestTask(tag, ctx.channel().attr(DefaultAttributeKeys.SESSION).get(), message, config.getMessageInvokerManager(),config.getMessageFilterManager()));
            
            if(LOGGER.isDebugEnabled()){
            	LOGGER.debug("\nReceived binary message  <----,\nchannel:{}\ntag:{}\nbytes-length:{}", ctx.channel(), tag, bytes.length);
            }
    		return;
    	}
    	if (frame instanceof CloseWebSocketFrame) {
            //handshaker.close(ctx.channel(), (CloseWebSocketFrame) frame.retain());
    		ctx.close();
            return;
        }
        /*
        if (frame instanceof TextWebSocketFrame) {
        	if(LOGGER.isDebugEnabled()){
        		LOGGER.debug("\nReceived string message:\nchannel{}\ntext:{} ; Channel Close!!", ctx.channel(), ((TextWebSocketFrame) frame).text());        		
        	}
        	ctx.close();
        	return;
        }
        if (frame instanceof PingWebSocketFrame) {
            ctx.write(new PongWebSocketFrame(frame.content().retain()));
            return;
        }
        */
        
        ctx.close();
        
    }

    private static void sendHttpResponse(
            ChannelHandlerContext ctx, FullHttpRequest req, FullHttpResponse res) {
        // Generate an error page if response getStatus code is not OK (200).
        if (res.status().code() != 200) {
            ByteBuf buf = Unpooled.copiedBuffer(res.status().toString(), CharsetUtil.UTF_8);
            res.content().writeBytes(buf);
            buf.release();
            HttpUtil.setContentLength(res, res.content().readableBytes());
        }

        // Send the response and close the connection if necessary.
        ChannelFuture f = ctx.channel().writeAndFlush(res);
        if (!HttpUtil.isKeepAlive(req) || res.status().code() != 200) {
            f.addListener(ChannelFutureListener.CLOSE);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

    private String getWebSocketLocation(FullHttpRequest req) {
        String location =  req.headers().get(HttpHeaderNames.HOST) + this.config.getWebsocketPath();
        return "wss://" + location;
    }
}