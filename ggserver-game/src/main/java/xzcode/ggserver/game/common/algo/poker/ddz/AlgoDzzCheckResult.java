package xzcode.ggserver.game.common.algo.poker.ddz;

/**
 * 三公牌型结果
 * 
 * @author zai
 * 2019-05-27 14:39:39
 */
public class AlgoDzzCheckResult {
	
	//牌型
	private AlgoDzzCardType algoDzzCardType;
	
	//点数
	private Integer points;
	
	
	
	

	public AlgoDzzCheckResult(AlgoDzzCardType algoDzzCardType) {
		this.algoDzzCardType = algoDzzCardType;
	}

	public AlgoDzzCheckResult(AlgoDzzCardType algoDzzCardType, Integer points) {
		this.algoDzzCardType = algoDzzCardType;
		this.points = points;
	}
	

	public AlgoDzzCardType getBj21CardType() {
		return algoDzzCardType;
	}

	public AlgoDzzCardType getBj21Type() {
		return algoDzzCardType;
	}

	public Integer getPoints() {
		return points;
	}

	
}
