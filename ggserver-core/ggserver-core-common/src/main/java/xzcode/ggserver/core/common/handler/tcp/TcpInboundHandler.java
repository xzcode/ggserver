package xzcode.ggserver.core.common.handler.tcp;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import xzcode.ggserver.core.common.config.GGConfig;
import xzcode.ggserver.core.common.handler.codec.impl.DefaultDecodeHandler;

/**
 * 数据输入控制器
 * @author Administrator
 *
 */
public class TcpInboundHandler extends ByteToMessageDecoder{
	private static final Logger LOGGER = LoggerFactory.getLogger(DefaultDecodeHandler.class);

	/**
	 * 数据包长度标识 字节数
	 */
	public static final int PACKAGE_LENGTH_BYTES = 4;
	
	private GGConfig config;
	
	


	public TcpInboundHandler(GGConfig config) {
		super();
		this.config = config;
	}




	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		
		int readableBytes = in.readableBytes();
		//如果长度不足4位，放弃并等待下次读取
		if (readableBytes < PACKAGE_LENGTH_BYTES) {
			return;
		}
		in.markReaderIndex();
		
		int packLen = in.readInt();
		
		if (packLen > config.getMaxDataLength()) {
			LOGGER.error("Package length {} is over limit {} ! Channel close !", packLen, config.getMaxDataLength());
			ctx.channel().close();
			return;
		}
		
		if (readableBytes - PACKAGE_LENGTH_BYTES < packLen) {
			in.resetReaderIndex();
			return;
		}
		
		//调用解码处理器
		config.getDecodeHandler().handle(ctx, in);
		
	}

}
