package xzcode.ggserver.game.card.games.poker;

import xzcode.ggserver.game.card.games.card.Card;

/**
 * 扑克牌
 * 
 * @author zai 2018-12-21 15:14:38
 */
public class PokerCard extends Card{
	
	//游戏牌值
	protected int gameValue;
	//排序
	protected int sort;
	//花色
	protected int suit;

	public PokerCard() {
		
	}

	public PokerCard(int gameValue, int sort, int suit) {
		super();
		this.gameValue = gameValue;
		this.sort = sort;
		this.suit = suit;
	}

	

	public int getGameValue() {
		return gameValue;
	}

	public void setGameValue(int gameValue) {
		this.gameValue = gameValue;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public int getSuit() {
		return suit;
	}

	public void setSuit(int suit) {
		this.suit = suit;
	}

	

	

	
}
