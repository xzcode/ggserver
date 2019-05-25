package xzcode.ggserver.game.common.algorithms.paijiu;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 牌九算法工具类
 * 
 * @author zai
 * 2019-05-25 17:09:07
 */
public class PaijiuAlgoUtil {
	
	
			
	/**
	 * 丁三
	 */
	public static int DING_SAN = 1003000;
	
	/**
	 * 二四
	 */
	public static int ER_SI = 1004000;
	
	/**
	 * 杂5-1
	 */
	public static int ZA_WU_1 = 1005132;
	
	/**
	 * 杂5-2
	 */
	public static int ZA_WU_2 = 1005214;
	
	/**
	 * 杂7-1
	 */
	public static int ZA_QI_1 = 1007125;
	/**
	 * 杂7-2
	 */
	public static int ZA_QI_2 = 1007234;
	/**
	 * 杂8-1
	 */
	public static int ZA_BA_1 = 1008126;
	/**
	 * 杂8-2
	 */
	public static int ZA_BA_2 = 1008235;
	/**
	 * 杂9-1
	 */
	public static int ZA_JIU_1 = 1009145;
	/**
	 * 杂9-2
	 */
	public static int ZA_JIU_2 = 1009236;
	/**
	 * 霖零六
	 */
	public static int LING_LING_LIU = 1106000;
	/**
	 * 高脚七
	 */
	public static int GAO_JIAO_QI = 1107000;
	/**
	 * 红头十
	 */
	public static int HONG_TOU_SHI = 1110000;
	/**
	 * 斧头
	 */
	public static int FU_TOU = 1111000;
	/**
	 * 板凳
	 */
	public static int BAN_DENG = 1202000;
	/**
	 * 长三
	 */
	public static int CHANG_SAN = 1206000;
	/**
	 * 梅牌
	 */
	public static int MEI_PAI = 1310000;
	/**
	 * 鹅牌
	 */
	public static int E_PAI = 1404000;
	/**
	 * 人牌
	 */
	public static int REN_PAI = 1408000;
	/**
	 * 地牌
	 */
	public static int DI_PAI = 1502000;
	/**
	 * 天牌
	 */
	public static int TIAN_PAI = 1512000;
	
