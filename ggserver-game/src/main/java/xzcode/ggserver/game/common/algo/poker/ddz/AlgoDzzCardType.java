package xzcode.ggserver.game.common.algo.poker.ddz;

/**
 * 三公牌型
 * 
 * @author zai
 * 2019-05-27 18:36:23
 */
public enum AlgoDzzCardType {
	
	NONE(100, "0点"),
	DOT_1(101, "1点"),
	DOT_2(102, "2点"),
	DOT_3(103, "3点"),
	DOT_4(104, "4点"),
	DOT_5(105, "5点"),
	DOT_6(106, "6点"),
	DOT_7(107, "7点"),
	DOT_8(108, "8点"),
	DOT_9(109, "9点"),
	SAN_GONG(113, "三公"),
	BOMB(114, "炸弹"),
	BAO_JIU(115, "爆玖"),
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
