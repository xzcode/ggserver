package com.xzcode.ggserver.test.netty.tcp.server.handler;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class TestInboundHandler extends ChannelInboundHandlerAdapter{

	private static final Logger logger = LoggerFactory.getLogger(TestInboundHandler.class);
	private AtomicInteger counter = new AtomicInteger();
	/*
	@Override
	public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
		super.channelRegistered(ctx);
		logger.info("\nchannelRegistered:{}", counter.incrementAndGet());
	}

	@Override
	public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
		super.channelUnregistered(ctx);
		logger.info("\nchannelUnregistered:{}", counter.incrementAndGet());
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		super.channelActive(ctx);
		logger.info("\nchannelActive:{}", counter.incrementAndGet());
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		super.channelInactive(ctx);
		logger.info("\nchannelInactive:{}", counter.incrementAndGet());
	}
*/
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		
		logger.info("\nchannelRead:{}", counter.incrementAndGet());
		if (msg instanceof ByteBuf) {
			ByteBuf in = (ByteBuf)msg;
			if (in.readableBytes() == 0) {
				return;
			}
			byte[] bytes = new byte[in.readableBytes()];
			in.readBytes(bytes);
			System.out.println(Arrays.toString(bytes));
		}
		
		super.channelRead(ctx, msg);
		
	}
/*
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		super.channelReadComplete(ctx);
		logger.info("\nchannelReadComplete:{}", counter.incrementAndGet());
	}

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		super.userEventTriggered(ctx, evt);
		logger.info("\nuserEventTriggered:{}", counter.incrementAndGet());
	}

	@Override
	public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
		super.channelWritabilityChanged(ctx);
		logger.info("\nchannelWritabilityChanged:{}", counter.incrementAndGet());
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		super.exceptionCaught(ctx, cause);
		logger.info("\nexceptionCaught:{}", counter.incrementAndGet());
	}
	*/
	
	
}
