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
	
	
	/**
	 * 状态
	 */
	protected int status = Status.IDLE;
	
	/**
	 * 使用超时
	 */
	protected Long timeoutMillisec;
	
	/**
	 * 状态常量
	 * 
	 * @author zai
	 * 2019-03-06 11:48:43
	 */
	public static interface Status {
		
		/**
		 * 空闲
		 */
		int IDLE = 0;
		
		/**
		 * 使用中
		 */
		int BUSY = 1;
		
	}

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


	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Long getTimeoutMillisec() {
		return timeoutMillisec;
	}

	public void setTimeoutMillisec(Long timeoutMillisec) {
		this.timeoutMillisec = timeoutMillisec;
	} 
	
	
	
	
}
