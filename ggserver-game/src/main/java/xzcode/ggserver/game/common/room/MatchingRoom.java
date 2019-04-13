package xzcode.ggserver.game.common.room;

import xzcode.ggserver.game.common.player.Player;

public abstract class MatchingRoom<P extends Player<R, H>, R, H> extends Room<P, R, H>{

	protected long matchingTimeoutMillisec;
	
	public boolean checkMatchingTimeout() {
		return matchingTimeoutMillisec < System.currentTimeMillis();
	}

	public void refreshMatchingTimeout(long timeoutDelay) {
		this.matchingTimeoutMillisec = System.currentTimeMillis() + timeoutDelay;
	}

}
