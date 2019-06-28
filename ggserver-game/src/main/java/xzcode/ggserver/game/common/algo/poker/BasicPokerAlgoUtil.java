package xzcode.ggserver.game.common.algo.poker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import xzcode.ggserver.game.common.algo.BasicAlgoUtil;
import xzcode.ggserver.game.common.poker.PokerCard;
import xzcode.ggserver.game.common.poker.constant.PokerSuitType;

/**
 * 基本扑克工具
 * 
 * @author zai 2018-12-27 18:06:30
 */
public class BasicPokerAlgoUtil extends BasicAlgoUtil{
	
	public static final List<Integer> CARD_VAL_LIST = Arrays.asList(
			101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113,//方块1 到 K
			201, 202, 203, 204, 205, 206, 207, 208, 209, 210, 211, 212, 213,//梅花1 到 K
			301, 302, 303, 304, 305, 306, 307, 308, 309, 310, 311, 312, 313,//红桃1 到 K
			401, 402, 403, 404, 405, 406, 407, 408, 409, 410, 411, 412, 413,//黑桃1 到 K
			501, //小王
			502 //大王
		);
	
	public static final List<Integer> CARD_VAL_LIST_NO_JOKERS = getCardValListNoJokers();
	
	public static List<Integer> getCardValListNoJokers() {
		List<Integer> list = new ArrayList<>();
		list.addAll(CARD_VAL_LIST);
		list.remove((Integer)501);
		list.remove((Integer)502);
		return list;
	}
	
	/**
	 * 获取新洗好的牌数值组合
	 * 
	 * @return
	 * @author zai 2018-12-27 18:10:22
	 */
	public static List<Integer> newShuffledValList() {
		List<Integer> cards = new ArrayList<>(CARD_VAL_LIST.size());
		Collections.shuffle(cards);
		return cards;
	}
	
	/**
	 * 获取已洗好的扑克牌集合
	 * 
	 * @param withJokers 是否包含大小王
	 * @param cardNums 获取牌数量
	 * @return
	 * @author zai
	 * 2019-06-28 16:30:18
	 */
	public static List<PokerCard> newShuffledPokerCardList(boolean withJokers, int cardNums) {
		List<Integer> vals;
		if (withJokers) {
			vals = new ArrayList<>(CARD_VAL_LIST.size());
			vals.addAll(CARD_VAL_LIST);
		}else {
			vals = new ArrayList<>(CARD_VAL_LIST_NO_JOKERS.size());
			vals.addAll(CARD_VAL_LIST_NO_JOKERS);
		}
		Collections.shuffle(vals);
		
		List<PokerCard> cards = new ArrayList<>(cardNums);
		for (int i = 0; i < cardNums; i++) {
			cards.add(new PokerCard(vals.get(i)));
		}
		return cards;
	}
	
	/**
	 * 是否黑桃
	 * 
	 * @param cardType
	 * @return
	 * @author zai
	 * 2019-01-22 11:17:03
	 */
	public static boolean isSpade(int value) {
		return PokerSuitType.isSpade(value);
	}
	
	/**
	 * 是否红桃
	 * 
	 * @param cardType
	 * @return
	 * @author zai
	 * 2019-01-22 11:17:09
	 */
	public static boolean isHeat(int value) {
		return PokerSuitType.isHeat(value);
	}
	
	/**
	 * 是否梅花
	 * 
	 * @param cardType
	 * @return
	 * @author zai
	 * 2019-01-22 11:17:17
	 */
	public static boolean isClub(int value) {
		return PokerSuitType.isClub(value);
	}
	
	/**
	 * 方块
	 * 
	 * @param cardType
	 * @return
	 * @author zai
	 * 2019-01-22 11:17:42
	 */
	public static boolean isDemand(int value) {
		return PokerSuitType.isDemand(value);
	}
	
	/**
	 * 是否大小王
	 * 
	 * @param cardType
	 * @return
	 * @author zai
	 * 2019-01-22 11:18:02
	 */
	public static boolean isJoker(int value) {
		return PokerSuitType.isJoker(value);
	}
	
