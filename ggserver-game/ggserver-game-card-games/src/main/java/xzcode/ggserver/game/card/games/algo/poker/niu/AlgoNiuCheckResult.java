package xzcode.ggserver.game.card.games.algo.poker.niu;

/**
 * 牌型结果
 * 
 * @author zai
 * 2019-05-27 14:39:39
 */
public class AlgoNiuCheckResult {
	
	//牌型
	private int cardType;
	
	//点数
	private int points;
	

	public AlgoNiuCheckResult(int cardType) {
		this.cardType = cardType;
	}

	public AlgoNiuCheckResult(int cardType, int points) {
		this.cardType = cardType;
		this.points = points;
	}

	public int getCardType() {
		return cardType;
	}

	public void setCardType(int cardType) {
		this.cardType = cardType;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(Integer points) {
		this.points = points;
	}
	
	
	
	
}
