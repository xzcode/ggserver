package xzcode.ggserver.game.common.player;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import xzcode.ggserver.game.common.card.Card;

/**
     牌类玩家基类
 * 
 * @author zai
 * 2019-01-21 20:21:20
 */
public abstract class CardPlayer<C extends Card, R, H> extends RoomPlayer<R, H> implements ICardPlayer<C>{
	
	/**
	 * 手牌
	 */
	protected List<C> handcards = new ArrayList<>(maxHandcarNum());
	
	public void reset() {

		this.handcards.clear();
		this.ready = false;
		this.inGame = false;

		if (this.idleTimeoutHolder != null) {
			this.idleTimeoutHolder.cancelTask();
		}
		if (this.autoReadyHolder != null) {
			this.autoReadyHolder.cancelTask();
		}
	}
	
	
	
	
	/**
	 * 获取手牌
	 * 
	 * @return
	 * @author zai
	 * 2019-01-22 19:46:17
	 */
	public List<C> getHandcards() {
		return handcards;
	}
	

}
