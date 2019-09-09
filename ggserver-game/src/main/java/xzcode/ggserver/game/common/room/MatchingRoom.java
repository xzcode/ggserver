package xzcode.ggserver.game.common.room;

import xzcode.ggserver.game.common.player.CoinsRoomPlayer;

/**
 * 匹配房间
 * 
 * @param <P>
 * @param <R>
 * @param <H>
 * @author zai
 * 2019-04-13 14:10:16
 */
public abstract class MatchingRoom<P extends CoinsRoomPlayer<R, H>, R, H> extends Room<P, R, H>{
	
	/**
	 * 房间使用超时延迟
	 */
	protected long playTimeoutDelay;
	
	/**
	 * 房间超时时间
	 */
	protected long playTimeoutMs;
	
	/**
	 * 是否标记为已使用超时
	 */
	protected boolean playTimeout;
	
	/**
	 * 是否标记为即将移除房间
	 */
	protected boolean markedToRemove;

	/**
	 * 匹配超时时间
	 */
	protected long matchingTimeoutMillisec;
	
	/**
	 * 匹配超时延迟
	 */
	protected long matchingTimeoutDelayMs;
	
	
	/**
	 * 设置房间超时延迟(0为不超时)
	 * 
	 * @param playTimeoutDelay
	 * @author zai
	 * 2019-07-08 10:45:20
	 */
	public void setPlayTimeoutDelay(long playTimeoutDelay) {
		this.playTimeoutDelay = playTimeoutDelay;
		if (playTimeoutDelay > 0) {
			this.playTimeoutMs = System.currentTimeMillis() + playTimeoutDelay;
			this.setPlayTimeout(false);
		}
	}
	
	/**
	 * 刷新房间超时延迟
	 * 
	 * @param playTimeoutDelay
	 * @author zai
	 * 2019-07-08 16:10:35
	 */
	public void refreshPlayTimeoutDelay() {
		if (this.playTimeoutDelay > 0) {
			this.playTimeoutMs = System.currentTimeMillis() + playTimeoutDelay;
			this.setPlayTimeout(false);
		}
	}
	
	/**
	 * 获取游戏超时时间
	 * 
	 * @return
	 * @author zai
	 * 2019-07-08 11:08:51
	 */
	public long getPlayTimeoutMs() {
		return playTimeoutMs;
	}
	
	public boolean checkMatchingTimeout() {
		return matchingTimeoutMillisec < System.currentTimeMillis();
	}

	public void refreshMatchingTimeout() {
		this.matchingTimeoutMillisec = System.currentTimeMillis() + matchingTimeoutDelayMs;
	}
	
	public void setMatchingTimeoutDelayMs(long matchingTimeoutDelayMs) {
		this.matchingTimeoutDelayMs = matchingTimeoutDelayMs;
		refreshMatchingTimeout();
	}
	
	public long getMatchingTimeoutDelayMs() {
		return matchingTimeoutDelayMs;
	}
	
	public void setMarkedToRemove(boolean markedToRemove) {
		this.markedToRemove = markedToRemove;
	}
	
	public boolean isMarkedToRemove() {
		return markedToRemove;
	}
	
	public void setPlayTimeout(boolean playTimeout) {
		this.playTimeout = playTimeout;
	}
	
	public boolean isPlayTimeout() {
		return playTimeout;
	}

}
