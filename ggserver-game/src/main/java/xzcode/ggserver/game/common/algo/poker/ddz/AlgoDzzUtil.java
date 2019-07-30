package xzcode.ggserver.game.common.algo.poker.ddz;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ThreadLocalRandom;

import xzcode.ggserver.game.common.algo.poker.BasicPokerAlgoUtil;




/**
 * 斗地主算法工具类
 * 
 * @author zai
 * 2019-05-25 17:09:07
 */
public class AlgoDzzUtil extends BasicPokerAlgoUtil{
	
	/**
	 * 小王牌值
	 */
	public static int DA_WANG = 531;
	
	/**
	 * 大王牌值
	 */
	public static int XIAO_WANG = 532;
	
	public static final List<Integer> CARD_VAL_LIST = Arrays.asList(
			103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 222,		//方块1 到 K
			203, 204, 205, 206, 207, 208, 209, 210, 211, 212, 213, 214, 222,		//梅花1 到 K
			303, 304, 305, 306, 307, 308, 309, 310, 311, 312, 313, 314, 322,		//红桃1 到 K
			403, 404, 405, 406, 407, 408, 409, 410, 411, 412, 413, 414, 422,		//黑桃1 到 K
			531, //小王
			532 //大王
		);
	
	public static List<Integer> getCardValList() {
		return CARD_VAL_LIST;
	}
	
	
	
	/**
	 * 是否A
	 * 
	 * @param value
	 * @return
	 * @author zai
	 * 2019-07-02 14:20:17
	 */
	public boolean isAce(int value) {
		return value%100==14;
		 //return value == 114 || value == 214 || value == 314 || value == 414;
	}
	
	/**
	 * 是否2
	 * 
	 * @param value
	 * @return
	 * @author zai
	 * 2019-07-02 14:20:24
	 */
	public boolean isTwo(int value) {
		return value%100==22;
		//return value == 122 || value == 222 || value == 322 || value == 422;
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
		
		//判断王炸
		if (isWangZha(cards)) {
			return AlgoDzzCardType.WANG_ZHA;
		}
		
		//是否单张
		if (isDanZhang(cards)) {
			return AlgoDzzCardType.DAN_ZHANG;
		}
		
		//判断对子
		if (isDuiZi(cards)) {
			return AlgoDzzCardType.DUI_ZI;
		}
		
		//***进行排序
		sort(cards);
		
		//判断三张
		if (isSanZhang(cards)) {
			return AlgoDzzCardType.SAN_ZHANG;
		}
		
		//判断炸弹
		if (isZhaDan(cards)) {
			return AlgoDzzCardType.ZHA_DAN;
		}
		
		//判断三带一
		if (isSanDaiYi(cards)) {
			return AlgoDzzCardType.SAN_DAI_YI;
		}
		
		//判断三带二
		if (isSanDaiEr(cards)) {
			return AlgoDzzCardType.SAN_DAI_ER;
		}
		
		//判断四带二(四带二张单牌)
		if (isSiDaiErDan(cards)) {
			return AlgoDzzCardType.SI_DAI_ER_DAN;
		}
		
		//判断四带二(四张带两对)
		if (isSiDaiErShuang(cards)) {
			return AlgoDzzCardType.SI_DAI_ER_SHUANG;
		}
		
		//判断单顺子
		if (isStraightSingle(cards)) {
			return AlgoDzzCardType.DAN_SHUN_ZI;
		}
		
		//判断双顺子(连对)
		if (isStraightPairs(cards)) {
			return AlgoDzzCardType.SHUANG_SHUN_ZI;
		}
		
		//判断三顺子
		if (isStraightThreeCards(cards)) {
			return AlgoDzzCardType.SAN_SHUN_ZI;
		}
		
		//判断飞机(三顺带单张)
		if (isFeiJiDan(cards)) {
			return AlgoDzzCardType.FEI_JI_31;
		}
		
		//判断飞机(三顺带对子)
		if (isFeiJiShuang(cards)) {
			return AlgoDzzCardType.FEI_JI_32;
		}
		
		return AlgoDzzCardType.NONE;
	}
	
