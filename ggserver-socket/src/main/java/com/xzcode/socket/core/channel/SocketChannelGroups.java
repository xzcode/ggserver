package com.xzcode.socket.core.channel;

import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

public class SocketChannelGroups {
	
	private static final ChannelGroup globalGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
	
	private static final ChannelGroup registeredGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
	
	public static ChannelGroup getGlobalGroup() {
		return globalGroup;
	}
	
	public static ChannelGroup getRegisteredGroup() {
		return registeredGroup;
	}
	
	public static void closeAll() {
		globalGroup.close();
		registeredGroup.close();
	}
	
	public static void clearAll() {
		globalGroup.clear();
		registeredGroup.clear();
	}

}
