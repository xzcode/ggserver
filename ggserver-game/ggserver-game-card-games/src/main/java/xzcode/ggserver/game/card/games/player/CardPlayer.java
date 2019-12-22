package xzcode.ggserver.game.card.games.player;

import java.util.ArrayList;
import java.util.List;

import xzcode.ggserver.game.card.games.house.House;
import xzcode.ggserver.game.card.games.poker.PokerCard;
import xzcode.ggserver.game.card.games.room.Room;

/**
     牌类玩家基类
 * 
 * @author zai
 * 2019-01-21 20:21:20
 */
public abstract class CardPlayer
<
C extends PokerCard, 
P extends RoomPlayer<P, R, H>,
R extends Room<P, R, H>, 
H extends House<P, R, H>
> 
extends RoomPlayer<P, R, H> 
implements ICardPlayer<C>
{
	
	/**
	 * 手牌
	 */
	protected List<C> handcards = new ArrayList<>(maxHandcarNum());
	
	public void reset() {

		this.handcards.clear();
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
