package xzcode.ggserver.game.core.cardgames.player;

import xzcode.ggserver.game.core.player.Player;

/**
 * 房间玩家
 * 
 * @param <R>
 * @author zzz
 * 2019-09-22 10:07:01
 */
public class RoomPlayer<R> extends Player{
	/**
	 * IP地址
	 */
	protected String ip; 
	
	/**
	 * 用户座号
	 */
	protected int seatNo; 
	
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
	public int getSeatNo() {
		return seatNo;
	}
	public void setSeatNo(int seatNo) {
		this.seatNo = seatNo;
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
	
}
