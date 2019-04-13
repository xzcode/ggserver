package xzcode.ggserver.game.common.player;

/**
     玩家基类
 * 
 * @author zai
 * 2019-01-21 20:21:20
 */
public class RoomPlayer<R, H> extends Player{
	/**
	 * IP地址
	 */
	protected String ip; 
	
	/**
	 * 用户座号
	 */
	protected int seatNum; 
	
	/**
	 * 是否参与游戏
	 */
	protected boolean inGame;
	
	/**
	 * 是否机器人
	 */
	protected boolean robot;
	/**
	 * 所在房间
	 */
	protected R room;
	
	/**
	 * 所在大厅
	 */
	protected H house;
	
	/**
	 * 所在大厅
	 */
	protected Long coins;
	
	/**
	 * 已玩局数
	 */
	private int playedRounds = 0;
	
	/**
	 * 可玩的最大局数
	 */
	private int maxPlayRounds = 0;
	
	public int getPlayedRounds() {
		return playedRounds;
	}
	
	public void setPlayedRounds(int playedRounds) {
		this.playedRounds = playedRounds;
	}
	
	public void incrPlayedRounds() {
		this.playedRounds += 1;
	}
	public void setMaxPlayRounds(int maxPlayRounds) {
		this.maxPlayRounds = maxPlayRounds;
	}
	public int getMaxPlayRounds() {
		return maxPlayRounds;
	}
	
	/**
	 * 是否最大已玩局数
	 * 
	 * @return
	 * @author zai
	 * 2019-04-02 19:37:34
	 */
	public boolean isMaxPlayerRound() {
		return playedRounds >= maxPlayRounds;
	}
	
	
	
	public R getRoom() {
		return room;
	}
	public void setRoom(R room) {
		this.room = room;
	}
	
	public H getHouse() {
		return house;
	}
	
	public void setHouse(H house) {
		this.house = house;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public int getSeatNum() {
		return seatNum;
	}
	public void setSeatNum(int seatNum) {
		this.seatNum = seatNum;
	}
	public boolean isInGame() {
		return inGame;
	}
	public void setInGame(boolean inGame) {
		this.inGame = inGame;
	}
	public boolean isRobot() {
		return robot;
	}
	public void setRobot(boolean robot) {
		this.robot = robot;
	}
	
	public Long getCoins() {
		return coins;
	}
	public void setCoins(Long coins) {
		this.coins = coins;
	}
	

}