	/**
	 * 获取跟牌选项
	 * 
	 * @param cards
	 * @return
	 * @author zai
	 * 2019-07-02 16:16:45
	 */
	public List<int[]> checkFollowOptions(int[] cards, int[] followCards) {
		
		//判断王炸
		if (isWangZha(followCards)) {
			return null;
		}
		
		List<int[]> opts = null;
		
		//判断单张
		if (isDanZhang(followCards)) {
			opts = checkFollowDanZhang(followCards, followCards[0]);
		}
		
		//判断对子
		if (isDuiZi(followCards)) {
			opts = checkFollowDuiZi(cards, followCards);
		}
		
		//判断三张
		if (isSanZhang(followCards)) {
			opts = checkFollowDuiZi(cards, followCards);
		}
		
		//判断炸弹
		if (isZhaDan(followCards)) {
			opts = checkFollowZhaDan(cards, followCards);
		}
		
		//判断三带一
		if (isSanDaiYi(followCards)) {
			opts = checkFollowSanDaiYi(cards, followCards);
		}
		//判断三带二
		if (isSanDaiEr(followCards)) {
			opts = checkFollowSanDaiYi(cards, followCards);
		}
		
		
		//判断四带二(四带二张单牌)
		if (isSiDaiErDan(followCards)) {
			opts = checkSiDaiErDan(cards, followCards);
		}
		
		
		/*
		
		//判断四带二(四张带两对)
		if (isSiDaiErShuang(followCards)) {
			opts =  AlgoDzzCardType.SI_DAI_ER_SHUANG;
		}
		
		//判断单顺子
		if (isStraightSingle(followCards)) {
			opts =  AlgoDzzCardType.DAN_SHUN_ZI;
		}
		
		//判断双顺子(连对)
		if (isStraightPairs(followCards)) {
			opts =  AlgoDzzCardType.SHUANG_SHUN_ZI;
		}
		
		//判断三顺子
		if (isStraightThreeCards(followCards)) {
			opts =  AlgoDzzCardType.SAN_SHUN_ZI;
		}
		
		//判断飞机(三顺带单张)
		if (isFeiJiDan(followCards)) {
			opts =  AlgoDzzCardType.FEI_JI_31;
		}
		
		//判断飞机(三顺带对子)
		if (isFeiJiShuang(followCards)) {
			opts =  AlgoDzzCardType.FEI_JI_32;
		}
		*/
		return null;
	}
	
	
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
			if (i == XIAO_WANG) {
				xiaowang = true;
			}
			if (i == DA_WANG) {
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
		//if (cards.length != 2) {
		//	return false;
		//}
		return cards.length != 2?false: cards[0]>=XIAO_WANG&&cards[1]>=XIAO_WANG;
		//return cards[0] == XIAO_WANG || cards[0] == DA_WANG || cards[1] == XIAO_WANG || cards[1] == DA_WANG;
	}
	
	/**
	 * 是否单张
	 * 
	 * @param cardvals
	 * @return
	 * @author zai
	 * 2019-07-02 16:23:38
	 */
	public boolean isDanZhang(int[] cardvals) {
		return cardvals.length == 1;
	}
	
	/**
	 * 获取单张跟牌选项
	 * 
	 * @param cards 手牌牌值集合
	 * @param followCardVal 跟牌牌值集合
	 * @return
	 * @author zai
	 * 2019-07-02 16:26:48
	 */
	public List<int[]> checkFollowDanZhang(int[] cards, int followCardVal) {
		int[] dcards = distinct(cards);
		List<int[]> list = null;
		int followCalcVal = followCardVal % 100;
		for (int i = 0; i < dcards.length; i++) {
			if (dcards[i] % 100 > followCalcVal) {
				if (list == null) {
					list = new ArrayList<>(5);
				}
				list.add(new int[] {cards[i]});
			}
		}
		return list;
	}
	
	
	/**
	 * 是否对子
	 * 
	 * @param cardvals
	 * @return
	 * @author zai
	 * 2019-06-29 10:45:57
	 */
	public boolean isDuiZi(int[] cards) {
		if (cards.length != 2) {
			return false;
		}
		//获取去除花色的值
		int[] cardvals = removeSuits(cards);
		return cardvals[0] == cardvals[1];
	}
	
