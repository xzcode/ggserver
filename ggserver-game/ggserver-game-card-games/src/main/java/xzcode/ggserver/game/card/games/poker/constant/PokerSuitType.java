package xzcode.ggserver.game.card.games.poker.constant;

/**
 * 卡片花色类型
 * 
 * @author zai
 * 2019-01-21 14:57:20
 */
public interface PokerSuitType {
	
	/**
	 * 小丑
	 */
	int JOKER = 5;	
	
	/**
	 * 黑桃
	 */
	int SPADE = 4;
	/**
	 * 红桃
	 */
	int HEART = 3;
	/**
	 * 梅花
	 */
	int CLUB = 2;
	/**
	 * 方块
	 */
	int DEMAND = 1;
	
	
	
	/**
	 * 是否黑桃
	 * 
	 * @param cardType
	 * @return
	 * @author zai
	 * 2019-01-22 11:17:03
	 */
	public static boolean isSpade(int cardValue) {
		return cardValue / 100 == PokerSuitType.SPADE;
	}
	
	/**
	 * 是否红桃
	 * 
	 * @param cardType
	 * @return
	 * @author zai
	 * 2019-01-22 11:17:09
	 */
	public static boolean isHeat(int cardValue) {
		return cardValue / 100 == PokerSuitType.HEART;
	}
	
	/**
	 * 是否梅花
	 * 
	 * @param cardType
	 * @return
	 * @author zai
	 * 2019-01-22 11:17:17
	 */
	public static boolean isClub(int cardValue) {
		return cardValue / 100 == PokerSuitType.CLUB;
	}
	
	/**
	 * 方块
	 * 
	 * @param cardType
	 * @return
	 * @author zai
	 * 2019-01-22 11:17:42
	 */
	public static boolean isDemand(int cardValue) {
		return cardValue / 100 == PokerSuitType.DEMAND;
	}
	
	/**
	 * 是否大小王
	 * 
	 * @param cardType
	 * @return
	 * @author zai
	 * 2019-01-22 11:18:02
	 */
	public static boolean isJoker(int cardValue) {
		return cardValue / 100 == PokerSuitType.JOKER;
	}

}
