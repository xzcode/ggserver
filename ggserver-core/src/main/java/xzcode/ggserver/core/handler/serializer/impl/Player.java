package xzcode.ggserver.core.handler.serializer.impl;

import java.util.List;

public final class Player {
	private String nickname;
	private int winTimes;
	private long coins;
	private Integer points;
	private boolean zhuang;
	private Boolean winner;
	private List<Integer> cardVals;
	private Card luckyCard;
	private List<Card> cards;
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public int getWinTimes() {
		return winTimes;
	}
	public void setWinTimes(int winTimes) {
		this.winTimes = winTimes;
	}
	public Integer getPoints() {
		return points;
	}
	public void setPoints(Integer points) {
		this.points = points;
	}
	public List<Card> getCards() {
		return cards;
	}
	public void setCards(List<Card> cards) {
		this.cards = cards;
	}
	public List<Integer> getCardVals() {
		return cardVals;
	}
	public void setCardVals(List<Integer> cardVals) {
		this.cardVals = cardVals;
	}
	public Card getLuckyCard() {
		return luckyCard;
	}
	public void setLuckyCard(Card luckyCard) {
		this.luckyCard = luckyCard;
	}
	public long getCoins() {
		return coins;
	}
	public void setCoins(long coins) {
		this.coins = coins;
	}
	public boolean isZhuang() {
		return zhuang;
	}
	public void setZhuang(boolean zhuang) {
		this.zhuang = zhuang;
	}
	public Boolean getWinner() {
		return winner;
	}
	public void setWinner(Boolean winner) {
		this.winner = winner;
	}
	
	
}
