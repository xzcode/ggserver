package xzcode.ggserver.game.support.player;

/**
     玩家基类
 * 
 * @author zai
 * 2019-01-21 20:21:20
 */
public class Player {
	
	/**
	 * 用户id
	 */
	protected Object playerId; 
	
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
	
	

	public Object getPlayerId() {
		return playerId;
	}


	public void setPlayerId(Object playerId) {
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
	
}