	/**
	 * 获取对子跟牌
	 * 
	 * @param cards
	 * @param followCardVal
	 * @return
	 * @author zai
	 * 2019-07-02 16:41:54
	 */
	public List<int[]> checkFollowDuiZi(int[] cards, int[] followCards) {
		int[] cardvals = removeSuits(cards);
		sort(cardvals);
		int followVal = followCards[0] % 100;
		List<int[]> list = null;
		for (int i = 0; i < cardvals.length; i++) {
			if (cardvals[i] > followVal) {
				if (cardvals[i] == cardvals[i + 1]) {
					if (list == null) {
						list = new ArrayList<>(5);
					}
					list.add(new int[] {cards[i], cards[i + 1]});
					i++;
				}
			}
		}
		return list;
	}
	
	
	/**
	 * 是否三张
	 * 
	 * @param cardvals
	 * @return
	 * @author zai
	 * 2019-06-29 10:45:57
	 */
	public boolean isSanZhang(int[] cards) {
		if (cards.length != 3) {
			return false;
		}
		//获取去除花色的值
		int[] cardvals = removeSuits(cards);
		return cardvals[0] == cardvals[2];
	}
	
	/**
	 * 获取三张跟牌
	 * 
	 * @param cards
	 * @param followCards
	 * @return
	 * @author zai
	 * 2019-07-02 17:58:40
	 */
	public List<int[]> checkFollowSanZhang(int[] cards, int[] followCards) {
		List<int[]> list = null;
		if (cards.length < followCards.length) {
			return list;
		}
		int[] cardvals = removeSuits(cards);
		sort(cardvals);
		int followVal = followCards[0] % 100;
		for (int i = 0; i < cardvals.length; i++) {
			if (cardvals[i] > followVal) {
				if (cardvals[i] == cardvals[i + 2]) {
					if (list == null) {
						list = new ArrayList<>(6);
					}
					list.add(new int[] {cards[i], cards[i + 2]});
					i+=2;;
				}
			}
		}
		return list;
	}
	
	/**
	 * 是否炸弹
	 * 
	 * @param cardvals
	 * @return
	 * @author zai
	 * 2019-07-02 11:17:43
	 */
	public boolean isZhaDan(int[] cards) {
		if (cards.length != 4) {
			return false;
		}
		//获取去除花色的值
		int[] cardvals = removeSuits(cards);
		sort(cardvals);
		return cardvals[0] == cardvals[3];
	}
	
	/**
	 * 获取炸弹跟牌
	 * 
	 * @param cards
	 * @param followCards
	 * @return
	 * @author zai
	 * 2019-07-02 17:58:40
	 */
	public List<int[]> checkFollowZhaDan(int[] cards, int[] followCards) {
		List<int[]> list = null;
		if (cards.length < followCards.length) {
			return list;
		}
		int[] cardvals = removeSuits(cards);
		sort(cardvals);
		int followVal = followCards[0] % 100;
		for (int i = 0; i < cardvals.length; i++) {
			if (cardvals[i] > followVal) {
				if (cardvals[i] == cardvals[i + 3]) {
					if (list == null) {
						list = new ArrayList<>(6);
					}
					list.add(new int[] {cards[i], cards[i + 3]});
					i+=3;
				}
			}
		}
		return list;
	}
	
	
	/**
	 * 是否三带一
	 * 
	 * @param cardvals
	 * @return
	 * @author zai
	 * 2019-06-29 10:45:57
	 */
	public boolean isSanDaiYi(int[] cards) {
		if (cards.length != 4) {
			return false;
		}
		//获取去除花色的值
		int[] cardvals = removeSuits(cards);
		sort(cardvals);
		return cardvals[0] == cardvals[2] 
				&& 
			   cardvals[0] != cardvals[3];
	}
	