	/**
	 * 获取扑克花色类型值
	 * 
	 * @param value
	 * @return
	 * @author zai
	 * 2019-01-24 19:06:41
	 */
	public static int getPokerSuitTypeValue(int value) {
		return value % 100;
	}
	
	/**
	 * 获取扑克花色类型
	 * 
	 * @param value
	 * @return
	 * @author zai
	 * 2019-01-24 19:10:08
	 */
	public static int getPokerCardType(int value) {
		if (isSpade(value)) {
			return PokerSuitType.SPADE;
		}
		if (isHeat(value)) {
			return PokerSuitType.HEART;
		}
		if (isClub(value)) {
			return PokerSuitType.CLUB;
		}
		if (isDemand(value)) {
			return PokerSuitType.DEMAND;
		}
		if (isJoker(value)) {
			return PokerSuitType.JOKER;
		}
		return 0;
	}
	
	/**
	 * 
	 * 判断是否顺子(必须先进行升序排序，A = 1，无 大小王)
	 * 
	 * @param cards
	 * @return
	 * @author zai
	 * 2019-06-28 10:59:28
	 */
	public boolean isStraight(int[] cards) {
		int lastVal = -1;
		for (int i : cards) {
			int iVal = i % 100;
			if (lastVal == -1) {
				lastVal = iVal;
			} else {
				if (lastVal + 1 == iVal) {
					lastVal = iVal;
				}else {
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * 是否顺子（必须先进行升序排序，）
	 * 
	 * @param cards 牌集
	 * @param aceVal A可变值
	 * @return
	 * @author zai
	 * 2019-06-28 12:49:19
	 */
	public boolean isStraight(int[] cards, boolean aceChangeable) {
		if (!aceChangeable) {
			return isStraight(cards);
		}
		
		boolean straight = isStraight(cards);
		if (straight) {
			return true;			
		}
		if (!containsAce(cards)) {
			return straight;
		}
		
		int aceVal = 14;
		int[] nCards = new int[cards.length];
		for (int i = 0; i < cards.length; i++) {
			int iVal = cards[i] % 100;
			if (iVal == 1) {
				iVal = aceVal;
			}
			nCards[i] = iVal;
		}
		sort(nCards);
		int lastVal = -1;
		for (int i : nCards) {
			int iVal = i;
			if (lastVal == -1) {
				lastVal = iVal;
			} else {
				if (lastVal + 1 == iVal) {
					lastVal = iVal;
				}else {
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * 是否同花
	 * 
	 * @param cards
	 * @param aceActualVal
	 * @return
	 * @author zai
	 * 2019-06-28 10:13:16
	 */
	public boolean isFlush(int[] cards) {
		int flower = 0;
		for (int i : cards) {
			if (flower == 0) {
				flower = i / 100;
			}else {
				if (flower != i / 100) {
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * 是否拥有指定长度的顺子
	 * 
	 * @param cards
	 * @param flushLen
	 * @return
	 * @author zai
	 * 2019-06-28 17:24:56
	 */
	public boolean hasFlush(int[] cards, int flushLen) {
		if (cards.length < flushLen) {
			return false;
		}
		int[] dcards = distinct(cards);
		if (dcards.length < flushLen) {
			return false;
		}
		int[] flush = new int[flushLen];
		int iRange = dcards.length - flush.length;
		for (int i = 0; i <= iRange; i++) {
			for (int j = 0; j < flush.length; j++) {
				flush[j] = dcards[i + j];
			}
			if (isFlush(flush)) {
				return true;
			}
		}
		return false;
	}
	
	
	
	/**
	 * 是否葫芦
	 * 
	 * @param cards
	 * @return
	 * @author zai
	 * 2019-06-28 15:37:25
	 */
	public boolean isFullHouse(int[] cards) {
		
		return 	cards[0] % 100 == cards[2] % 100 && cards[3] % 100 == cards[4] % 100 
				|| 
				cards[0] % 100 == cards[1] % 100 && cards[2] % 100 == cards[4] % 100
				;
	}
	
	/**
	 * 判断是否包含牌值
	 * 
	 * @param cards
	 * @param value
	 * @return
	 * @author zai
	 * 2019-06-27 18:56:34
	 */
	public boolean containsValue(int[] cards, int value) {
		for (int i : cards) {
			if (i == value) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 是否包含A
	 * 
	 * @param cards
	 * @return
	 * @author zai
	 * 2019-06-28 13:49:29
	 */
	public boolean containsAce(int[] cards) {
		for (int i : cards) {
			if (i == 101 || i == 201 || i == 301 || i == 401) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 是否包含大小王
	 * 
	 * @param cards
	 * @return
	 * @author zai
	 * 2019-06-28 15:55:12
	 */
	public boolean containsJokers(int[] cards) {
		for (int i : cards) {
			if (i == 501 || i == 502) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 拥有大小王个数
	 * 
	 * @param cards
	 * @return
	 * @author zai
	 * 2019-06-28 15:55:41
	 */
	public int hasJokersNum(int[] cards) {
		int count = 0;
		for (int i : cards) {
			if (i == 501 || i == 502) {
				count++;
			}
		}
		return count;
	}

	public static void main(String[] args) {
		BasicPokerAlgoUtil util = new BasicPokerAlgoUtil();
		
		int testTimes = 100 * 10000;
		List<int[]> testList = new ArrayList<>();
		ThreadLocalRandom random = ThreadLocalRandom.current();
		
		long startTime = 0;
		long useTime = 0;
		
		for (int i = 0; i < testTimes; i++) {
			testList.add(new int[] {
					random.nextInt(101, 114),
					random.nextInt(201, 214),
					random.nextInt(301, 314),
					random.nextInt(401, 414),
					random.nextInt(501, 514),
					});			
		}
		
		int[] testArr1 = new int[] {101, 102 ,101, 101};
		
		//预热
		boolean straight = util.isStraight(new int[] {101,105,106,102,109});
		boolean straightA = util.isStraight(new int[] {101,102,103,104,105}, true);
		boolean flush = util.isFlush(new int[] {101,105,106,102,209});
		boolean containsValue = util.containsValue(new int[] {101,105,106,102,209}, 209);
		boolean hasFlush = util.hasFlush(new int[] {106,101,101,102,103}, 3);
		
		System.out.println("straight: " + straight);
		System.out.println("straightA: " + straightA);
		System.out.println("flush: " + flush);
		System.out.println("containsValue: " + containsValue);
		System.out.println("hasFlush: " + hasFlush);
		
		//计算时间
		startTime = System.currentTimeMillis();
		for (int[] is : testList) {
			util.isStraight(is);
		}
		useTime = System.currentTimeMillis() - startTime;
		
		System.out.println("isStraight : " + useTime + " ms");
		
		//计算时间
		startTime = System.currentTimeMillis();
		for (int[] is : testList) {
			util.isStraight(is, true);
		}
		useTime = System.currentTimeMillis() - startTime;
		
		System.out.println("isStraight ACE : " + useTime + " ms");
		
		//计算时间
		startTime = System.currentTimeMillis();
		for (int[] is : testList) {
			util.isFlush(is);
		}
		useTime = System.currentTimeMillis() - startTime;
		
		System.out.println("isFlush : " + useTime + " ms");
		
		
		//计算时间
		startTime = System.currentTimeMillis();
		
		for (int[] is : testList) {
			util.containsValue(is, 209);
		}
		useTime = System.currentTimeMillis() - startTime;
		
		System.out.println("containsValue : " + useTime + " ms");
		
		
		//计算时间
		startTime = System.currentTimeMillis();
		
		for (int[] is : testList) {
			util.containsAce(is);
		}
		useTime = System.currentTimeMillis() - startTime;
		
		System.out.println("containsAce : " + useTime + " ms");
		
		
		//计算时间
		startTime = System.currentTimeMillis();
		
		for (int[] is : testList) {
			util.isFullHouse(is);
		}
		useTime = System.currentTimeMillis() - startTime;
		
		System.out.println("isFullHouse : " + useTime + " ms");
		
		//计算时间
		startTime = System.currentTimeMillis();
		
		for (int[] is : testList) {
			util.hasFlush(is, 3);
		}
		useTime = System.currentTimeMillis() - startTime;
		
		System.out.println("hasFlush : " + useTime + " ms");
		
	}
	
	
}
