package xzcode.ggserver.test.netty.server.handler;

import java.net.SocketAddress;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

public class TestOutboundHandler extends ChannelOutboundHandlerAdapter{
	
	private static final Logger logger = LoggerFactory.getLogger(TestOutboundHandler.class);

	private AtomicInteger counter = new AtomicInteger();
	/*
	@Override
	public void bind(ChannelHandlerContext ctx, SocketAddress localAddress, ChannelPromise promise) throws Exception {
		super.bind(ctx, localAddress, promise);
		logger.info("\nbind:{}", counter.incrementAndGet());
		promise.addListener((e) -> {
			logger.info("\nbind done:{}", counter.incrementAndGet());
		});
	}

	@Override
	public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress, SocketAddress localAddress,
			ChannelPromise promise) throws Exception {
		super.connect(ctx, remoteAddress, localAddress, promise);
		logger.info("\nconnect:{}", counter.incrementAndGet());
		promise.addListener((e) -> {
			logger.info("\nconnect done:{}", counter.incrementAndGet());
		});
	}

	@Override
	public void disconnect(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
		super.disconnect(ctx, promise);
		logger.info("\ndisconnect:{}", counter.incrementAndGet());
		promise.addListener((e) -> {
			logger.info("\ndisconnect done:{}", counter.incrementAndGet());
		});
	}

	@Override
	public void close(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
		super.close(ctx, promise);
		logger.info("\nclose:{}", counter.incrementAndGet());
		promise.addListener((e) -> {
			logger.info("\nclose done:{}", counter.incrementAndGet());
		});
	}

	@Override
	public void deregister(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
		super.deregister(ctx, promise);
		logger.info("\nderegister:{}", counter.incrementAndGet());
		promise.addListener((e) -> {
			logger.info("\nderegister done:{}", counter.incrementAndGet());
		});
	}

	@Override
	public void read(ChannelHandlerContext ctx) throws Exception {
		super.read(ctx);
		logger.info("\nread:{}", counter.incrementAndGet());
	}

	@Override
	public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
		super.write(ctx, msg, promise);
		logger.info("\nwrite:{}", counter.incrementAndGet());
		promise.addListener((e) -> {
			logger.info("\nwrite done:{}", counter.incrementAndGet());
		});
	}

	@Override
	public void flush(ChannelHandlerContext ctx) throws Exception {
		super.flush(ctx);
		logger.info("\nflush:{}", counter.incrementAndGet());
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		super.exceptionCaught(ctx, cause);
		logger.info("\nexceptionCaught:{}", cause);
	}
*/
	
	
}
