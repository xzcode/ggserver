package xzcode.ggserver.game.common.robot;

import java.util.List;

/**
 * 机器人管理器接口
 * 
 * @author zai
 * 2019-03-05 19:29:46
 */
public interface IRobotManager {
	
	/**
	 * 添加机器人到管理器中
	 * 
	 * @param robotPlayer
	 * @author zai
	 * 2019-01-29 14:56:43
	 */
	public void addRobot(RobotInfo robotPlayer);
	
	/**
	 * 返还机器人
	 * 
	 * @param robotPlayer
	 * @author zai
	 * 2019-02-25 15:54:20
	 */
	public void returnRobot(IRobotReturnAction returnAction);
	
	/**
	 * 批量添加机器人
	 * 
	 * @param robotPlayers
	 * @author zai
	 * 2019-01-29 15:00:13
	 */
	public void addRobots(List<RobotInfo> robotPlayers);
	
	/**
	 * 随机获取机器人
	 * 
	 * @author zai
	 * 2019-01-29 15:11:49
	 */
	public List<RobotInfo> takeRandomRobots(int size);
	
	/**
	 * 获取一个机器人玩家
	 * 
	 * @return
	 * @author zai
	 * 2019-02-20 17:27:34
	 */
	public RobotInfo takeOneRandomRobot();

}
