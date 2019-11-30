package xzcode.ggserver.game.card.games.algo.poker.bj21;

/**
 * 21点牌型结果
 * 
 * @author zai
 * 2019-05-27 14:39:39
 */
public class AlgoBj21CheckResult {
	
	//牌型
	private AlgoBj21CardType cardType;
	
	//点数
	private Integer points;
	
	//点数A视为11点的情况
	private Integer pointsA;
	
	
	

	public AlgoBj21CheckResult(AlgoBj21CardType algoBj21CardType) {
		this.cardType = algoBj21CardType;
	}

	public AlgoBj21CheckResult(AlgoBj21CardType algoBj21CardType, Integer points) {
		this.cardType = algoBj21CardType;
		this.points = points;
	}
	

	public AlgoBj21CheckResult(AlgoBj21CardType algoBj21CardType, Integer points, Integer pointsA) {
		this.cardType = algoBj21CardType;
		this.points = points;
		this.pointsA = pointsA;
	}


	public Integer getPointsA() {
		return pointsA;
	}


	public AlgoBj21CardType getBj21Type() {
		return cardType;
	}

	public Integer getPoints() {
		return points;
	}

	public AlgoBj21CardType getCardType() {
		return cardType;
	}

	public void setCardType(AlgoBj21CardType cardType) {
		this.cardType = cardType;
	}

	public void setPoints(Integer points) {
		this.points = points;
	}

	public void setPointsA(Integer pointsA) {
		this.pointsA = pointsA;
	}

	
}
