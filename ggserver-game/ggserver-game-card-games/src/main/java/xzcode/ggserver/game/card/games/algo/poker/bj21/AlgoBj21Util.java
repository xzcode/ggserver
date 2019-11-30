package xzcode.ggserver.game.card.games.algo.poker.bj21;

import xzcode.ggserver.game.card.games.algo.poker.BasicPokerAlgoUtil;

/**
 * 21点算法工具类
 * 
 * @author zai
 * 2019-05-25 17:09:07
 */
public class AlgoBj21Util extends BasicPokerAlgoUtil{
	
	/**
	 * 爆牌结果常量
	 */
	private final AlgoBj21CheckResult EXPLODED_CHECK_RESULT = new AlgoBj21CheckResult(AlgoBj21CardType.EXPLODED);
	
	/**
	 * 五小龙结果常量
	 */
	private final AlgoBj21CheckResult FIVE_DRAGONS_CHECK_RESULT = new AlgoBj21CheckResult(AlgoBj21CardType.FIVE_DRAGONS);
	
	
	/**
	 * 黑杰克结果常量
	 */
	private final AlgoBj21CheckResult BLACK_JACK_CHECK_RESULT = new AlgoBj21CheckResult(AlgoBj21CardType.BLACK_JACK);
	
	/**
	 * 最大点数
	 */
	private final int MAX_POINTS = 21;
	
	/**
	 * 获取牌型
	 * 
	 * @param cards
	 * @return
	 * @author zai
	 * 2019-05-25 16:56:20
	 */
	public AlgoBj21CheckResult checkCardType(int[] cards) {
		
		//是否爆牌
		
		if (isExploded(cards)) {
			return EXPLODED_CHECK_RESULT;
		}
		
		//是否黑杰克
		
		if (isBlackJack(cards)) {
			return BLACK_JACK_CHECK_RESULT;
		}
		
		//是否五小龙
		
		if (isFiveDragons(cards)) {
			return FIVE_DRAGONS_CHECK_RESULT;
		}
		
		//获取点数
		int points = getPointsA1(cards);
		
		//如果存在A
		if (hasA(cards)) {
			//获取A为11时候点数
			int pointsA = getPointsA11(cards);
			//如果没爆牌，返回两个点数
			if (!isExploded(pointsA)) {
				return new AlgoBj21CheckResult(AlgoBj21CardType.DOT, points, pointsA);
			}
		}
		
		return new AlgoBj21CheckResult(AlgoBj21CardType.DOT, points);
	}
	
	/**
	 * 是否五小龙
	 * 
	 * @param cards
	 * @return
	 * @author zai
	 * 2019-05-27 15:23:10
	 */
	public boolean isFiveDragons(int[] cards) {
		if (cards.length < 5) {
			return false;
		}
		int points = 0;
		for (int c : cards) {
			int v = c % 100;
			if (v > 10) {
				v = 10;
			}
			points += v;
		}
		return points <= MAX_POINTS;
	}
	
	/**
	 * 是否黑杰克
	 * 
	 * @param cards
	 * @return
	 * @author zai
	 * 2019-05-27 15:04:05
	 */
	public boolean isBlackJack(int[] cards) {
		if (cards.length > 2) {
			return false;
		}
		int val1 = cards[0] % 100;
		int val2 = cards[1] % 100;
		
		return (val1 == 1 && val2 >= 10) || (val2 == 1 && val1 >= 10);
	}
	
	/**
	 * 是否爆牌
	 * 
	 * @param cards
	 * @return
	 * @author zai
	 * 2019-05-27 15:05:08
	 */
	public boolean isExploded(int[] cards) {
		int points = 0;
		for (int c : cards) {
			int v = c % 100;
			if (v > 10) {
				v = 10;
			}
			points += v;
		}
		return points > MAX_POINTS;
	}
	
	/**
	 * 是否爆牌
	 * 
	 * @param points 点数
	 * @return
	 * @author zai
	 * 2019-05-27 15:45:25
	 */
	public boolean isExploded(int points) {
		return points > MAX_POINTS;
	}
	
	
	
	/**
	 * 计算普通点数
	 * 
	 * @param cards
	 * @return
	 * @author zai
	 * 2019-05-25 18:06:46
	 */
	public int getPointsA1(int[] cards) {
		int points = 0;
		for (int c : cards) {
			int v = c % 100;
			if (v > 10) {
				v = 10;
			}
			points += v;
		}
		return points;
	}
	
	/**
	 * 计算A为11的情况(如果有多个A，只有1个A算作11)
	 * 
	 * @param cards
	 * @return
	 * @author zai
	 * 2019-05-27 15:39:33
	 */
	public int getPointsA11(int[] cards) {
		int points = 0;
		//是否已设置过A为11的标识
		boolean aAs11 = false;
		for (int c : cards) {
			int v = c % 100;
			if (v > 10) {
				v = 10;
			}
			if (!aAs11 && v == 1) {
				v = 11;
				aAs11 = true;
			}
			points += v;
		}
		return points;
	}
	/**
	 * 是否存在A牌
	 * 
	 * @param cards
	 * @return
	 * @author zai
	 * 2019-05-27 15:43:37
	 */
	public boolean hasA(int[] cards) {
		for (int c : cards) {
			if (c % 100 == 1) {
				return true;
			}
		}
		return false;
	}
	
}
