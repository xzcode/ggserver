package xzcode.ggserver.game.card.games.algo.poker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import xzcode.ggserver.game.card.games.algo.BasicAlgoUtil;
import xzcode.ggserver.game.card.games.poker.PokerCard;
import xzcode.ggserver.game.card.games.poker.constant.PokerSuitType;

/**
 * 基本扑克工具
 * 
 * @author zai 2018-12-27 18:06:30
 */
public class BasicPokerAlgoUtil extends BasicAlgoUtil{
	
	/**
	 * 扑克牌默认值
	 */
	public static final List<Integer> CARD_VAL_LIST = Arrays.asList(
			101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113,//方块1 到 K
			201, 202, 203, 204, 205, 206, 207, 208, 209, 210, 211, 212, 213,//梅花1 到 K
			301, 302, 303, 304, 305, 306, 307, 308, 309, 310, 311, 312, 313,//红桃1 到 K
			401, 402, 403, 404, 405, 406, 407, 408, 409, 410, 411, 412, 413,//黑桃1 到 K
			501, //小王
			502 //大王
		);
	
	
	/**
	 * 获取牌值集合
	 * 
	 * @return
	 * @author zai
	 * 2019-07-02 15:46:11
	 */
	public static List<Integer> getCardValList() {
		return CARD_VAL_LIST;
	}
	
	
	/**
	 * 获取新洗好的牌数值组合
	 * 
	 * @return
	 * @author zai 2018-12-27 18:10:22
	 */
	public List<Integer> newShuffledValList() {
		List<Integer> cards = new ArrayList<>(getCardValList().size());
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
	public List<PokerCard> newShuffledPokerCardList(boolean withJokers, int cardNums) {
		return newShuffledPokerCardList(withJokers, cardNums, null);
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
	public List<PokerCard> newShuffledPokerCardList(boolean withJokers, int cardNums, int[] exculde) {
		List<Integer> vals;
		List<Integer> cardValList = getCardValList();
		if (withJokers) {
			vals = new ArrayList<>(cardValList.size());
			vals.addAll(cardValList);
		}else {
			int noJokerSize = cardValList.size() - 2;
			vals = new ArrayList<>(noJokerSize);
			for (int i = 0; i < noJokerSize; i++) {
				vals.add(cardValList.get(i));
			}
		}
		Collections.shuffle(vals);
		
		List<PokerCard> cards = new ArrayList<>(cardNums);
		for (int i = 0; i < cardNums; i++) {
			int cardVal = vals.get(i);
			boolean flag = true;
			if (exculde != null) {
				for (int ex : exculde) {
					if (ex == cardVal) {
						flag = false;
						cardNums++;
						break;
					}
				}
			}
			if (flag) {
				//cards.add(new PokerCard(cardVal));				
			}
		}
		return cards;
	}
	
	/**
	 * 随机获取一张牌
	 * 
	 * @param withJokers
	 * @return
	 * @author zai
	 * 2019-07-01 14:13:33
	 */
	public PokerCard randomOneCard(boolean withJokers) {
		PokerCard pokerCard = new PokerCard();
		List<Integer> cardValList = getCardValList();
		if (withJokers) {
			pokerCard.setValue(cardValList.get(ThreadLocalRandom.current().nextInt(cardValList.size())));
		}else {
			pokerCard.setValue(cardValList.get(ThreadLocalRandom.current().nextInt(cardValList.size() - 2)));
		}
		return pokerCard;
	}
	
	/**
	 * 是否黑桃
	 * 
	 * @param cardType
	 * @return
	 * @author zai
	 * 2019-01-22 11:17:03
	 */
	public boolean isSpade(int value) {
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
	public boolean isHeat(int value) {
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
	public boolean isClub(int value) {
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
	public boolean isDemand(int value) {
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
	public boolean isJoker(int value) {
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
	public int getPokerSuitTypeValue(int value) {
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
	public int getPokerCardType(int value) {
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
	 * 排序，并把相同牌值的牌放在一起
	 * 
	 * @param cards 牌集
	 * @param orderType 排序方式，true 升序，false降序
	 * @author zai
	 * 2019-07-03 18:40:50
	 */
	public void sortSameValues(int[] cards, boolean orderType) {
		
		List<Integer> list = new ArrayList<>(cards.length);
		for (Integer card : cards) {
			list.add(card);
		}
		List<Integer> multiple = new ArrayList<>(cards.length / 2);
		for (Integer card : cards) {
			List<Integer> containValues = getContainValues(list, card);
			if (containValues != null && containValues.size() > 1) {
				multiple.addAll(containValues);
				list.removeAll(containValues);
			}
		}
		
		if (orderType) {
			multiple.sort((o1, o2) -> {
				if (orderType) {
					if (o1 % 100 == o2 % 100) {
						return o1 - o2;
					}else {
						return o1 % 100 - o2 % 100;
					}
				}else {
					if (o2 % 100 == o1 % 100) {
						return o2 - o1;
					}else {
						return o2 % 100 - o1 % 100;
					}
				}
			});
			list.sort((o1, o2) -> o1 % 100 - o2 % 100);
		}
		
		list.addAll(0, multiple);
		for (int i = 0; i < cards.length; i++) {
			cards[i] = list.get(i);
		}
	}
	
	/**
	 * 相同牌值的牌放在一起
	 * 
	 * @param cards 牌集
	 * @param orderType 排序方式，true 升序，false降序
	 * @author zai
	 * 2019-07-03 18:40:50
	 */
	public void makeSameValuesTogether(int[] cards, boolean orderType) {
		
		List<Integer> list = new ArrayList<>(cards.length);
		for (int i = 0; i < cards.length; i++) {
			list.add(cards[i]);
		}
		
		list.sort((o1, o2) -> {
			if (orderType) {
				if (o1 % 100 == o2 % 100) {
					return o1 - o2;
				}else {
					return o1 % 100 - o2 % 100;
				}
			}else {
				if (o2 % 100 == o1 % 100) {
					return o2 - o1;
				}else {
					return o2 % 100 - o1 % 100;
				}
			}
		});
		for (int i = 0; i < cards.length; i++) {
			cards[i] = list.get(i);
		}
	
	}
	
	/**
	 * 升序牌型，并把相同牌值并在一起
	 * 
	 * @param cards
	 * @author zai
	 * 2019-07-03 18:43:34
	 */
	public void sortSameValues(int[] cards) {
		sortSameValues(cards, true);
	}
	
	/**
	 * 将序牌型，并把相同牌值并在一起
	 * 
	 * @param cards
	 * @author zai
	 * 2019-07-03 18:44:02
	 */
	public void rSortSameValues(int[] cards) {
		sortSameValues(cards, true);
	}
	
	
	public List<Integer> getContainValues(List<Integer> cards, Integer containValue) {
		List<Integer> list = null;
		for (Integer card : cards) {
			if (card % 100 == containValue % 100) {
				if (list == null) {
					list = new ArrayList<>(cards.size() / 2);					
				}
				list.add(card);
			}
		}
		return list;
	}
	
	/**
	 * 去除花色(百位)
	 * 
	 * @param cards
	 * @return
	 * @author zai
	 * 2019-06-29 10:50:52
	 */
	public int[] removeSuits(int[] cards) {
		int[] ncards = new int[cards.length];
		for (int i = 0; i < cards.length; i++) {
			ncards[i] = cards[i] % 100;
		}
		return ncards;
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
		for (int i = 1; i < cards.length; i++) {
			if (cards[i] % 100 != cards[i - 1] % 100 + 1) {
				return false;
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
			if (isJoker(i)) {
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
			if (isJoker(i)) {
				count++;
			}
		}
		return count;
	}
	
	/**
	 * 转换成list
	 * 
	 * @param cards
	 * @return
	 * @author zai
	 * 2019-06-29 17:21:59
	 */
	public List<Integer> tranferToValues(int[] cards) {
		List<Integer> list = new ArrayList<>(cards.length);
		for (int i = 0; i < cards.length; i++) {
			list.add(cards[i]);
		}
		return list;
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
					random.nextInt(101, 114),
					random.nextInt(301, 314),
					random.nextInt(401, 414),
					random.nextInt(401, 414),
					random.nextInt(101, 114),
					random.nextInt(201, 214),
					random.nextInt(301, 314),
					random.nextInt(401, 414),
					random.nextInt(201, 214),
					random.nextInt(301, 314),
					});			
		}
		
		int[] testArr1 = new int[] {101, 102 ,101, 101};
		
		//预热
		boolean straight = util.isStraight(new int[] {101,102,103,104,105});
		boolean straightA = util.isStraight(new int[] {101,102,103,104,105}, true);
		boolean flush = util.isFlush(new int[] {101,105,106,102,209});
		boolean containsValue = util.containsValue(new int[] {101,105,106,102,209}, 209);
		boolean hasFlush = util.hasFlush(new int[] {106,101,101,102,103}, 3);
		boolean isFullHouse = util.isFullHouse(new int[] {102,102,106,106,106});
		int[] sortTestArr = new int[] {109,202,206,106,306,113,307,313,101,123,406};
		util.sortSameValues(sortTestArr);
		
		System.out.println("straight: " + straight);
		System.out.println("straightA: " + straightA);
		System.out.println("flush: " + flush);
		System.out.println("containsValue: " + containsValue);
		System.out.println("hasFlush: " + hasFlush);
		System.out.println("isFullHouse: " + isFullHouse);
		
		System.out.println("sortSameValues: " + sortTestArr);
		
		
		
		util.printArr(sortTestArr);
		
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
		
		//计算时间
		startTime = System.currentTimeMillis();
		for (int[] is : testList) {
			util.sortSameValues(is);
		}
		useTime = System.currentTimeMillis() - startTime;
		System.out.println("sortSameValues : " + useTime + " ms");
		
		//计算时间
		startTime = System.currentTimeMillis();
		for (int[] is : testList) {
			util.makeSameValuesTogether(is, true);
		}
		useTime = System.currentTimeMillis() - startTime;
		System.out.println("makeSameValuesTogether : " + useTime + " ms");
		
	}
	
	
}
