package xzcode.ggserver.game.common.algo.poker.ddz;

/**
 * 斗地主牌型
 * 
 * @author zai
 * 2019-05-27 18:36:23
 */
public interface AlgoDzzCardType {
	
	/**无牌型*/
	int NONE = 100;

	/**单张*/
	int DAN_ZHANG = 101;
	
	/**对子*/
	int DUI_ZI = 102;
	
	/**三张*/
	int SAN_ZHANG = 103; 
	
	/**三带一*/
	int SAN_DAI_YI = 104;
	
	/**三带二*/
	int SAN_DAI_ER = 105;
	
	/**四带二*/
	int SI_DAI_ER = 106; 
	
	/**单顺子*/
	int DAN_SHUN_ZI = 107;
	
	/**双顺子*/
	int SHUANG_SHUN_ZI = 108; 
	
	/**三顺子*/
	int SAN_SHUN_ZI = 109; 
	
	/**飞机*/
	int FEI_JI = 110; 
	
	/**炸弹*/
	int ZHA_DAN = 111; 
	
	/**王炸*/
	int WANG_ZHA = 112; 
	
}
