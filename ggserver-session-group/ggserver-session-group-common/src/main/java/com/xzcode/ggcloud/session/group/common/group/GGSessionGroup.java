package com.xzcode.ggcloud.session.group.common.group;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

import com.xzcode.ggserver.core.common.future.GGDefaultFuture;
import com.xzcode.ggserver.core.common.future.GGFailedFuture;
import com.xzcode.ggserver.core.common.future.IGGFuture;
import com.xzcode.ggserver.core.common.message.MessageData;
import com.xzcode.ggserver.core.common.message.Pack;
import com.xzcode.ggserver.core.common.message.model.IMessage;
import com.xzcode.ggserver.core.common.message.response.support.IMakePackSupport;
import com.xzcode.ggserver.core.common.session.GGSession;

/**
 * 会话组
 *
 * @author zai 2020-04-07 14:11:55
 */
public interface GGSessionGroup extends IMakePackSupport {

	/**
	 * 获取会话集合
	 *
	 * @return
	 * @author zai 2020-04-07 14:55:31
	 */
	Map<String, GGSession> getSessionMap();
	
	/**
	 * 获取会话组id
	 *
	 * @return
	 * @author zai
	 * 2020-04-07 15:28:56
	 */
	String getGroupId();

	/**
	 * 发送给所有会话
	 *
	 * @param pack
	 * @return
	 * @author zai 2020-04-07 14:45:01
	 */
	default IGGFuture sendToAll(Pack pack) {
		Map<String, GGSession> sessionMap = getSessionMap();
		GGDefaultFuture defaultFuture = new GGDefaultFuture();
		Set<Entry<String, GGSession>> entrySet = sessionMap.entrySet();
		int size = entrySet.size();
		AtomicInteger count = new AtomicInteger(0);
		for (Entry<String, GGSession> entry : sessionMap.entrySet()) {
			GGSession session = entry.getValue();
			if (session.isReady()) {
				session.send(pack).addListener(f -> {
					if (count.incrementAndGet() >= size && !defaultFuture.isDone()) {
						defaultFuture.setDone(true);
					}
				});
			}else {
				if (count.incrementAndGet() >= size && !defaultFuture.isDone()) {
					defaultFuture.setDone(true);
				}
			}
		}
		return defaultFuture;
	}

	/**
	 * 随机发送到一个会话中
	 *
	 * @param pack
	 * @return
	 * @author zai 2020-04-07 14:49:06
	 */
	@SuppressWarnings("unchecked")
	default IGGFuture sendToRandomOne(Pack pack) {
		Map<String, GGSession> sessionMap = getSessionMap();
		Set<Entry<String, GGSession>> entrySet = sessionMap.entrySet();
		int size = entrySet.size();
		if (size == 0) {
			return GGFailedFuture.DEFAULT_FAILED_FUTURE;
		}
		//获取随机会话
		GGSession session = (GGSession) ((Entry<String, GGSession>)entrySet.toArray()[ThreadLocalRandom.current().nextInt(size)]).getValue();
		if(!session.isReady()) {
			//如果随机会话未就绪，遍历并获取就绪会话进行发送
			for (Entry<String, GGSession> entry : entrySet) {
				session = entry.getValue();
				if (session.isReady()) {
					break;
				}
			}
		}
		if (session.isReady()) {
			pack.setSession(session);
			return session.send(pack);
		}
		return GGFailedFuture.DEFAULT_FAILED_FUTURE;
	}
	
	/**
	 * 随机发送到一个会话中
	 *
	 * @param message
	 * @return
	 * @author zai
	 * 2020-04-07 15:38:44
	 */
	default IGGFuture sendToRandomOne(IMessage message) {
		return sendToRandomOne(makePack(new MessageData<>(null, message.getActionId(), message)));
	}

	/**
	 * 添加会话
	 *
	 * @param session
	 * @author zai 2020-04-07 14:55:13
	 */
	public void addSession(GGSession session);

	/**
	 * 移除会话
	 *
	 * @param session
	 * @author zai 2020-04-07 14:55:20
	 */
	public void removeSession(GGSession session);
}
