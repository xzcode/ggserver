package xzcode.ggserver.game.common.holder.matching;

/**
 * 循环房间匹配容器
 * 
 * @param <R>
 * @param <P>
 * @author zai
 * 2019-04-12 10:39:02
 */
public class LoopMatchingHolder<R>{
	
	
	/**
	 * 房间对象
	 */
	private R room;
	
	/**
	 * 是否已匹配
	 */
	private boolean matched;
	

	public boolean isMatched() {
		return matched;
	}
	
	public void setMatched(boolean matched) {
		this.matched = matched;
	}

	public R getRoom() {
		return room;
	}

	public void setRoom(R room) {
		this.room = room;
	}

	

}
