package xzcode.ggserver.game.common.algo.g28;

public enum AlgoG28CardType {
	
	NONE(1000, "鳖十"),
	
	DOT_1(1010, "一点"),
	DOT_1_HALF(1015, "一点半"),
	
	DOT_2(1020, "二点"),
	DOT_2_HALF(1025, "二点半"),
	
	DOT_3(1030, "三点"),
	DOT_3_HALF(1035, "三点半"),
	
	DOT_4(1040, "四点"),
	DOT_4_HALF(1045, "四点半"),
	
	DOT_5(1050, "五点"),
	DOT_5_HALF(1055, "五点半"),
	
	DOT_6(1060, "六点"),
	DOT_6_HALF(1065, "六点半"),
	
	DOT_7(1070, "七点"),
	DOT_7_HALF(1075, "七点半"),
	
	DOT_8(1080, "八点"),
	DOT_8_HALF(1085, "八点半"),
	
	DOT_9(1090, "九点"),
	DOT_9_HALF(1095, "九点半"),
	
	GANG_28(1128, "二八杠"),
	
	BAO_1(1210, "一宝"),
	BAO_2(1220, "二宝"),
	BAO_3(1230, "三宝"),
	BAO_4(1240, "四宝"),
	BAO_5(1250, "五宝"),
	BAO_6(1260, "六宝"),
	BAO_7(1270, "七宝"),
	BAO_8(1280, "八宝"),
	BAO_9(1290, "九宝"),
	
	TIAN_WANG(1310, "天王"),
	
	;
	
	
	private int value;
	private String name;
	
		
	private AlgoG28CardType(int value, String name) {
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
