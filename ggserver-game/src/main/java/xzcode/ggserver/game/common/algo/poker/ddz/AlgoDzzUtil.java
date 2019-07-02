package xzcode.ggserver.game.common.algo.poker.ddz;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ThreadLocalRandom;

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
	public boolean hasZhaDan(int[] handcards, int targetValue) {
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
	public boolean hasWangZha(int[] handcards) {
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
	public boolean isWangZha(int[] cards) {
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
	public boolean isCanChupai(int[] curHandcards, int[] chupai, int[] lastPlayerChupai) {
		
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
	public int checkCardType(int[] cards) {
		
		int[] cardvals = removeSuits(cards);
		sort(cardvals);
		int length = cards.length;
		
		if (length == 1) {
			return AlgoDzzCardType.DAN_ZHANG;
		}
		
		//判断王炸
		if (isWangZha(cards)) {
			return AlgoDzzCardType.WANG_ZHA;
		}
		
		//判断对子
		if (isDuiZi(cardvals)) {
			return AlgoDzzCardType.DUI_ZI;
		}
		
		//判断三张
		if (isSanZhang(cardvals)) {
			return AlgoDzzCardType.SAN_ZHANG;
		}
		
		//判断炸弹
		if (isZhaDan(cardvals)) {
			return AlgoDzzCardType.ZHA_DAN;
		}
		
		//判断三带一
		if (isSanDaiYi(cardvals)) {
			return AlgoDzzCardType.SAN_DAI_YI;
		}
		
		//判断三带二
		if (isSanDaiEr(cardvals)) {
			return AlgoDzzCardType.SAN_DAI_ER;
		}
		
		//判断四带二(四带二张单牌)
		if (isSiDaiErDan(cardvals)) {
			return AlgoDzzCardType.SI_DAI_ER_DAN;
		}
		
		//判断四带二(四张带两对)
		if (isSiDaiErShuang(cardvals)) {
			return AlgoDzzCardType.SI_DAI_ER_SHUANG;
		}
		
		//判断单顺子
		if (isStraightSingle(cardvals)) {
			return AlgoDzzCardType.DAN_SHUN_ZI;
		}
		
		//判断双顺子(连对)
		if (isStraightPairs(cardvals)) {
			return AlgoDzzCardType.SHUANG_SHUN_ZI;
		}
		
		//判断三顺子
		if (isStraightThreeCards(cardvals)) {
			return AlgoDzzCardType.SAN_SHUN_ZI;
		}
		
		//判断飞机(三顺带单张)
		if (isFeiJiDan(cardvals)) {
			return AlgoDzzCardType.FEI_JI_31;
		}
		
		//判断飞机(三顺带对子)
		if (isFeiJiShuang(cardvals)) {
			return AlgoDzzCardType.FEI_JI_32;
		}
		
		return AlgoDzzCardType.NONE;
	}
	
	/**
	 * 是否对子
	 * 
	 * @param cardvals
	 * @return
	 * @author zai
	 * 2019-06-29 10:45:57
	 */
	public boolean isDuiZi(int[] cardvals) {
		if (cardvals.length != 2) {
			return false;
		}
		return cardvals[0] == cardvals[1];
	}
	
	/**
	 * 是否三张
	 * 
	 * @param cardvals
	 * @return
	 * @author zai
	 * 2019-06-29 10:45:57
	 */
	public boolean isSanZhang(int[] cardvals) {
		if (cardvals.length != 3) {
			return false;
		}
		return cardvals[0] == cardvals[2];
	}
	
	/**
	 * 是否炸弹
	 * 
	 * @param cardvals
	 * @return
	 * @author zai
	 * 2019-07-02 11:17:43
	 */
	public boolean isZhaDan(int[] cardvals) {
		if (cardvals.length != 3) {
			return false;
		}
		return cardvals[0] == cardvals[3];
	}
	
	
	/**
	 * 是否三带一
	 * 
	 * @param cardvals
	 * @return
	 * @author zai
	 * 2019-06-29 10:45:57
	 */
	public boolean isSanDaiYi(int[] cardvals) {
		if (cardvals.length != 4) {
			return false;
		}
		return cardvals[0] == cardvals[2] 
				&& 
			   cardvals[0] != cardvals[3];
	}
	
	/**
	 * 是否三带二
	 * 
	 * @param cardvals
	 * @return
	 * @author zai
	 * 2019-06-29 10:45:57
	 */
	public boolean isSanDaiEr(int[] cardvals) {
		if (cardvals.length != 5) {
			return false;
		}
		return cardvals[0] == cardvals[2] 
				&& 
			   cardvals[0] != cardvals[3]
				&& 
			   cardvals[0] != cardvals[4];
	}
	
	
	/**
	 * 是否四带二（四张带两张）
	 * 
	 * @param cardvals
	 * @return
	 * @author zai
	 * 2019-06-29 10:45:57
	 */
	public boolean isSiDaiErDan(int[] cardvals) {
		if (cardvals.length != 6) {
			return false;
		}
		return 
				(cardvals[0] == cardvals[3]) 
				|| 
				(cardvals[2] == cardvals[5]);
	}
	
	/**
	 * 是否四带二（四张带两对）
	 * 
	 * @param cardvals
	 * @return
	 * @author zai
	 * 2019-06-29 10:44:11
	 */
	public boolean isSiDaiErShuang(int[] cardvals) {
		if (cardvals.length != 8) {
			return false;
		}
		return 
			(
				cardvals[0] == cardvals[3] 
				&& 
				cardvals[4] == cardvals[5]
				&& 
				cardvals[6] == cardvals[7]
			) 
			|| 
			(
				cardvals[0] == cardvals[1] 
				&& 
				cardvals[2] == cardvals[3]
				&& 
				cardvals[4] == cardvals[7]
			);
	}
	
	
	/**
	 * 是否单顺子
	 * 
	 * @param cardvals
	 * @return
	 * @author zai
	 * 2019-06-29 16:39:03
	 */
	public boolean isStraightSingle(int[] cardvals) {
		return isStraightSingle(cardvals, true);
	}
	
	/**
	 * 判断单顺子
	 * 
	 * @param cardvals
	 * @param checkLen 是否检查长度
	 * @return
	 * @author zai
	 * 2019-06-29 17:48:53
	 */
	public boolean isStraightSingle(int[] cardvals, boolean checkLen) {
		if (checkLen && cardvals.length < 5) {
			return false;
		}
		for (int i = 1; i < cardvals.length; i++) {
			if (cardvals[i] != cardvals[i - 1] + 1) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 是否连对
	 * 
	 * @param cardvals
	 * @return
	 * @author zai
	 * 2019-06-29 11:17:54
	 */
	public boolean isStraightPairs(int[] cardvals) {
		if (cardvals.length < 6) {
			return false;
		}
		for (int i = 2; i < cardvals.length - 2; i += 2) {
			if (
				cardvals[i] != cardvals[i + 1]
				||
				cardvals[i - 1] != cardvals[i - 2]
				||
				cardvals[i] != cardvals[i - 1] + 1
				) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 是否三顺
	 * 
	 * @param cardvals
	 * @return
	 * @author zai
	 * 2019-06-29 11:17:54
	 */
	public boolean isStraightThreeCards(int[] cardvals) {
		if (cardvals.length < 6) {
			return false;
		}
		int len = cardvals.length - 3;
		for (int i = 3; i < len; i+=3) {
			if (
				cardvals[i] != cardvals[i + 1]
				||
				cardvals[i - 1] != cardvals[i - 3]
				||
				cardvals[i] != cardvals[i - 1] + 1
				) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 是否飞机（三顺带一张）
	 * 
	 * @param cardvals
	 * @return
	 * @author zai
	 * 2019-06-29 11:17:54
	 */
	public boolean isFeiJiDan(int[] cardvals) {
		int len = cardvals.length;
		if (len < 7 || len % 4 != 0) {
			return false;
		}
		Map<Integer, Integer> map = new LinkedHashMap<>(len);
		for (int i : cardvals) {
			Integer count = map.get(i);
			if (count == null) {
				map.put(i, 1);			
			}else {
				map.put(i, count + 1);			
			}
		}
		int[] sanshun = new int[len / 4];
		int i = 0;
		for (Entry<Integer, Integer> entry : map.entrySet()) {
			int count = entry.getValue();
			if (count != 1 || count != 3) {
				return false;
			}
			if (count == 3) {
				sanshun[i] = entry.getKey();
				i++;
			}
		}
		return isStraightSingle(sanshun, false);
	}
	
	/**
	 * 是否飞机（三顺带对子）
	 * 
	 * @param cardvals
	 * @return
	 * @author zai
	 * 2019-06-29 11:17:54
	 */
	public boolean isFeiJiShuang(int[] cardvals) {
		int len = cardvals.length;
		if (len < 8 || len % 5 != 0) {
			return false;
		}
		Map<Integer, Integer> map = new LinkedHashMap<>(len);
		for (int i : cardvals) {
			Integer count = map.get(i);
			if (count == null) {
				map.put(i, 1);			
			}else {
				map.put(i, count + 1);			
			}
		}
		int[] sanshun = new int[len / 4];
		int i = 0;
		for (Entry<Integer, Integer> entry : map.entrySet()) {
			int count = entry.getValue();
			if (count != 2 && count != 3) {
				return false;
			}
			if (count == 3) {
				sanshun[i] = entry.getKey();
				i++;
			}
		}
		return isStraightSingle(sanshun, false);
	}
	
	
	public static void main(String[] args) {
		AlgoDzzUtil util = new AlgoDzzUtil();
		
		int testTimes = 100 * 10000;
		List<int[]> testList = new ArrayList<>();
		ThreadLocalRandom random = ThreadLocalRandom.current();
		
		long startTime = 0;
		long useTime = 0;
		
		int[] testArr1 = new int[] {319,201};
		for (int i = 0; i < testTimes; i++) {
			/*
			testList.add(new int[] {
				random.nextInt(101, 114),
				random.nextInt(201, 214),
				random.nextInt(301, 314),
				random.nextInt(401, 414),
				random.nextInt(501, 514),
				random.nextInt(501, 514),
				random.nextInt(501, 514),
				random.nextInt(501, 514),
				random.nextInt(501, 514),
				random.nextInt(501, 514),
			});		
			*/	
			testList.add(testArr1);
		}
		
		
		//预热
		int cardType = util.checkCardType(testArr1);
		
		System.out.println("checkCardType: " + cardType);
		
		//计算时间
		startTime = System.currentTimeMillis();
		for (int[] is : testList) {
			util.checkCardType(is);
		}
		useTime = System.currentTimeMillis() - startTime;
		System.out.println("checkCardType : " + useTime + " ms");
		
		
	}
	
}
