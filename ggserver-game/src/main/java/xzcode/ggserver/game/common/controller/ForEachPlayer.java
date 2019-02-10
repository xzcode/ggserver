package xzcode.ggserver.game.common.controller;

import xzcode.ggserver.game.common.player.Player;

public interface ForEachPlayer<P extends Player> {

	void each(P player);
	
}