	/**
	 * 获取三带一跟牌
	 * 
	 * @param cards
	 * @param followCards
	 * @return
	 * @author zai
	 * 2019-07-02 17:58:40
	 */
	public List<int[]> checkFollowSanDaiYi(int[] cards, int[] followCards) {
		List<int[]> list = null;
		if (cards.length < followCards.length) {
			return list;
		}
		//获取跟牌三张
		List<Integer> followShunZiOpts = getShunZiOptions(followCards, 3);
		
		//获取手牌三张集合
		List<Integer> shunZiOptions = getShunZiOptions(cards, 3);
		
		if (shunZiOptions != null) {
			
			List<Integer> opts = null;
			
			//对比牌值大小
			for (Integer opt : shunZiOptions) {
				for (Integer fopt : followShunZiOpts) {
					if (opt > fopt) {
						if (opts == null) {
							opts = new ArrayList<>(2);
						}
						opts.add(opt);
					}
				}
			}
			if (opts.size() > 0) {
				for (Integer opt : opts) {
					for (int i = 0; i < cards.length; i++) {
						int card = cards[i];
						if (card % 100 != opt) {
							if (list == null) {
								list = new ArrayList<>(3);
							}
							int[] arr = new int[4];
							int k = 0;
							for (int j = 0; j < 3; j++) {
								for (; k < cards.length; k++) {
									if (cards[k] % 100 == opt) {
										arr[j] = cards[k];
										k++;
										break;
									}
								}
							}
							arr[3] = card;
							list.add(arr);
						}
					}
				}
			}
		
		}
		
		return list;
	}
	
	public List<Integer> getShunZiOptions(int[] cards, int shunziSize) {
		List<Integer> list = null;
		int[] ncards = removeSuits(cards);
		sort(ncards);
		for (int i = 0; i <= ncards.length - shunziSize ; i++) {
			if (ncards[i] == ncards[i + shunziSize - 1]) {
				if (list == null) {
					list = new ArrayList<>(3);
				}
				list.add(ncards[i]);
			}
		}
		return list;
	}
	
	/**
	 * 是否三带二
	 * 
	 * @param cardvals
	 * @return
	 * @author zai
	 * 2019-06-29 10:45:57
	 */
	public boolean isSanDaiEr(int[] cards) {
		if (cards.length != 5) {
			return false;
		}
		//获取去除花色的值
		int[] cardvals = removeSuits(cards);
		sort(cardvals);
		return cardvals[0] == cardvals[2] 
				&& 
			   cardvals[0] != cardvals[3]
				&& 
			   cardvals[0] != cardvals[4];
	}
	
	
	/**
	 * 获取三带二跟牌
	 * 
	 * @param cards
	 * @param followCards
	 * @return
	 * @author zai
	 * 2019-07-02 17:58:40
	 */
	public List<int[]> checkFollowSanDaiEr(int[] cards, int[] followCards) {
		List<int[]> list = null;
		if (cards.length < followCards.length) {
			return list;
		}
		//获取跟牌三张
		List<Integer> followShunZiOpts = getShunZiOptions(followCards, 3);
		//获取手牌三张集合
		List<Integer> shunZiOptions = getShunZiOptions(cards, 3);
		
		if (shunZiOptions != null) {
			
			List<Integer> opts = null;
			
			//对比牌值大小
			for (Integer opt : shunZiOptions) {
				for (Integer fopt : followShunZiOpts) {
					if (opt > fopt) {
						if (opts == null) {
							opts = new ArrayList<>(2);
						}
						opts.add(opt);
					}
				}
			}
			if (opts.size() > 0) {
				//让相同点算的牌放在一起
				makeSameValuesTogether(cards, true);
				for (Integer opt : opts) {
					for (int i = 0; i < cards.length - 1; i++) {
						int card = cards[i];
						if (card % 100 != opt) {
							if (cards[i] % 100 == cards[i + 1] % 100) {
								
								if (list == null) {
									list = new ArrayList<>(3);
								}
								int[] arr = new int[5];
								int k = 0;
								for (int j = 0; j < 3; j++) {
									for (; k < cards.length; k++) {
										if (cards[k] % 100 == opt) {
											arr[j] = cards[k];
											k++;
											break;
										}
									}
								}
								arr[3] = cards[i];
								arr[4] = cards[i + 1];
								list.add(arr);
								//i++;  //解除注释可开启对子同牌值的多个选项
							}
						}
					}
				}
			}
		}
		
		return list;
	}
	
	
	/**
	 * 是否四带二（四张带两张）
	 * 
	 * @param cardvals
	 * @return
	 * @author zai
	 * 2019-06-29 10:45:57
	 */
	public boolean isSiDaiErDan(int[] cards) {
		if (cards.length != 6) {
			return false;
		}
		//获取去除花色的值
		int[] cardvals = removeSuits(cards);
		sort(cardvals);
		return 
				(cardvals[0] == cardvals[3]) 
				|| 
				(cardvals[2] == cardvals[5]);
	}
	
