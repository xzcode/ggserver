package xzcode.ggserver.game.card.games.algo.pj;

public enum AlgoPjCardType {
	
	NONE(100, "零点"),
	DOT_1(101, "一点"),
	DOT_2(102, "二点"),
	DOT_3(103, "三点"),
	DOT_4(104, "四点"),
	DOT_5(105, "五点"),
	DOT_6(106, "六点"),
	DOT_7(107, "七点"),
	DOT_8(108, "八点"),
	DOT_9(109, "九点"),
	DI_GAO_JIU(110, "地高九"),
	TIAN_GAO_JIU(111, "天高九"),
	DI_GANG(112, "地杠"),
	TIAN_GANG(113, "天杠"),
	DI_WANG(114, "地王"),
	TIAN_WANG(115, "天王"),
	ZA_WU(116, "杂五"),
	ZA_QI(117, "杂七"),
	ZA_BA(118, "杂八"),
	ZA_JIU(119, "杂九"),
	SHUANG_LING_LING(120, "双零霖"),
	SHUANG_GAO_JIAO(121, "双高脚"),
	SHUANG_HONG_TOU(122, "双红头"),
	SHUANG_FU_TOU(123, "双斧头"),
	SHUANG_BAN_DENG(124, "双板凳"),
	SHUANG_CHANG_SAN(125, "双长三"),
	SHUANG_MEI(126, "双梅"),
	SHUANG_E(127, "双鹅"),
	SHUANG_REN(128, "双人"),
	SHUANG_DI(129, "双地"),
	SHUANG_TIAN(130, "双天"),
	ZHI_ZHUN(131, "至尊"),
	
	;
	
	
	private int value;
	
	private String name;
	
		
	private AlgoPjCardType(int value, String name) {
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
