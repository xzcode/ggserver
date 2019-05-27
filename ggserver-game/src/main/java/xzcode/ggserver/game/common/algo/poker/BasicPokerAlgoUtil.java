package xzcode.ggserver.game.common.algo.poker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import xzcode.ggserver.game.common.poker.constant.PokerCardType;

/**
 * 基本扑克工具
 * 
 * @author zai 2018-12-27 18:06:30
 */
public class BasicPokerAlgoUtil {
	
	public static final int CARD_SPADE_START = 101;
	public static final int CARD_SPADE_END = 113;
	public static final int CARD_HEART_START = 201;
	public static final int CARD_HEART_END = 213;
	public static final int CARD_CLUB_START = 301;
	public static final int CARD_CLUB_END = 313;
	public static final int CARD_DEMAND_START = 401;
	public static final int CARD_DEMAND_END = 413;
	public static final int CARD_JOKER_START = 501;
	public static final int CARD_JOKER_END = 502;

	public static final List<Integer> CARD_VAL_LIST = Arrays.asList(
			101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113,//方块1 到 K
			201, 202, 203, 204, 205, 206, 207, 208, 209, 210, 211, 212, 213,//梅花1 到 K
			301, 302, 303, 304, 305, 306, 307, 308, 309, 310, 311, 312, 313,//红桃1 到 K
			401, 402, 403, 404, 405, 406, 407, 408, 409, 410, 411, 412, 413,//黑桃1 到 K
			501, //小王
			502 //大王
		);
	
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
	 * 是否黑桃
	 * 
	 * @param cardType
	 * @return
	 * @author zai
	 * 2019-01-22 11:17:03
	 */
	public static boolean isSpade(int value) {
		return value >= CARD_SPADE_START && value <= CARD_SPADE_END;
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
		return value >= CARD_HEART_START && value <= CARD_HEART_END;
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
		return value >= CARD_CLUB_START && value <= CARD_CLUB_END;
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
		return value >= CARD_DEMAND_START && value <= CARD_DEMAND_END;
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
		return value >= CARD_JOKER_START && value <= CARD_JOKER_END;
	}
	
	/**
	 * 获取扑克花色类型值
	 * 
	 * @param value
	 * @return
	 * @author zai
	 * 2019-01-24 19:06:41
	 */
	public static int getPokerCardTypeValue(int value) {
		return getPokerCardType(value).getValue();
	}
	
	/**
	 * 获取扑克花色类型
	 * 
	 * @param value
	 * @return
	 * @author zai
	 * 2019-01-24 19:10:08
	 */
	public static PokerCardType getPokerCardType(int value) {
		if (isSpade(value)) {
			return PokerCardType.SPADE;
		}
		if (isHeat(value)) {
			return PokerCardType.HEART;
		}
		if (isClub(value)) {
			return PokerCardType.CLUB;
		}
		if (isDemand(value)) {
			return PokerCardType.DEMAND;
		}
		if (isJoker(value)) {
			return PokerCardType.JOKER;
		}
		return null;
	}

}
