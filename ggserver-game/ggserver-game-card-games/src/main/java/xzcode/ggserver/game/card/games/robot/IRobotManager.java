package xzcode.ggserver.game.card.games.robot;

import java.util.List;

/**
 * 机器人管理器
 * 
 * @author zai
 * 2019-12-21 10:16:25
 */
public interface IRobotManager<P> {
	
	
	
	/**
	 * 获取一个机器人
	 * 
	 * @return
	 * @author zai
	 * 2019-12-21 10:18:14
	 */
	P acquireOneRobot();
	
	/**
	 * 获取机器人
	 * 
	 * @param size 机器人数量
	 * @return
	 * @author zai
	 * 2019-12-21 10:18:21
	 */
	List<P> acquireRobots(int size);
	
	
	/**
	 * 归还机器人
	 * 
	 * @param robot
	 * @author zai
	 * 2019-12-21 10:39:43
	 */
	void returnRobot(P robot);
	
	

}
