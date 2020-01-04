package xzcode.ggserver.game.card.games.algo.pj;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import xzcode.ggserver.game.card.games.algo.BasicAlgoUtil;

/**
 * 牌九算法工具类
 * 
 * @author zai
 * 2019-05-25 17:09:07
 */
public class AlgoPjUtil extends BasicAlgoUtil{
			
	/**
	 * 丁三
	 */
	public static int DING_SAN 			= 1003000;
	
	/**
	 * 二四
	 */
	public static int ER_SI 			= 1006000;
	
	/**
	 * 杂5-1
	 */
	public static int ZA_WU_1 			= 1105132;
	
	/**
	 * 杂5-2
	 */
	public static int ZA_WU_2 			= 1105214;
	
	/**
	 * 杂7-1
	 */
	public static int ZA_QI_1 			= 1107125;
	/**
	 * 杂7-2
	 */
	public static int ZA_QI_2 			= 1107234;
	/**
	 * 杂8-1
	 */
	public static int ZA_BA_1 			= 1108126;
	/**
	 * 杂8-2
	 */
	public static int ZA_BA_2 			= 1108235;
	/**
	 * 杂9-1
	 */
	public static int ZA_JIU_1 			= 1109145;
	/**
	 * 杂9-2
	 */
	public static int ZA_JIU_2 			= 1109236;
	/**
	 * 霖零六
	 */
	public static int LING_LING_LIU 	= 1206000;
	/**
	 * 高脚七
	 */
	public static int GAO_JIAO_QI 		= 1207000;
	/**
	 * 红头十
	 */
	public static int HONG_TOU_SHI 		= 1210000;
	/**
	 * 斧头
	 */
	public static int FU_TOU 			= 1211000;
	/**
	 * 板凳
	 */
	public static int BAN_DENG 			= 1304000;
	/**
	 * 长三
	 */
	public static int CHANG_SAN 		= 1306000;
	/**
	 * 梅牌
	 */
	public static int MEI_PAI 			= 1410000;
	/**
	 * 鹅牌
	 */
	public static int E_PAI 			= 1504000;
	/**
	 * 人牌
	 */
	public static int REN_PAI 			= 1508000;
	/**
	 * 地牌
	 */
	public static int DI_PAI 			= 1602000;
	/**
	 * 天牌
	 */
	public static int TIAN_PAI 			= 1712000;
	
	/**
	 * 所有牌的合集
	 */
	public static int[] ALL_PAI_VALUES = {
			DING_SAN,
			ER_SI,
			ZA_WU_1,
			ZA_WU_2,
			ZA_QI_1,
			ZA_QI_2,
			ZA_BA_1,
			ZA_BA_2,
			ZA_JIU_1,
			ZA_JIU_2,
			LING_LING_LIU,
			GAO_JIAO_QI,
			HONG_TOU_SHI,
			FU_TOU,
			BAN_DENG,
			CHANG_SAN,
			MEI_PAI,
			E_PAI,
			REN_PAI,
			DI_PAI,
			TIAN_PAI 
	};
	
	
	/**
	 * 文牌集合
	 */
	public static int[] WEN_PAI_VALUES = {
			LING_LING_LIU,
			GAO_JIAO_QI,
			HONG_TOU_SHI,
			FU_TOU,
			BAN_DENG,
			CHANG_SAN,
			MEI_PAI,
			E_PAI,
			REN_PAI,
			DI_PAI,
			TIAN_PAI

	};
	
	/**
	 * 武牌集合
	 */
	public static int[] WU_PAI_VALUES = {
			DING_SAN,
			ER_SI,
			ZA_WU_1,
			ZA_WU_2,
			ZA_QI_1,
			ZA_QI_2,
			ZA_BA_1,
			ZA_BA_2,
			ZA_JIU_1,
			ZA_JIU_2,
	};
	
