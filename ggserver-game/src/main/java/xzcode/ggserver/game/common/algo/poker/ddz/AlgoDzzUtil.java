package xzcode.ggserver.game.common.algo.poker.ddz;

import xzcode.ggserver.game.common.algo.poker.BasicPokerAlgoUtil;

/**
 * 三公算法工具类
 * 
 * @author zai
 * 2019-05-25 17:09:07
 */
public class AlgoDzzUtil extends BasicPokerAlgoUtil{
	
	
	/**
	 * 是否有炸弹
	 * 
	 * @param handcards 手牌牌值
	 * @param targetValue 目标值
	 * @return
	 * @author zai
	 * 2019-05-28 11:00:43
	 */
	public static boolean hasZhaDan(int[] handcards, int targetValue) {
		int count = 0;
		for (int i : handcards) {
			if (i == targetValue) {
				count++;
				if (count == 4) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * 是否有王炸
	 * 
	 * @param handcards 手牌
	 * @return
	 * @author zai
	 * 2019-05-28 11:04:39
	 */
	public static boolean hasWangZha(int[] handcards) {
		boolean dawang = false;
		boolean xiaowang = false;
		for (int i : handcards) {
			if (i == 501) {
				xiaowang = true;
			}
			if (i == 502) {
				dawang = true;
			}
			
			if (dawang && xiaowang) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 是否王炸
	 * 
	 * @param cards
	 * @return
	 * @author zai
	 * 2019-05-28 11:28:23
	 */
	public static boolean isWangZha(int[] cards) {
		if (cards.length != 2) {
			return false;
		}
		return cards[0] == 501 || cards[0] == 502 || cards[1] == 501 || cards[1] == 502;
	}
	
	
	
	/**
	 * 是否可出牌
	 * 
	 * @param curHandcards
	 * @param chupai
	 * @param lastPlayerChupai
	 * @return
	 * @author zai
	 * 2019-05-28 11:19:52
	 */
	public static boolean isCanChupai(int[] curHandcards, int[] chupai, int[] lastPlayerChupai) {
		
		//如果没有传入上家出牌，则可出任意牌型
		if (lastPlayerChupai == null) {
			
			//TODO 判断所出牌型是否合法
			
			
			//如果只出单张牌，可以直接出牌
			if (chupai.length == 1) {
				return true;
			}
			
			
			return false;
		}
		
		
		//判断出牌牌型与上家是否匹配
		if (lastPlayerChupai.length != chupai.length) {
			//如果不匹配，判断是否是王炸并且 手牌中是否有 王炸
			if (isWangZha(chupai) && hasWangZha(curHandcards)) {
				return true;
			}
		}
		
		//判断手牌中是否存在出牌牌型
		
		return false;
	}
	
	/**
	 * 检查牌型
	 * 
	 * @param cards
	 * @return
	 * @author zai
	 * 2019-05-28 15:03:24
	 */
	public static int checkCardType(int[] cards) {
		
		if (cards.length == 1) {
			return AlgoDzzCardType.DAN_ZHANG;
		}
		
		if (cards.length == 2) {
			//判断王炸
			if (isWangZha(cards)) {
				return AlgoDzzCardType.WANG_ZHA;
			}
			if (cards[0] == cards[1]) {
				return AlgoDzzCardType.DUI_ZI;
			}
			return AlgoDzzCardType.NONE;
		}
		
		if (cards.length == 3) {
			if (cards[0] == cards[1] && cards[0] == cards[2]) {
				return AlgoDzzCardType.SAN_ZHANG;
			}
			return AlgoDzzCardType.NONE;
		}
		
		if (cards.length == 4) {
			
			//判断炸弹
			if (
				cards[0] == cards[1] 
				&& 
				cards[0] == cards[2]
				&& 
				cards[0] == cards[3]
				) {
				return AlgoDzzCardType.ZHA_DAN;
			}
			
			//判断三带一
			if (
				cards[0] == cards[1] 
				&& 
				cards[0] == cards[2]
				&& 
				cards[0] != cards[3]
				) {
				return AlgoDzzCardType.SAN_DAI_YI;
			}
			return AlgoDzzCardType.NONE;
		}
		
		if (cards.length == 5) {
			
			//判断顺子
			if (
				cards[0] == cards[1] 
				&& 
				cards[0] == cards[2]
				&& 
				cards[0] == cards[3]
				) {
				return AlgoDzzCardType.ZHA_DAN;
			}
			
			//判断同花
			if (
				cards[0] == cards[1] 
				&& 
				cards[0] == cards[2]
				&& 
				cards[0] != cards[3]
				) {
				return AlgoDzzCardType.SAN_DAI_YI;
			}
			return AlgoDzzCardType.NONE;
		}
		
		
		return AlgoDzzCardType.NONE;
		
		
		
		
		
		
	}
	
	
}
