package xzcode.ggserver.game.card.games.robot.factory;

/**
 * 机器人工厂接口
 * 
 * @param <P>
 * @author zai
 * 2019-12-21 10:54:44
 */
public interface IRobotFactory<P, R> {
	
	/**
	 * 创建机器人
	 * 
	 * @param room
	 * @return
	 * @author zai
	 * 2020-01-09 12:17:14
	 */
	P createRobot(R room);
	
	/**
	 * 批量创建机器人
	 * 
	 * @param size
	 * @param room
	 * @return
	 * @author zai
	 * 2020-01-09 14:55:51
	 */
	//P createRobots(int size, R room);

}
