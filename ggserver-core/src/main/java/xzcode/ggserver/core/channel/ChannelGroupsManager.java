package xzcode.ggserver.core.channel;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * 通道组管理器
 * 
 * 
 * @author zai
 * 2019-02-08 18:03:16
 */
public class ChannelGroupsManager {
	
	/**
	 * 全局通道组
	 */
	private final ChannelGroup globalGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
	
	/**
	 * 已注册的通道组
	 */
	private final ChannelGroup registeredGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
	
	
	/**
	 * 从全局组中移除对应的channel
	 * @param channel
	 * @return
	 * 
	 * @author zai
	 * 2019-02-08 18:09:12
	 */
	public boolean removeFromGlobalGroup(Channel channel) {
		return globalGroup.remove(channel);
	}
	
	/**
	 * 从注册组中移除对应的channel
	 * @param channel
	 * @return
	 * 
	 * @author zai
	 * 2019-02-08 18:09:12
	 */
	public boolean removeFromRegisteredGroup(Channel channel) {
		return globalGroup.remove(channel);
	}
	
	
	/**
	 * 获取全局通道组
	 * @return
	 * 
	 * @author zai
	 * 2019-02-08 18:05:16
	 */
	public ChannelGroup getGlobalGroup() {
		return globalGroup;
	}
	
	/**
	 * 获取已注册的通道组
	 * @return
	 * 
	 * @author zai
	 * 2019-02-08 18:05:30
	 */
	public ChannelGroup getRegisteredGroup() {
		return registeredGroup;
	}
	
	/**
	 * 关闭所有通道组
	 * 
	 * 
	 * @author zai
	 * 2019-02-08 18:05:43
	 */
	public void closeAll() {
		globalGroup.close();
		registeredGroup.close();
	}
	
	/**
	 * 清除所有通道组
	 * 
	 * 
	 * @author zai
	 * 2019-02-08 18:05:52
	 */
	public void clearAll() {
		globalGroup.clear();
		registeredGroup.clear();
	}

}