	/**
	 * 对牌牌型预设
	 */
	public static final Map<String, AlgoPjCardType> PAIR_TYPES = new HashMap<>();
	static {
		PAIR_TYPES.put(DING_SAN + "" + ER_SI, AlgoPjCardType.ZHI_ZHUN);
		
		PAIR_TYPES.put(TIAN_PAI + "" + TIAN_PAI, AlgoPjCardType.SHUANG_TIAN);
		PAIR_TYPES.put(DI_PAI + "" + DI_PAI, AlgoPjCardType.SHUANG_DI);
		PAIR_TYPES.put(REN_PAI + "" + REN_PAI, AlgoPjCardType.SHUANG_REN);
		PAIR_TYPES.put(E_PAI + "" + E_PAI, AlgoPjCardType.SHUANG_E);
		
		
		PAIR_TYPES.put(MEI_PAI + "" + MEI_PAI, AlgoPjCardType.SHUANG_MEI);
		PAIR_TYPES.put(CHANG_SAN + "" + CHANG_SAN, AlgoPjCardType.SHUANG_CHANG_SAN);
		PAIR_TYPES.put(BAN_DENG + "" + BAN_DENG, AlgoPjCardType.SHUANG_BAN_DENG);
		PAIR_TYPES.put(FU_TOU + "" + FU_TOU, AlgoPjCardType.SHUANG_FU_TOU);
		PAIR_TYPES.put(HONG_TOU_SHI + "" + HONG_TOU_SHI, AlgoPjCardType.SHUANG_HONG_TOU);
		PAIR_TYPES.put(GAO_JIAO_QI + "" + GAO_JIAO_QI, AlgoPjCardType.SHUANG_GAO_JIAO);
		PAIR_TYPES.put(LING_LING_LIU + "" + LING_LING_LIU, AlgoPjCardType.SHUANG_LING_LING);
		
		
		PAIR_TYPES.put(ZA_JIU_1 + "" + ZA_JIU_2, AlgoPjCardType.ZA_JIU);
		PAIR_TYPES.put(ZA_BA_1 + "" + ZA_BA_2, AlgoPjCardType.ZA_BA);
		PAIR_TYPES.put(ZA_QI_1 + "" + ZA_QI_2, AlgoPjCardType.ZA_QI);
		PAIR_TYPES.put(ZA_WU_1 + "" + ZA_WU_2, AlgoPjCardType.ZA_WU);
		
		PAIR_TYPES.put(ZA_JIU_1 + "" + TIAN_PAI, AlgoPjCardType.TIAN_WANG);
		PAIR_TYPES.put(ZA_JIU_2 + "" + TIAN_PAI, AlgoPjCardType.TIAN_WANG);
		
		
		PAIR_TYPES.put(ZA_JIU_1 + "" + DI_PAI, AlgoPjCardType.DI_WANG);
		PAIR_TYPES.put(ZA_JIU_2 + "" + DI_PAI, AlgoPjCardType.DI_WANG);
		
		
		PAIR_TYPES.put(ZA_BA_1 + "" + TIAN_PAI, AlgoPjCardType.TIAN_GANG);
		PAIR_TYPES.put(ZA_BA_2 + "" + TIAN_PAI, AlgoPjCardType.TIAN_GANG);
		
		
		
		PAIR_TYPES.put(ZA_BA_1 + "" + DI_PAI, AlgoPjCardType.DI_GANG);
		PAIR_TYPES.put(ZA_BA_2 + "" + DI_PAI, AlgoPjCardType.DI_GANG);
		PAIR_TYPES.put(REN_PAI + "" + DI_PAI, AlgoPjCardType.DI_GANG);
		
		
		
		PAIR_TYPES.put(ZA_QI_1 + "" + TIAN_PAI, AlgoPjCardType.TIAN_GAO_JIU);
		
		PAIR_TYPES.put(GAO_JIAO_QI + "" + DI_PAI, AlgoPjCardType.DI_GAO_JIU);
		
	}
	
	
	/**
	 * 获取对牌型
	 * 
	 * @param cards
	 * @return
	 * @author zai
	 * 2019-05-25 16:56:20
	 */
	public static AlgoPjCardType checkCardType(int[] cards) {
		//从小到大排序
		Arrays.sort(cards);
		AlgoPjCardType pairType = PAIR_TYPES.get(cards[0] + "" + cards[1]);
		//如果能组成对牌，返回对牌牌型
		if (pairType != null) {
			return pairType;
		}
		//获取不到对牌，计算点数
		int point = getPoints(cards);
		
		return getPointCardType(point);
	}
	
	/**
	 * 获取点数牌型
	 * 
	 * @param point
	 * @return
	 * @author zai
	 * 2019-05-25 18:07:07
	 */
	public static AlgoPjCardType getPointCardType(int point) {
		switch (point) {
		case 0: return AlgoPjCardType.NONE;
		case 1: return AlgoPjCardType.DOT_1;
		case 2: return AlgoPjCardType.DOT_2;
		case 3: return AlgoPjCardType.DOT_3;
		case 4: return AlgoPjCardType.DOT_4;
		case 5: return AlgoPjCardType.DOT_5;
		case 6: return AlgoPjCardType.DOT_6;
		case 7: return AlgoPjCardType.DOT_7;
		case 8: return AlgoPjCardType.DOT_8;
		case 9: return AlgoPjCardType.DOT_9;
		default: return AlgoPjCardType.NONE;
		}
	}
	
	/**
	 * 对比单牌大小
	 * 
	 * @param card1 第一个牌牌值
	 * @param card2 第二个牌牌值
	 * @return 第一个牌大，返回1；第二个牌大，返回2；否则一样大，返回0
	 * @author zai
	 * 2019-06-20 10:12:19
	 */
	public static int compareSingleCard(int card1, int card2) {
		
		if (card1 == DING_SAN && card2 == ER_SI) {
			return 0;
		}
		
		if (card2 == DING_SAN && card1 == ER_SI) {
			return 0;
		}
		
		if (card1 > card2) {
			return 1;
		}else if (card2 > card1) {
			return -1;
		}else {
			return 0;
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
		return (cards[0] + cards[1])  / 1000 % 10;
	}
	
	
}
