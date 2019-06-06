package xzcode.ggserver.game.common.algo.poker.sg;

/**
 * 三公牌型结果
 * 
 * @author zai
 * 2019-05-27 14:39:39
 */
public class AlgoSgCheckResult {
	
	//牌型
	private AlgoSgCardType cardType;
	
	//点数
	private Integer points;
	
	//倍数
	private Integer cardTimes;
	
	
	
	

	public AlgoSgCheckResult(AlgoSgCardType cardType) {
		super();
		this.cardType = cardType;
	}


	public AlgoSgCheckResult(AlgoSgCardType cardType, Integer cardTimes) {
		this.cardType = cardType;
		this.cardTimes = cardTimes;
	}


	public AlgoSgCheckResult(AlgoSgCardType cardType, Integer points, Integer cardTimes) {
		this.cardType = cardType;
		this.points = points;
		this.cardTimes = cardTimes;
	}




	public AlgoSgCardType getCardType() {
		return cardType;
	}

	public void setCardType(AlgoSgCardType cardType) {
		this.cardType = cardType;
	}

	public void setPoints(Integer points) {
		this.points = points;
	}

	public Integer getPoints() {
		return points;
	}


	public Integer getCardTimes() {
		return cardTimes;
	}


	public void setCardTimes(Integer cardTimes) {
		this.cardTimes = cardTimes;
	}

	
}