	/**
	 * 获取四带二跟牌（四张带两张）
	 * 
	 * @param cards
	 * @param followCards
	 * @return
	 * @author zai
	 * 2019-07-02 17:58:40
	 */
	public List<int[]> checkSiDaiErDan(int[] cards, int[] followCards) {
		List<int[]> list = null;
		if (cards.length < followCards.length) {
			return list;
		}
		//获取跟牌三张
		List<Integer> followShunZiOpts = getShunZiOptions(followCards, 4);
		//获取手牌三张集合
		List<Integer> shunZiOptions = getShunZiOptions(cards, 4);
		
		if (shunZiOptions != null) {
			
			List<Integer> opts = null;
			
			//对比牌值大小
			for (Integer opt : shunZiOptions) {
				for (Integer fopt : followShunZiOpts) {
					if (opt > fopt) {
						if (opts == null) {
							opts = new ArrayList<>(2);
						}
						opts.add(opt);
					}
				}
			}
			if (opts.size() > 0) {
				
				//让相同点算的牌放在一起
				makeSameValuesTogether(cards, true);
				
				for (Integer opt : opts) {
					for (int i = 0; i < cards.length - 1; i++) {
						if (cards[i] % 100 != opt && cards[i + 1] % 100 != opt) {
							//if (cards[i] % 100 == cards[i + 1] % 100) {
								
								if (list == null) {
									list = new ArrayList<>(3);
								}
								int[] arr = new int[6];
								int k = 0;
								for (int j = 0; j < 4; j++) {
									for (; k < cards.length; k++) {
										if (cards[k] % 100 == opt) {
											arr[j] = cards[k];
											k++;
											break;
										}
									}
								}
								arr[4] = cards[i];
								arr[5] = cards[i + 1];
								list.add(arr);
								//i++;  //解除注释可开启对子同牌值的多个选项
							}
						//}
					}
				}
			}
		}
		
		return list;
	}
	
