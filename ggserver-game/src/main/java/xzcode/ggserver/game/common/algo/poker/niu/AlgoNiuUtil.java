package xzcode.ggserver.game.common.algo.poker.niu;

import xzcode.ggserver.game.common.algo.poker.BasicPokerAlgoUtil;

/**
 * 21点算法工具类
 * 
 * @author zai
 * 2019-05-25 17:09:07
 */
public class AlgoNiuUtil extends BasicPokerAlgoUtil{
	
	
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
	public AlgoNiuCheckResult checkCardType(int[] cards) {
		
		
		
		return null;
	}
	
	/**
	 * 是否五小牛
	 * 
	 * @param cards
	 * @return
	 * @author zai
	 * 2019-05-27 15:23:10
	 */
	public boolean isFiveNiu(int[] cards) {
		return true;
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
