package xzcode.ggserver.game.card.games.algo.poker.ddz;

/**
 * 斗地主牌型
 * 
 * @author zai
 * 2019-05-27 18:36:23
 */
public interface AlgoDzzCardType {
	
	/**无牌型*/
	int NONE = 1000;

	/**单张*/
	int DAN_ZHANG = 1010;
	
	/**对子*/
	int DUI_ZI = 1020;
	
	/**三张*/
	int SAN_ZHANG = 1030; 
	
	/**三带一*/
	int SAN_DAI_YI = 1040;
	
	/**三带二*/
	int SAN_DAI_ER = 1050;
	
	/**四带二-单(四带两张单牌)*/
	int SI_DAI_ER_DAN = 1060; 
	
	/**四带二-双(四带两对牌)*/
	int SI_DAI_ER_SHUANG = 1070; 
	
	/**单顺子*/
	int DAN_SHUN_ZI = 1080;
	
	/**双顺子(连对)*/
	int SHUANG_SHUN_ZI = 1090; 
	
	/**三顺子*/
	int SAN_SHUN_ZI = 1100; 
	
	/**飞机（三顺+单张）*/
	int FEI_JI_31 = 1110; 
	
	/**飞机（三顺+对子）*/
	int FEI_JI_32 = 1120; 
	
	/**炸弹*/
	int ZHA_DAN = 1130; 
	
	/**王炸*/
	int WANG_ZHA = 1140; 
	
}
