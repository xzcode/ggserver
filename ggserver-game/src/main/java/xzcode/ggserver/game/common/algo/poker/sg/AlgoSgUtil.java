package xzcode.ggserver.game.common.algo.poker.sg;

import xzcode.ggserver.game.common.algo.poker.BasicPokerAlgoUtil;

/**
 * 三公算法工具类
 * 
 * @author zai
 * 2019-05-25 17:09:07
 */
public class AlgoSgUtil extends BasicPokerAlgoUtil{
	
	/**
	 * 爆玖结果常量
	 */
	private static final AlgoSgCheckResult BAO_JIU_CHECK_RESULT = new AlgoSgCheckResult(AlgoSgCardType.BAO_JIU);
	
	/**
	 * 炸弹结果常量
	 */
	private static final AlgoSgCheckResult BOMB_CHECK_RESULT = new AlgoSgCheckResult(AlgoSgCardType.BOMB);
	
	
	/**
	 * 三公结果常量
	 */
	private static final AlgoSgCheckResult SAN_GONG_CHECK_RESULT = new AlgoSgCheckResult(AlgoSgCardType.SAN_GONG);
	
	
	/**
	 * 获取牌型
	 * 
	 * @param cards
	 * @return
	 * @author zai
	 * 2019-05-25 16:56:20
	 */
	public static AlgoSgCheckResult checkCardType(int[] cards) {
		
		//是否爆玖
		if (isBaoJiu(cards)) {
			return BAO_JIU_CHECK_RESULT;
		}
		
		//是否炸弹
		if (isBomb(cards)) {
			return BOMB_CHECK_RESULT;
		}
		
		//是否三公
		if (isSanGong(cards)) {
			return SAN_GONG_CHECK_RESULT;
		}
		
		int points = getPoints(cards);
		
		return new AlgoSgCheckResult(getPointCardType(points), points);
	}
	
	
	public static AlgoSgCardType getPointCardType(int points) {
		switch (points) {
		case 0: return AlgoSgCardType.NONE;
		case 1: return AlgoSgCardType.DOT_1;
		case 2: return AlgoSgCardType.DOT_2;
		case 3: return AlgoSgCardType.DOT_3;
		case 4: return AlgoSgCardType.DOT_4;
		case 5: return AlgoSgCardType.DOT_5;
		case 6: return AlgoSgCardType.DOT_6;
		case 7: return AlgoSgCardType.DOT_7;
		case 8: return AlgoSgCardType.DOT_8;
		case 9: return AlgoSgCardType.DOT_9;
		default: return AlgoSgCardType.NONE;
		}
	}
	
	/**
	 * 是否爆玖
	 * 
	 * @param cards
	 * @return
	 * @author zai
	 * 2019-05-27 15:23:10
	 */
	public static boolean isBaoJiu(int[] cards) {
		return 3 == cards[0] % 100 && 3 == cards[1] % 100 && 3 == cards[2] % 100;
	}
	
	/**
	 * 是否炸弹
	 * 
	 * @param cards
	 * @return
	 * @author zai
	 * 2019-05-27 18:50:07
	 */
	public static boolean isBomb(int[] cards) {
		int card1 = cards[0] % 100;
		return card1 == cards[1] % 100 && card1 == cards[2] % 100;
	}
	
	
	/**
	 * 是否三公
	 * 
	 * @param cards
	 * @return
	 * @author zai
	 * 2019-05-27 18:53:36
	 */
	public static boolean isSanGong(int[] cards) {
		return 10 < cards[0] % 100 && 10 < cards[1] % 100  && 10 < cards[2] % 100 ;
	}
	
	/**
	 * 获取点数
	 * 
	 * @param cards
	 * @return
	 * @author zai
	 * 2019-05-27 18:56:25
	 */
	public static int getPoints(int[] cards) {
		return (cards[0] % 100  + cards[1] % 100 + cards[2] % 100) % 10 ;
	}
	
	
}
