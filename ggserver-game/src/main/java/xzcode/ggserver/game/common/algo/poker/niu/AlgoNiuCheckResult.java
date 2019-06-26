package xzcode.ggserver.game.common.algo.poker.niu;

/**
 * 21点牌型结果
 * 
 * @author zai
 * 2019-05-27 14:39:39
 */
public class AlgoNiuCheckResult {
	
	//牌型
	private AlgoNiuCardType cardType;
	
	//点数
	private Integer points;
	
	//点数A视为11点的情况
	private Integer pointsA;
	
	
	

	public AlgoNiuCheckResult(AlgoNiuCardType algoNiuCardType) {
		this.cardType = algoNiuCardType;
	}

	public AlgoNiuCheckResult(AlgoNiuCardType algoNiuCardType, Integer points) {
		this.cardType = algoNiuCardType;
		this.points = points;
	}
	

	public AlgoNiuCheckResult(AlgoNiuCardType algoNiuCardType, Integer points, Integer pointsA) {
		this.cardType = algoNiuCardType;
		this.points = points;
		this.pointsA = pointsA;
	}


	public Integer getPointsA() {
		return pointsA;
	}


	public AlgoNiuCardType getBj21Type() {
		return cardType;
	}

	public Integer getPoints() {
		return points;
	}

	public AlgoNiuCardType getCardType() {
		return cardType;
	}

	public void setCardType(AlgoNiuCardType cardType) {
		this.cardType = cardType;
	}

	public void setPoints(Integer points) {
		this.points = points;
	}

	public void setPointsA(Integer pointsA) {
		this.pointsA = pointsA;
	}

	
}
