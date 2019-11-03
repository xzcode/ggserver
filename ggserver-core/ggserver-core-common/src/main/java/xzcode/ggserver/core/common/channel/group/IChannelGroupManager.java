package xzcode.ggserver.core.common.channel.group;

import java.util.concurrent.TimeUnit;

import io.netty.channel.Channel;
import xzcode.ggserver.core.common.future.IGGFuture;
import xzcode.ggserver.core.common.message.Pack;

/**
 * 通道组管理器
 * 
 * 
 * @author sai
 * 2019-11-03 20:29:40
 */
public interface IChannelGroupManager {

	/**
	 * 是否包含通道组
	 * @param channelGroupId 组id
	 * @return
	 * 
	 * @author sai
	 * 2019-11-03 20:26:23
	 */
	boolean hasChannelGroup(String channelGroupId);
	
	/**
	 * 获取通道组
	 * @param channelGroupId
	 * @return
	 * 
	 * @author sai
	 * 2019-11-03 20:39:05
	 */
	IChannelGroup getChannelGroup(String channelGroupId);
	
	/**
	 * 删除通道组
	 * @param channelGroupId
	 * @return
	 * 
	 * @author sai
	 * 2019-11-03 20:39:05
	 */
	IChannelGroup removeChannelGroup(String channelGroupId);

	/**
	 * 添加到通道组
	 * @param channelGroupId
	 * @param channel
	 * 
	 * @author sai
	 * 2019-11-03 22:21:19
	 */
	void addToChannelGroup(String channelGroupId, Channel channel);

	
	/**
	 * 从通道组中移除通道
	 * @param channelGroupId
	 * @param channel
	 * 
	 * @author sai
	 * 2019-11-03 22:21:26
	 */
	void removeFromChannelGroup(String channelGroupId, Channel channel);
	
}