	/**
	 * 对牌牌型预设
	 */
	public static final Map<Integer, PaijiuPairType> PAIR_TYPES = new HashMap<>();
	static {
		PAIR_TYPES.put(DING_SAN + ER_SI, PaijiuPairType.SHUANG_ZHUN);
		
		PAIR_TYPES.put(2 * TIAN_PAI, PaijiuPairType.SHUANG_TIAN);
		PAIR_TYPES.put(2 * DI_PAI, PaijiuPairType.SHUANG_DI);
		PAIR_TYPES.put(2 * REN_PAI, PaijiuPairType.SHUANG_REN);
		PAIR_TYPES.put(2 * E_PAI, PaijiuPairType.SHUANG_E);
		
		
		PAIR_TYPES.put(2 * MEI_PAI, PaijiuPairType.SHUANG_MEI);
		PAIR_TYPES.put(2 * CHANG_SAN, PaijiuPairType.SHUANG_CHANG_SAN);
		PAIR_TYPES.put(2 * BAN_DENG, PaijiuPairType.SHUANG_BAN_DENG);
		PAIR_TYPES.put(2 * FU_TOU, PaijiuPairType.SHUANG_FU_TOU);
		PAIR_TYPES.put(2 * HONG_TOU_SHI, PaijiuPairType.SHUANG_HONG_TOU);
		PAIR_TYPES.put(2 * GAO_JIAO_QI, PaijiuPairType.SHUANG_GAO_JIAO);
		PAIR_TYPES.put(2 * LING_LING_LIU, PaijiuPairType.SHUANG_LING_LING);
		
		
		PAIR_TYPES.put(ZA_JIU_1 + ZA_JIU_2, PaijiuPairType.ZA_JIU);
		PAIR_TYPES.put(ZA_BA_1 + ZA_BA_2, PaijiuPairType.ZA_BA);
		PAIR_TYPES.put(ZA_QI_1 + ZA_QI_2, PaijiuPairType.ZA_QI);
		PAIR_TYPES.put(ZA_WU_1 + ZA_WU_2, PaijiuPairType.ZA_WU);
		
		PAIR_TYPES.put(ZA_JIU_1 + TIAN_PAI, PaijiuPairType.TIAN_WANG);
		PAIR_TYPES.put(ZA_JIU_2 + TIAN_PAI, PaijiuPairType.TIAN_WANG);
		
		
		PAIR_TYPES.put(ZA_JIU_1 + DI_PAI, PaijiuPairType.DI_WANG);
		PAIR_TYPES.put(ZA_JIU_2 + DI_PAI, PaijiuPairType.DI_WANG);
		
		
		PAIR_TYPES.put(ZA_BA_1 + TIAN_PAI, PaijiuPairType.TIAN_GANG);
		PAIR_TYPES.put(ZA_BA_2 + TIAN_PAI, PaijiuPairType.TIAN_GANG);
		
		
		
		PAIR_TYPES.put(ZA_BA_1 + DI_PAI, PaijiuPairType.DI_GANG);
		PAIR_TYPES.put(ZA_BA_2 + DI_PAI, PaijiuPairType.DI_GANG);
		
		
		
		PAIR_TYPES.put(ZA_QI_1 + TIAN_PAI, PaijiuPairType.TIAN_GAO_JIU);
		
		PAIR_TYPES.put(ZA_QI_2 + DI_PAI, PaijiuPairType.DI_GAO_JIU);
		
	}
	
	
	/**
	 * 获取对牌型
	 * 
	 * @param cards
	 * @return
	 * @author zai
	 * 2019-05-25 16:56:20
	 */
	public static PaijiuPairType checkPairType(int[] cards) {
		//从小到大排序
		Arrays.sort(cards);
		PaijiuPairType pairType = PAIR_TYPES.get(cards[0] + cards[1]);
		//如果能组成对牌，返回对牌牌型
		if (pairType != null) {
			return pairType;
		}
		//获取不到对牌，计算点数
		int point = getPoints(cards);
		
		return getPointPairType(point);
	}
	
	/**
	 * 获取点数牌型
	 * 
	 * @param point
	 * @return
	 * @author zai
	 * 2019-05-25 18:07:07
	 */
	public static PaijiuPairType getPointPairType(int point) {
		switch (point) {
		case 0: return PaijiuPairType.NONE;
		case 1: return PaijiuPairType.DOT_ONE;
		case 2: return PaijiuPairType.DOT_TWO;
		case 3: return PaijiuPairType.DOT_THREE;
		case 4: return PaijiuPairType.DOT_FOUR;
		case 5: return PaijiuPairType.DOT_FIVE;
		case 6: return PaijiuPairType.DOT_SIX;
		case 7: return PaijiuPairType.DOT_SEVEN;
		case 8: return PaijiuPairType.DOT_EIGHT;
		case 9: return PaijiuPairType.DOT_NINE;
		default: return PaijiuPairType.NONE;
		}
	}
	
	/**
	 * 计算点数
	 * 
	 * @param cards
	 * @return
	 * @author zai
	 * 2019-05-25 18:06:46
	 */
	public static int getPoints(int[] cards) {
		//int points = (cards[0] + cards[1])  / 1000 % 1000 % 10;
		return (cards[0] + cards[1])  / 1000 % 10000;
	}
	
	public static PaijiuPairType checkCardType(List<PaijiuCard> cards) {
		int[] arr = new int[cards.size()];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = cards.get(i).getValue();
		}
		return checkPairType(arr);
	}
	
	
	public static void main(String[] args) {
		int[] cards = {TIAN_PAI , ZA_JIU_1};
		System.out.println(checkPairType(cards ));
	}
	
}
