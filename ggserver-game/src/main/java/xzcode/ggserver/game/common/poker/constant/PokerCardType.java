package xzcode.ggserver.game.common.poker.constant;

/**
 * 卡片花色类型
 * 
 * @author zai
 * 2019-01-21 14:57:20
 */
public enum PokerCardType {
	/**
	 * 黑桃
	 */
	SPADE(4,"黑桃"),
	/**
	 * 红桃
	 */
	HEART(3,"红桃"),
	/**
	 * 梅花
	 */
	CLUB(2,"梅花"),
	/**
	 * 方块
	 */
	DEMAND(1,"方块"),
	/**
	 * 小丑
	 */
	JOKER(5,"小丑")	
	;
	
	/**
	 * 值
	 */
	private int value;
	
	/**
	 * 名称
	 */
	private String name;
	
	private PokerCardType(int value, String name) {
		this.value = value;
		this.name = name();
	}
	
	public int getValue() {
		return value;
	}
	public String getName() {
		return name;
	}
	
	/**
	 * 是否黑桃
	 * 
	 * @param cardType
	 * @return
	 * @author zai
	 * 2019-01-22 11:17:03
	 */
	public boolean isSpade() {
		return this == PokerCardType.SPADE;
	}
	
	/**
	 * 是否红桃
	 * 
	 * @param cardType
	 * @return
	 * @author zai
	 * 2019-01-22 11:17:09
	 */
	public boolean isHeat() {
		return this == PokerCardType.HEART;
	}
	
	/**
	 * 是否梅花
	 * 
	 * @param cardType
	 * @return
	 * @author zai
	 * 2019-01-22 11:17:17
	 */
	public boolean isClub() {
		return this == PokerCardType.CLUB;
	}
	
	/**
	 * 方块
	 * 
	 * @param cardType
	 * @return
	 * @author zai
	 * 2019-01-22 11:17:42
	 */
	public boolean isDemand() {
		return this == PokerCardType.DEMAND;
	}
	
	/**
	 * 是否大小王
	 * 
	 * @param cardType
	 * @return
	 * @author zai
	 * 2019-01-22 11:18:02
	 */
	public boolean isJoker() {
		return this == PokerCardType.JOKER;
	}

}
