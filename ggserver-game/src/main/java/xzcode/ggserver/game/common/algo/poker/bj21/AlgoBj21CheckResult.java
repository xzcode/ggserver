package xzcode.ggserver.game.common.algo.poker.bj21;

/**
 * 21点牌型结果
 * 
 * @author zai
 * 2019-05-27 14:39:39
 */
public class AlgoBj21CheckResult {
	
	//牌型
	private AlgoBj21CardType algoBj21CardType;
	
	//点数
	private Integer points;
	
	//点数A视为11点的情况
	private Integer pointsA;
	
	
	

	public AlgoBj21CheckResult(AlgoBj21CardType algoBj21CardType) {
		this.algoBj21CardType = algoBj21CardType;
	}

	public AlgoBj21CheckResult(AlgoBj21CardType algoBj21CardType, Integer points) {
		this.algoBj21CardType = algoBj21CardType;
		this.points = points;
	}
	

	public AlgoBj21CheckResult(AlgoBj21CardType algoBj21CardType, Integer points, Integer pointsA) {
		this.algoBj21CardType = algoBj21CardType;
		this.points = points;
		this.pointsA = pointsA;
	}

	public AlgoBj21CardType getBj21CardType() {
		return algoBj21CardType;
	}

	public Integer getPointsA() {
		return pointsA;
	}


	public AlgoBj21CardType getBj21Type() {
		return algoBj21CardType;
	}

	public Integer getPoints() {
		return points;
	}

	
}