	/**
	 * 是否四带二（四张带两对）
	 * 
	 * @param cardvals
	 * @return
	 * @author zai
	 * 2019-06-29 10:44:11
	 */
	public boolean isSiDaiErShuang(int[] cards) {
		if (cards.length != 8) {
			return false;
		}
		//获取去除花色的值
		int[] cardvals = removeSuits(cards);
		sort(cardvals);
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
	 * 获取四带二跟牌（四张带两对）
	 * 
	 * @param cards
	 * @param followCards
	 * @return
	 * @author zai
	 * 2019-07-02 17:58:40
	 */
	public List<int[]> checkSiDaiErShuang(int[] cards, int[] followCards) {
		List<int[]> list = null;
		if (cards.length < followCards.length) {
			return list;
		}
		//获取跟牌三张
		List<Integer> followShunZiOpts = getShunZiOptions(followCards, 4);
		//获取手牌三张集合
		List<Integer> shunZiOptions = getShunZiOptions(cards, 4);
		
		if (shunZiOptions != null) {
			
			List<Integer> opts = null;
			
			//对比牌值大小
			for (Integer opt : shunZiOptions) {
				for (Integer fopt : followShunZiOpts) {
					if (opt > fopt) {
						if (opts == null) {
							opts = new ArrayList<>(2);
						}
						opts.add(opt);
					}
				}
			}
			if (opts.size() > 0) {
				
				//让相同点算的牌放在一起
				makeSameValuesTogether(cards, true);
				
				for (Integer opt : opts) {
					for (int i = 0; i < cards.length - 1; i++) {
						if (cards[i] % 100 != opt) {
							if (cards[i] % 100 == cards[i + 1] % 100) {
								
								if (list == null) {
									list = new ArrayList<>(3);
								}
								int[] arr = new int[6];
								int k = 0;
								for (int j = 0; j < 4; j++) {
									for (; k < cards.length; k++) {
										if (cards[k] % 100 == opt) {
											arr[j] = cards[k];
											k++;
											break;
										}
									}
								}
								arr[3] = cards[i];
								arr[4] = cards[i + 1];
								list.add(arr);
								//i++;  //解除注释可开启对子同牌值的多个选项
							}
						}
					}
				}
			}
		}
		
		return list;
	}
	
	
	
	/**
	 * 是否单顺子
	 * 
	 * @param cardvals
	 * @return
	 * @author zai
	 * 2019-06-29 16:39:03
	 */
	public boolean isStraightSingle(int[] cards) {
		return isStraightSingle(cards, true);
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
	public boolean isStraightSingle(int[] cards, boolean checkLen) {
		if (checkLen && cards.length < 5) {
			return false;
		}
		//获取去除花色的值
		int[] cardvals = removeSuits(cards);
		sort(cardvals);
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
	public boolean isStraightPairs(int[] cards) {
		if (cards.length < 6) {
			return false;
		}
		//获取去除花色的值
		int[] cardvals = removeSuits(cards);
		sort(cardvals);
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
	public boolean isStraightThreeCards(int[] cards) {
		if (cards.length < 6) {
			return false;
		}
		//获取去除花色的值
		int[] cardvals = removeSuits(cards);
		sort(cardvals);
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
	public boolean isFeiJiDan(int[] cards) {
		//获取去除花色的值
		int[] cardvals = removeSuits(cards);
		sort(cardvals);
		int len = cardvals.length;
		if (len < 7 || len % 4 != 0) {
			return false;
		}
		Map<Integer, Integer> map = new LinkedHashMap<>(len);
		for (int i : cardvals) 	{
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
	public boolean isFeiJiShuang(int[] cards) {
		//获取去除花色的值
		int[] cardvals = removeSuits(cards);
		sort(cardvals);
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
		
		int[] cards = new int[] { 114, 105, 205, 305, 108, 208, 306, 214, 101, 206,405, 303, 314, 313, 412, 413, 414 };
		int[] followCards = new int[] {103, 203, 303, 403, 405, 305};
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
			
			testList.add(cards);
		}
		
		
		//预热
		int cardType = util.checkCardType(cards);
		List<int[]> checkFollowSanDaiYi = util.checkFollowSanDaiYi(cards,followCards);
		List<int[]> checkFollowSanDaiEr = util.checkFollowSanDaiEr(cards,followCards);
		List<int[]> checkSiDaiErDan = util.checkSiDaiErDan(cards,followCards);
		List<int[]> checkSiDaiErShuang = util.checkSiDaiErShuang(cards,followCards);
		
		System.out.println("checkCardType: " + cardType);
		System.out.println("checkFollowSanDaiYi: ");
		for (int[] is : checkFollowSanDaiYi) {
			util.printArr(is);
		}
		System.out.println("checkFollowSanDaiEr: ");
		for (int[] is : checkFollowSanDaiEr) {
			util.printArr(is);
		}
		System.out.println("checkSiDaiErDan: ");
		if (checkSiDaiErDan != null) {
			for (int[] is : checkSiDaiErDan) {
				util.printArr(is);
			}
		}
		
		System.out.println("checkSiDaiErShuang: ");		
		if (checkSiDaiErShuang != null) {
			for (int[] is : checkSiDaiErShuang) {
				util.printArr(is);
			}
		}
		
		//计算时间
		startTime = System.currentTimeMillis();
		for (int[] is : testList) {
			util.checkCardType(is);
		}
		useTime = System.currentTimeMillis() - startTime;
		System.out.println("checkCardType : " + useTime + " ms");
		
		//计算时间
		startTime = System.currentTimeMillis();
		for (int[] is : testList) {
			util.checkFollowSanDaiYi(cards,followCards);
		}
		useTime = System.currentTimeMillis() - startTime;
		System.out.println("checkFollowSanDaiYi : " + useTime + " ms");
		
		
		//计算时间
		startTime = System.currentTimeMillis();
		for (int[] is : testList) {
			util.checkFollowSanDaiEr(cards, followCards);
		}
		useTime = System.currentTimeMillis() - startTime;
		System.out.println("checkFollowSanDaiEr : " + useTime + " ms");
		
		
	}
	
}
