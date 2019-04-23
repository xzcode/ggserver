package xzcode.ggserver.game.common.room;

import xzcode.ggserver.game.common.player.RoomPlayer;

/**
 * 匹配房间
 * 
 * @param <P>
 * @param <R>
 * @param <H>
 * @author zai
 * 2019-04-13 14:10:16
 */
public abstract class MatchingRoom<P extends RoomPlayer<R, H>, R, H> extends Room<P, R, H>{

	protected long matchingTimeoutMillisec;
	
	protected long matchingTimeoutDelayMs;
	
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

}
