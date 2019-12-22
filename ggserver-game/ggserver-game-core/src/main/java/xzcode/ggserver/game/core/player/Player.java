package xzcode.ggserver.game.core.player;

import xzcode.ggserver.core.common.session.GGSession;
import xzcode.ggserver.game.core.player.support.IGGSessionPlayerSupport;

/**
     玩家基类
 * 
 * @author zai
 * 2019-01-21 20:21:20
 */
public class Player implements IGGSessionPlayerSupport{
	
	protected GGSession session; 
	
	/**
	 * 玩家id
	 */
	protected String playerId; 
	
	/**
	 * 用户昵称
	 */
	protected String nickname; 
	
	/**
	 * 用户头像
	 */
	protected String avatar; 
	
	
	/**
	 * 是否在线
	 */
	protected boolean online;
	
	

	public String getPlayerId() {
		return playerId;
	}


	public void setPlayerId(String playerId) {
		this.playerId = playerId;
	}




	public String getNickname() {
		return nickname;
	}


	public void setNickname(String nickname) {
		this.nickname = nickname;
	}


	public String getAvatar() {
		return avatar;
	}


	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public boolean isOnline() {
		return online;
	}


	public void setOnline(boolean online) {
		this.online = online;
	}


	public GGSession getSession() {
		return session;
	}


	public void setSession(GGSession session) {
		this.session = session;
	}

	
}
