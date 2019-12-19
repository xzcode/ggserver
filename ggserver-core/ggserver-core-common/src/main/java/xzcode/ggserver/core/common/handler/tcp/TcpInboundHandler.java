package xzcode.ggserver.core.common.handler.tcp;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.AttributeKey;
import xzcode.ggserver.core.common.channel.DefaultChannelAttributeKeys;
import xzcode.ggserver.core.common.config.GGConfig;
import xzcode.ggserver.core.common.constant.ProtocolTypeConstants;
import xzcode.ggserver.core.common.handler.codec.impl.DefaultDecodeHandler;

/**
 * 数据输入控制器
 * @author zai
 *
 */
public class TcpInboundHandler extends ChannelInboundHandlerAdapter{
	private static final Logger LOGGER = LoggerFactory.getLogger(DefaultDecodeHandler.class);

	/**
	 * 数据包长度标识 字节数
	 */
	public static final int PACKAGE_LEN = 4;
	
	
	private GGConfig config;
	
	


	public TcpInboundHandler(GGConfig config) {
		super();
		this.config = config;
	}




	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		
		ByteBuf in = (ByteBuf) msg;
		
		int readableBytes = in.readableBytes();
		
		//如果长度不足4位，放弃并等待下次读取
		if (readableBytes < PACKAGE_LEN) {
			return;
		}
		in.markReaderIndex();
		
		int packLen = in.readInt();
		in.resetReaderIndex();
		
		if (packLen > config.getMaxDataLength()) {
			LOGGER.error("Package length {} is over limit {} ! Channel close !", packLen, config.getMaxDataLength());
			ctx.channel().close();
			return;
		}
		
		if (readableBytes - PACKAGE_LEN < packLen) {
			return;
		}
		
		//调用解码处理器
		config.getDecodeHandler().handle(ctx, in, ProtocolTypeConstants.TCP);
		//super.channelRead(ctx, msg);
	}

}
