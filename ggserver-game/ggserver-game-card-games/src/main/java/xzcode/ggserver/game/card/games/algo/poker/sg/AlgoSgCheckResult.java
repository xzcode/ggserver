package xzcode.ggserver.game.card.games.algo.poker.sg;

/**
 * 三公牌型结果
 * 
 * @author zai
 * 2019-05-27 14:39:39
 */
public class AlgoSgCheckResult {
	
	//牌型
	private int cardType;
	
	//点数
	private Integer points;
	
	//倍数
	private Integer cardTimes;
	
	
	
	

	public AlgoSgCheckResult(int cardType) {
		super();
		this.cardType = cardType;
	}
	
	

	public AlgoSgCheckResult(int cardType, Integer points) {
		super();
		this.cardType = cardType;
		this.points = points;
	}



	public AlgoSgCheckResult(int cardType, Integer points, Integer cardTimes) {
		super();
		this.cardType = cardType;
		this.points = points;
		this.cardTimes = cardTimes;
	}

	public int getCardType() {
		return cardType;
	}

	public void setCardType(int cardType) {
		this.cardType = cardType;
	}

	public Integer getPoints() {
		return points;
	}

	public void setPoints(Integer points) {
		this.points = points;
	}

	public Integer getCardTimes() {
		return cardTimes;
	}

	public void setCardTimes(Integer cardTimes) {
		this.cardTimes = cardTimes;
	}
	
	
	
	

	
	
}
