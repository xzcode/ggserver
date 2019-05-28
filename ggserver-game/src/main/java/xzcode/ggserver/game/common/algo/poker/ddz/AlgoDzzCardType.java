package xzcode.ggserver.game.common.algo.poker.ddz;

/**
 * 三公牌型
 * 
 * @author zai
 * 2019-05-27 18:36:23
 */
public enum AlgoDzzCardType {
	
	NONE(100, "单张"),
	DAN_ZHANG(101, "单张"),
	DUI_ZI(102, "对子"),
	SAN_ZHANG(103, "三张"),
	SAN_DAI_YI(104, "三带一"),
	SAN_DAI_ER(105, "三带二"),
	SI_DAI_ER(106, "四带二"),
	DAN_SHUN_ZI(107, "单顺子"),
	SHUANG_SHUN_ZI(108, "双顺子"),
	SAN_SHUN_ZI(109, "三顺子"),
	FEI_JI(110, "飞机"),
	ZHA_DAN(111, "炸弹"),
	WANG_ZHA(112, "王炸"),
	;
	
	
	private int value;
	
	private String name;
	
		
	private AlgoDzzCardType(int value, String name) {
		this.value = value;
		this.name = name;
	}



	public int getValue() {
		return value;
	}



	public void setValue(int value) {
		this.value = value;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	
	
}
