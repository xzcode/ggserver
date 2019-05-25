package xzcode.ggserver.game.common.poker;

import xzcode.ggserver.game.common.card.Card;
import xzcode.ggserver.game.common.poker.constant.PokerCardType;

/**
 * 扑克牌
 * 
 * @author zai 2018-12-21 15:14:38
 */
public abstract class PokerCard extends Card{

	/**
	 * 花色
	 */
	protected PokerCardType cardType;
	
	/**
	 * 花色值
	 */
	protected int cardTypeVal;


	public PokerCardType getCardType() {
		return cardType;
	}

	public void setCartType(PokerCardType cardType) {
		this.cardType = cardType;
		this.cardTypeVal = cardType.getValue();
	}
	
	public int getCardTypeVal() {
		return cardTypeVal;
	}

}
