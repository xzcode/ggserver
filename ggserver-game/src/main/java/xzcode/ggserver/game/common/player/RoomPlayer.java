package xzcode.ggserver.game.common.player;

import xzcode.ggserver.game.common.holder.timeout.TimeoutHolder;

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
	 * 是否已准备
	 */
	protected boolean ready;
	
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
	 * 已玩局数
	 */
	private int playedRounds = 0;
	
	/**
	 * 可玩的最大局数
	 */
	private int maxPlayRounds = 0;
	
	/**
	 * 自动准备超时容器
	 */
	protected TimeoutHolder autoReadyHolder;
	
	/**
	 * 空闲超时容器
	 */
	protected TimeoutHolder idleTimeoutHolder;
	
	public TimeoutHolder getIdleTimeoutHolder() {
		return idleTimeoutHolder;
	}
	
	public void setIdleTimeoutHolder(TimeoutHolder idleTimeoutHolder) {
		this.idleTimeoutHolder = idleTimeoutHolder;
	}
	
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
	public boolean isRobot() {
		return robot;
	}
	public void setRobot(boolean robot) {
		this.robot = robot;
	}
	
	public boolean isReady() {
		return ready;
	}
	public void setReady(boolean ready) {
		this.ready = ready;
	}
	
	public TimeoutHolder getAutoReadyHolder() {
		return autoReadyHolder;
	}
	
	public void setAutoReadyHolder(TimeoutHolder autoReadyHolder) {
		this.autoReadyHolder = autoReadyHolder;
	}
}
