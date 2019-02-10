package xzcode.ggserver.game.common.holder.matching;

import java.util.concurrent.ScheduledFuture;

import xzcode.ggserver.game.common.holder.timeout.AutoFinishAbleTimeoutHolder;
import xzcode.ggserver.game.common.holder.timeout.TimeoutHolder;

/**
 * 匹配玩家容器
 * 
 * @author zai
 * 2019-01-24 10:51:29
 */
public class MachingPlayerHolder<P> extends AutoFinishAbleTimeoutHolder{
	
	
	/**
	 * 玩家对象
	 */
	private P player;
	
	/**
	 * 是否已匹配
	 */
	private boolean matched;
	
	/**
	 * 是否已取消
	 */
	private boolean canceled;
	
	/**
	 * 是否已匹配超时
	 */
	private boolean timeout;
	

	public P getPlayer() {
		return player;
	}
	
	public void setPlayer(P player) {
		this.player = player;
	}

	public boolean isMatched() {
		return matched;
	}
	
	public void setMatched(boolean matched) {
		this.matched = matched;
	}
	
	
	public boolean isCanceled() {
		return canceled;
	}
	public boolean isTimeout() {
		return timeout;
	}
	public void setCanceled(boolean canceled) {
		this.canceled = canceled;
	}
	public void setTimeout(boolean timeout) {
		this.timeout = timeout;
	}

	

}
