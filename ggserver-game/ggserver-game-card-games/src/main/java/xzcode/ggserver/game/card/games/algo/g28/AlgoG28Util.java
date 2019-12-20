package xzcode.ggserver.game.card.games.algo.g28;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import xzcode.ggserver.game.card.games.algo.BasicAlgoUtil;

/**
 * 二八杠算法工具类
 * 
 * @author zai 2019-05-25 17:09:07
 */
public class AlgoG28Util extends BasicAlgoUtil {

	public static final int TONG_1 = 110;
	public static final int TONG_2 = 120;
	public static final int TONG_3 = 130;
	public static final int TONG_4 = 140;
	public static final int TONG_5 = 150;
	public static final int TONG_6 = 160;
	public static final int TONG_7 = 170;
	public static final int TONG_8 = 180;
	public static final int TONG_9 = 190;

	/**
	 * 白板
	 */
	public static final int BAI_BAN = 105;

	/**
	 * 所有值集合
	 */
	public static final List<Integer> ALL_VALUE_LIST = Arrays.asList(
			TONG_1, 
			TONG_2, 
			TONG_3, 
			TONG_4, 
			TONG_5, 
			TONG_6,
			TONG_7, 
			TONG_8, 
			TONG_9, 
			BAI_BAN
			);
	
