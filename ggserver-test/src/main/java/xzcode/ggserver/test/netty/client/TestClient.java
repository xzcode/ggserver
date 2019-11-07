package xzcode.ggserver.test.netty.client;

import java.io.UnsupportedEncodingException;
import java.net.SocketAddress;
import java.util.concurrent.TimeUnit;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import xzcode.ggserver.test.netty.server.handler.TestInboundHandler;
import xzcode.ggserver.test.netty.server.handler.TestOutboundHandler;

public class TestClient {
	// 要请求的服务器的ip地址
    private String ip;
    // 服务器的端口
    private int port;
    
    public TestClient(String ip, int port){
        this.ip = ip;
        this.port = port;
    }
    
    // 请求端主题
    private void action() throws InterruptedException, UnsupportedEncodingException {
        
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        
        Bootstrap bs = new Bootstrap();
        
        bs.group(bossGroup)
          .channel(NioSocketChannel.class)
          .option(ChannelOption.SO_KEEPALIVE, true)
          .handler(new ChannelInitializer<SocketChannel>() {
              @Override
              protected void initChannel(SocketChannel socketChannel) throws Exception {              
                    socketChannel.pipeline().addLast(new TestInboundHandler());
                    socketChannel.pipeline().addLast(new TestOutboundHandler());
              }
         });
        
        // 客户端开启
        ChannelFuture cf = bs.connect(ip, port).sync();
        
        //String reqStr = "我是客户端请求";
        
        // 发送客户端的请求
        //cf.channel().writeAndFlush(Unpooled.copiedBuffer(reqStr.getBytes()));
        
        cf.channel();
        
        // 等待直到连接中断
        cf.channel().closeFuture().sync();      
    }
            
    public static void main(String[] args) throws UnsupportedEncodingException, InterruptedException {
        new TestClient("192.168.1.116", 9990).action();
    }
}
