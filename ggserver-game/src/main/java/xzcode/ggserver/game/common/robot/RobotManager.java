package xzcode.ggserver.game.common.robot;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentHashMap.KeySetView;

import xzcode.ggserver.game.common.player.Player;

/**
 * 机器人管理器
 * 
 * @author zai
 * 2019-01-29 14:22:08
 */
public class RobotManager<P extends Player> {
	
	private final ConcurrentHashMap<Object, P> robotMap = new ConcurrentHashMap<>();
	
	public ConcurrentHashMap<Object, P> getRobotMap() {
		return robotMap;
	}
	
	/**
	 * 添加机器人到管理器中
	 * 
	 * @param robotPlayer
	 * @author zai
	 * 2019-01-29 14:56:43
	 */
	public void addRobot(P robotPlayer) {
		robotMap.put(robotPlayer.getPlayerId(), robotPlayer);
	}
	
	/**
	 * 批量添加机器人
	 * 
	 * @param robotPlayers
	 * @author zai
	 * 2019-01-29 15:00:13
	 */
	public void addRobots(List<P> robotPlayers) {
		for (P p : robotPlayers) {
			robotMap.put(p.getPlayerId(), p);	
		}
	}
	
	/**
	 * 随机获取机器人
	 * 
	 * @author zai
	 * 2019-01-29 15:11:49
	 */
	public List<P> takeRandomRobots(int size) {
		List<P> list = null;
		if (robotMap.size() < size) {
			return list;
		}
		list = new ArrayList<>(size);
		
		Random random = new Random();
		for (int i = 0; i < size; i++) {
			KeySetView<Object,P> keySetView = robotMap.keySet();
			Object[] keys = keySetView.toArray();
			Object randomKey = keys[random.nextInt(keys.length)];
			list.add(robotMap.remove(randomKey));
		}
		
		return list;
		
	}

}
