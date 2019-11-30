package xzcode.ggserver.game.card.games.algo.poker.bj21;


public enum AlgoBj21CardType {
	
	EXPLODED(100, "爆牌"),
	DOT(101, "点数"),
	FIVE_DRAGONS(102, "五小龙"),
	BLACK_JACK(103, "黑杰克"),
	
	;
	
	
	private int value;
	
	private String name;
	
		
	private AlgoBj21CardType(int value, String name) {
		this.value = value;
		this.name = name;
	}



	public int getValue() {
		return value;
	}



	public void setValue(int value) {
		this.value = value;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	
	
}
