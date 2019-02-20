package xzcode.ggserver.game.common.holder.matching;

import xzcode.ggserver.game.common.holder.timeout.AutoFinishAbleTimeoutHolder;
import xzcode.ggserver.game.common.player.Player;
import xzcode.ggserver.game.common.room.Room;

/**
 * 匹配房间容器
 * 
 * @param <P>
 * @param <R>
 * @author zai
 * 2019-02-20 14:34:32
 */
public class MachingRoomHolder<R extends Room<P>, P extends Player> extends AutoFinishAbleTimeoutHolder{
	
	
	/**
	 * 房间对象
	 */
	private R room;
	
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

	public R getRoom() {
		return room;
	}

	public void setRoom(R room) {
		this.room = room;
	}

	

}
