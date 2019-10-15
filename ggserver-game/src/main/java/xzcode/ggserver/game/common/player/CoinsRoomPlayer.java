package xzcode.ggserver.game.common.player;

/**
     金币房玩家基类
 * 
 * @author zai
 * 2019-01-21 20:21:20
 */
public class CoinsRoomPlayer<R, H> extends RoomPlayer<R, H>{
	/**
	 * 当前金币数
	 */
	protected Long coins= 0L;
	
	/**
	 * 游戏进行前金币数
	 */
	protected Long beforeCoins= 0L;
	
	/**
	 * 抽佣金币数
	 */
	protected Long waterCoins = 0L;
	
	/**
	 * 盈利金币数
	 */
	protected Long gainCoins;
	
	
	/*
	 * 
	 * 减去金币
	 */
	public void minusCoins(long minusCoins) {
		this.coins -= minusCoins;
		
	}
	/*
	 * 
	 * 增加金币
	 */
	public void incrCoins(long incrCoins) {
		this.coins += incrCoins;
		
	}
	
	public int getSeatNum() {
		return seatNum;
	}
	public void setSeatNum(int seatNum) {
		this.seatNum = seatNum;
	}
	
	public Long getCoins() {
		return coins;
	}
	public void setCoins(Long coins) {
		this.coins = coins;
	}
	public Long getGainCoins() {
		return gainCoins;
	}
	
	public void setGainCoins(Long gainCoins) {
		this.gainCoins = gainCoins;
	}
	
	public Long getWaterCoins() {
		return waterCoins;
	}
	public void setWaterCoins(Long waterCoins) {
		this.waterCoins = waterCoins;
	}

	public Long getBeforeCoins() {
		return beforeCoins;
	}
	
	public void setBeforeCoins(Long beforeCoins) {
		this.beforeCoins = beforeCoins;
	}
}