	/**
	 * 牌型集合
	 */
	public static final Map<Integer, Integer> CARD_TYPES = new HashMap<>();
	static {
		
		//初始化牌型集合
		
		CARD_TYPES.put(TONG_6 * 1000 + TONG_4, AlgoG28CardType.NONE);
		CARD_TYPES.put(TONG_7 * 1000 + TONG_3, AlgoG28CardType.NONE);
		CARD_TYPES.put(TONG_9 * 1000 + TONG_1, AlgoG28CardType.NONE);
		
		CARD_TYPES.put(TONG_9 * 1000 + TONG_2, AlgoG28CardType.DOT_1);
		CARD_TYPES.put(TONG_8 * 1000 + TONG_3, AlgoG28CardType.DOT_1);
		CARD_TYPES.put(TONG_7 * 1000 + TONG_4, AlgoG28CardType.DOT_1);
		CARD_TYPES.put(TONG_6 * 1000 + TONG_5, AlgoG28CardType.DOT_1);
		
		CARD_TYPES.put(TONG_9 * 1000 + TONG_3, AlgoG28CardType.DOT_2);
		CARD_TYPES.put(TONG_8 * 1000 + TONG_4, AlgoG28CardType.DOT_2);
		CARD_TYPES.put(TONG_7 * 1000 + TONG_5, AlgoG28CardType.DOT_2);
		
		CARD_TYPES.put(TONG_9 * 1000 + TONG_4, AlgoG28CardType.DOT_3);
		CARD_TYPES.put(TONG_8 * 1000 + TONG_5, AlgoG28CardType.DOT_3);
		CARD_TYPES.put(TONG_7 * 1000 + TONG_6, AlgoG28CardType.DOT_3);
		CARD_TYPES.put(TONG_2 * 1000 + TONG_1, AlgoG28CardType.DOT_3);
		
		CARD_TYPES.put(TONG_9 * 1000 + TONG_5, AlgoG28CardType.DOT_4);
		CARD_TYPES.put(TONG_8 * 1000 + TONG_6, AlgoG28CardType.DOT_4);
		CARD_TYPES.put(TONG_3 * 1000 + TONG_1, AlgoG28CardType.DOT_4);
		
		CARD_TYPES.put(TONG_9 * 1000 + TONG_6, AlgoG28CardType.DOT_5);
		CARD_TYPES.put(TONG_8 * 1000 + TONG_7, AlgoG28CardType.DOT_5);
		CARD_TYPES.put(TONG_4 * 1000 + TONG_1, AlgoG28CardType.DOT_5);
		CARD_TYPES.put(TONG_3 * 1000 + TONG_2, AlgoG28CardType.DOT_5);
		
		CARD_TYPES.put(TONG_9 * 1000 + TONG_7, AlgoG28CardType.DOT_6);
		CARD_TYPES.put(TONG_5 * 1000 + TONG_1, AlgoG28CardType.DOT_6);
		CARD_TYPES.put(TONG_4 * 1000 + TONG_2, AlgoG28CardType.DOT_6);
		
		CARD_TYPES.put(TONG_9 * 1000 + TONG_8, AlgoG28CardType.DOT_7);
		CARD_TYPES.put(TONG_6 * 1000 + TONG_1, AlgoG28CardType.DOT_7);
		CARD_TYPES.put(TONG_5 * 1000 + TONG_2, AlgoG28CardType.DOT_7);
		CARD_TYPES.put(TONG_4 * 1000 + TONG_3, AlgoG28CardType.DOT_7);
		
		CARD_TYPES.put(TONG_7 * 1000 + TONG_1, AlgoG28CardType.DOT_8);
		CARD_TYPES.put(TONG_6 * 1000 + TONG_2, AlgoG28CardType.DOT_8);
		CARD_TYPES.put(TONG_5 * 1000 + TONG_3, AlgoG28CardType.DOT_8);
		
		CARD_TYPES.put(TONG_8 * 1000 + TONG_1, AlgoG28CardType.DOT_9);
		CARD_TYPES.put(TONG_7 * 1000 + TONG_2, AlgoG28CardType.DOT_9);
		CARD_TYPES.put(TONG_6 * 1000 + TONG_3, AlgoG28CardType.DOT_9);
		CARD_TYPES.put(TONG_5 * 1000 + TONG_4, AlgoG28CardType.DOT_9);
		
		CARD_TYPES.put(TONG_1 * 1000 + BAI_BAN, AlgoG28CardType.DOT_1_HALF);
		CARD_TYPES.put(TONG_2 * 1000 + BAI_BAN, AlgoG28CardType.DOT_2_HALF);
		CARD_TYPES.put(TONG_3 * 1000 + BAI_BAN, AlgoG28CardType.DOT_3_HALF);
		CARD_TYPES.put(TONG_4 * 1000 + BAI_BAN, AlgoG28CardType.DOT_4_HALF);
		CARD_TYPES.put(TONG_5 * 1000 + BAI_BAN, AlgoG28CardType.DOT_5_HALF);
		CARD_TYPES.put(TONG_6 * 1000 + BAI_BAN, AlgoG28CardType.DOT_6_HALF);
		CARD_TYPES.put(TONG_7 * 1000 + BAI_BAN, AlgoG28CardType.DOT_7_HALF);
		CARD_TYPES.put(TONG_8 * 1000 + BAI_BAN, AlgoG28CardType.DOT_8_HALF);
		CARD_TYPES.put(TONG_9 * 1000 + BAI_BAN, AlgoG28CardType.DOT_9_HALF);
		
		CARD_TYPES.put(TONG_8 * 1000 + TONG_2, AlgoG28CardType.GANG_28);
		
		CARD_TYPES.put(TONG_1 * 1000 + TONG_1, AlgoG28CardType.BAO_1);
		CARD_TYPES.put(TONG_2 * 1000 + TONG_2, AlgoG28CardType.BAO_2);
		CARD_TYPES.put(TONG_3 * 1000 + TONG_3, AlgoG28CardType.BAO_3);
		CARD_TYPES.put(TONG_4 * 1000 + TONG_4, AlgoG28CardType.BAO_4);
		CARD_TYPES.put(TONG_5 * 1000 + TONG_5, AlgoG28CardType.BAO_5);
		CARD_TYPES.put(TONG_6 * 1000 + TONG_6, AlgoG28CardType.BAO_6);
		CARD_TYPES.put(TONG_7 * 1000 + TONG_7, AlgoG28CardType.BAO_7);
		CARD_TYPES.put(TONG_8 * 1000 + TONG_8, AlgoG28CardType.BAO_8);
		CARD_TYPES.put(TONG_9 * 1000 + TONG_9, AlgoG28CardType.BAO_9);
		
		CARD_TYPES.put(BAI_BAN * 1000 + BAI_BAN, AlgoG28CardType.TIAN_WANG);
		
	}
	
	/**
	 * 获取牌型
	 * 
	 * @param cards
	 * @return
	 * @author zai
	 * 2019-05-27 17:36:00
	 */
	public int checkCardType(int[] cards) {
		rSort(cards);
		return CARD_TYPES.get(cards[0] * 1000 + cards[1]);
	}
	
	/**
	 * 值对比
	 * 
	 * @param cards1
	 * @param cards2
	 * @return 如果cards1 大 返回1，如果cards2大返回-1，一样的值返回0
	 * @author zai
	 * 2019-07-05 18:29:52
	 */
	public int compareValues(int[] cards1, int[] cards2) {
		int value1 = cards1[0] * 1000 + cards1[1];
		int value2 = cards2[0] * 1000 + cards2[1];
		if (value1 > value2) {
			return 1;
		}
		if (value1 < value2) {
			return -1;
		}
		return 0;
	}
	
	/**
	 * 从大到小排列
	 * 
	 * @param cards
	 * @author zai
	 * 2019-05-27 17:28:02
	 */
	public void rSort(int[] cards) {
		if (cards[0] < cards[1]) {
			int tmp = cards[0];
			cards[0] = cards[1];
			cards[1] = tmp;
		}
	}
	
	
}