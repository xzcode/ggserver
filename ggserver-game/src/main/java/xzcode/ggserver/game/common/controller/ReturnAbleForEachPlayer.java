package xzcode.ggserver.game.common.controller;

import xzcode.ggserver.game.common.player.Player;

public interface ReturnAbleForEachPlayer<P extends Player, T> {

	T each(P player);
	
}
