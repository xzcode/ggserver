package xzcode.ggserver.game.card.games.robot.factory;

/**
 * 机器人工厂接口
 * 
 * @param <P>
 * @author zai
 * 2019-12-21 10:54:44
 */
public interface IRobotFactory<P> {
	
	/**
	 * 创建机器人
	 * 
	 * @return
	 * @author zai
	 * 2019-12-21 10:54:39
	 */
	P createRobot();

}
