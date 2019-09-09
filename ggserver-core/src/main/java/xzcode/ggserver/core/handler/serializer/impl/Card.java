package xzcode.ggserver.core.handler.serializer.impl;

/**
 * 扑克牌
 * 
 * @author zzz
 * 2019-09-09 11:12:05
 */
public final class Card {
	
	private int value;
	private int type;
	private CardType cardType;
	
	public Card() {
		super();
	}
	
	
	public Card(int value, int type) {
		this.value = value;
		this.type = type;
	}
	
	


	public Card(int value, int type, CardType cardType) {
		super();
		this.value = value;
		this.type = type;
		this.cardType = cardType;
	}


	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}

	public CardType getCardType() {
		return cardType;
	}

	public void setCardType(CardType cardType) {
		this.cardType = cardType;
	}
	
	
}
