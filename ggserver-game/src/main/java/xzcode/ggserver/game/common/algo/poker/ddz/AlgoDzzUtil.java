package xzcode.ggserver.game.common.algo.poker.ddz;

import java.util.Arrays;

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
	public static boolean hasBomb(int[] handcards, int targetValue) {
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
		
		//如果没有闯入上家出牌，则可出任意牌型
		if (lastPlayerChupai == null) {
			
			//TODO 判断所出牌型是否合法
			
			
			//如果只出一个牌，可以直接出
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
	
	
}
