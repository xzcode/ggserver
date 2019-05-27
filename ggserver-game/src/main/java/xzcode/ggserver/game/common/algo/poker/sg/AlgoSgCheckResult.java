package xzcode.ggserver.game.common.algo.poker.sg;

/**
 * 三公牌型结果
 * 
 * @author zai
 * 2019-05-27 14:39:39
 */
public class AlgoSgCheckResult {
	
	//牌型
	private AlgoSgCardType algoSgCardType;
	
	//点数
	private Integer points;
	
	
	
	

	public AlgoSgCheckResult(AlgoSgCardType algoSgCardType) {
		this.algoSgCardType = algoSgCardType;
	}

	public AlgoSgCheckResult(AlgoSgCardType algoSgCardType, Integer points) {
		this.algoSgCardType = algoSgCardType;
		this.points = points;
	}
	

	public AlgoSgCardType getBj21CardType() {
		return algoSgCardType;
	}

	public AlgoSgCardType getBj21Type() {
		return algoSgCardType;
	}

	public Integer getPoints() {
		return points;
	}

	
}
