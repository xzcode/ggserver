package com.xzcode.ggserver.core.common.handler.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * 异常处理器
 * 
 * 
 * @author zai
 * 2019-11-05 00:16:51
 */
public class ExceptionHandler extends ChannelHandlerAdapter{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionHandler.class);

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		if (cause instanceof java.io.IOException) {
			LOGGER.error("Exception Caught! {}", cause.getMessage());
			return;
		}
		if (cause instanceof UnsupportedOperationException) {
			LOGGER.error("UnsupportedOperationException ! ", cause);
			ctx.close();
		}
		LOGGER.error("Exception Caught! ", cause);
	}


}
