package xzcode.ggserver.game.common.robot;

/**
 * 机器人信息
 * 
 * @author zai
 * 2019-03-05 19:10:25
 */
public class RobotInfo {
	/**
	 * 用户id
	 */
	protected Object robotId; 
	
	/**
	 * 用户编号
	 */
	protected String robotNo; 
	
	/**
	 * 用户昵称
	 */
	protected String nickname; 
	
	/**
	 * 用户头像
	 */
	protected String avatar;
	
	

	

	public Object getRobotId() {
		return robotId;
	}

	public void setRobotId(Object robotId) {
		this.robotId = robotId;
	}

	public String getRobotNo() {
		return robotNo;
	}

	public void setRobotNo(String robotNo) {
		this.robotNo = robotNo;
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
	
	
	
	
}
