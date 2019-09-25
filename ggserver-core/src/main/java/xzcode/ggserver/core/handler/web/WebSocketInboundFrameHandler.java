package xzcode.ggserver.core.handler.web;

import static io.netty.handler.codec.http.HttpMethod.GET;
import static io.netty.handler.codec.http.HttpResponseStatus.BAD_REQUEST;
import static io.netty.handler.codec.http.HttpResponseStatus.FORBIDDEN;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
import io.netty.handler.codec.http.websocketx.WebSocketHandshakeException;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshakerFactory;
import io.netty.util.CharsetUtil;
import xzcode.ggserver.core.channel.DefaultChannelAttributeKeys;
import xzcode.ggserver.core.config.GGConfig;
import xzcode.ggserver.core.message.receive.RequestMessageTask;


public class WebSocketInboundFrameHandler extends SimpleChannelInboundHandler<Object> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketInboundFrameHandler.class);

    //private static final String WEBSOCKET_PATH = "/websocket";

    private WebSocketServerHandshaker handshaker;
    
    
    private GGConfig config;
    
    

    public WebSocketInboundFrameHandler(GGConfig config) {
		this.config = config;
	}

	@Override
    public void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof FullHttpRequest) {
        	String uri = ((FullHttpRequest) msg).uri();
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
        
    	if (!req.decoderResult().isSuccess()) {
            sendHttpResponse(ctx, req, new DefaultFullHttpResponse(HTTP_1_1, BAD_REQUEST));
            return;
        }

        if (req.method() != GET) {
            sendHttpResponse(ctx, req, new DefaultFullHttpResponse(HTTP_1_1, FORBIDDEN));
            return;
        }
        
        /*
        // Send the demo page and favicon.ico
        if ("/".equals(req.uri())) {
            ByteBuf content = WebSocketServerBenchmarkPage.getContent(getWebSocketLocation(req));
            FullHttpResponse res = new DefaultFullHttpResponse(HTTP_1_1, OK, content);

            res.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/html; charset=UTF-8");
            HttpUtil.setContentLength(res, content.readableBytes());

            sendHttpResponse(ctx, req, res);
            return;
        }
        */
        /*
        if ("/favicon.ico".equals(req.uri())) {
            FullHttpResponse res = new DefaultFullHttpResponse(HTTP_1_1, NOT_FOUND);
            sendHttpResponse(ctx, req, res);
            return;
        }
         */
        
        // Handshake
        WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory(getWebSocketLocation(req), null, true, config.getMaxDataLength());
        handshaker = wsFactory.newHandshaker(req);
        if (handshaker == null) {
            WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
        } else {
        	try {
        		handshaker.handshake(ctx.channel(), req);				
			} catch (WebSocketHandshakeException e) {
				LOGGER.error(e.getMessage());
				ctx.channel().close();
			}
        }
    }

    private void handleWebSocketFrame(ChannelHandlerContext ctx, WebSocketFrame frame) throws Exception {
    	
    	if (frame instanceof BinaryWebSocketFrame) {
    		ByteBuf content = ((BinaryWebSocketFrame) frame).content();
            
            Integer readUShort = content.readUnsignedShort();
            
            byte[] tagBytes = new byte[readUShort];
            content.readBytes(tagBytes);
            
            //String tag = new String(tagBytes, Charset.forName("utf-8"));
            
            //如果没有数据体
            if (content.readableBytes() == 0) {            	
            	
            	config.getTaskExecutor().submit(new RequestMessageTask(tagBytes, null, ctx.channel().attr(DefaultChannelAttributeKeys.SESSION).get(), config));
            	if(LOGGER.isDebugEnabled()){
                	LOGGER.debug("\nReceived no-data-body binary message <----, \nchannel:{}\ntag:{}", ctx.channel(), new String(tagBytes));
                }
            	return;
			}
            
            
          //如果有数据体
            
            byte[] bytes = new byte[content.readableBytes()];
            content.readBytes(bytes);
            
            //提交任务
            config.getTaskExecutor().submit(new RequestMessageTask(tagBytes, bytes, ctx.channel().attr(DefaultChannelAttributeKeys.SESSION).get(), config));
            
            if(LOGGER.isDebugEnabled()){
            	LOGGER.debug("\nReceived binary message  <----,\nchannel:{}\ntag:{}\nbytes-length:{}", ctx.channel(), new String(tagBytes), bytes.length);
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
        return "ws://" + location;
    }
}