package xzcode.ggserver.game.card.games.algo.poker.sg;

import xzcode.ggserver.game.card.games.algo.poker.BasicPokerAlgoUtil;

/**
 * 三公算法工具类
 * 
 * @author zai
 * 2019-05-25 17:09:07
 */
public class AlgoSgUtil extends BasicPokerAlgoUtil{
	
	/**
	 * 获取牌型
	 * 
	 * @param cards
	 * @return
	 * @author zai
	 * 2019-05-25 16:56:20
	 */
	public  AlgoSgCheckResult checkCardType(int[] cards) {
		
		AlgoSgCheckResult result;
		int pointCardType;
		
		//是否爆玖
		if (isBaoJiu(cards)) {
			pointCardType = AlgoSgCardType.BAO_JIU;
			result = new AlgoSgCheckResult(pointCardType);
		}
		
		//是否炸弹
		else if (isBomb(cards)) {
			result = new AlgoSgCheckResult(AlgoSgCardType.BOMB);
		}
		
		//是否三公
		else if (isSanGong(cards)) {
			result = new AlgoSgCheckResult(AlgoSgCardType.SAN_GONG);
		}
		
		else {
			
			int points = getPoints(cards);
			
			pointCardType = getPointCardType(points);
			
			result = new AlgoSgCheckResult(pointCardType, points);
		}
		
		
		return result;
	}
	
	/**
	 * 获取点数牌型
	 * 
	 * @param points
	 * @return
	 * @author zai
	 * 2019-06-06 14:27:41
	 */
	public  int getPointCardType(int points) {
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
	public  boolean isBaoJiu(int[] cards) {
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
	public  boolean isBomb(int[] cards) {
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
	public  boolean isSanGong(int[] cards) {
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
	public  int getPoints(int[] cards) {
		return (cards[0] % 100  + cards[1] % 100 + cards[2] % 100) % 10 ;
	}
	
	
}
