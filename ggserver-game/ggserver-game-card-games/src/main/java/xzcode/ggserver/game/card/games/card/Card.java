package xzcode.ggserver.game.card.games.card;

/**
 * 牌基类
 * 
 * @author zai 2018-12-21 15:14:38
 */
public abstract class Card {
	
	//默认牌值
	protected int value;
	
	
	

	public Card() {
	}
	
	

	public Card(int value) {
		super();
		this.value = value;
	}



	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}


}
