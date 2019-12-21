package xzcode.ggserver.game.card.games.player;

import xzcode.ggserver.game.core.player.Player;

/**
 * 房间玩家
 * 
 * @param <R>
 * @author zai
 * 2019-12-21 15:57:44
 */
public class RoomPlayer<R> extends Player {
	
	/**
	 * 玩家编号
	 */
	protected String playerNo;
	
	
	/**
	 * IP地址
	 */
	protected String ip; 
	
	/**
	 * 用户座号
	 */
	protected int seat; 
	
	/**
	 * 是否已准备
	 */
	protected boolean ready;
	

	/**
	 * 是否参与游戏
	 */
	protected boolean inGame;
	
	/**
	 * 所在房间
	 */
	protected R room;
	
	public R getRoom() {
		return room;
	}
	public void setRoom(R room) {
		this.room = room;
	}
	
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public int getSeat() {
		return seat;
	}
	public void setSeat(int seatNo) {
		this.seat = seatNo;
	}
	
	public boolean isReady() {
		return ready;
	}
	public void setReady(boolean ready) {
		this.ready = ready;
	}
	public boolean isInGame() {
		return inGame;
	}
	public void setInGame(boolean inGame) {
		this.inGame = inGame;
	}
	public String getPlayerNo() {
		return playerNo;
	}
	public void setPlayerNo(String playerNo) {
		this.playerNo = playerNo;
	}
	
	
	
}
