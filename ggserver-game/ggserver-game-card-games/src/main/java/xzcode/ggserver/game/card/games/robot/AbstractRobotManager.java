package xzcode.ggserver.game.card.games.robot;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;

import xzcode.ggserver.game.card.games.robot.factory.IRobotFactory;

/**
 * 机器人抽象管理器
 * 
 * @param <P>
 * @author zai
 * 2019-12-21 10:38:30
 */
public abstract class AbstractRobotManager<P, R> implements IRobotManager<P>{
	
	/**
	 * 机器人集合
	 */
	protected CopyOnWriteArraySet<P> remainRobots = new CopyOnWriteArraySet<>();
	
	/**
	 * 工作中的机器人
	 */
	protected CopyOnWriteArraySet<P> workingRobots = new CopyOnWriteArraySet<>();
	
	
	
	/**
	 * 机器人工厂
	 */
	protected IRobotFactory<P, R> robotFactory;
	
	
	/**
	 * 初始化构造器
	 * @param initRobotSize 初始化机器人数量
	 * @param robotFactory 机器人工厂
	 */
	public AbstractRobotManager(int initRobotSize, IRobotFactory<P, R> robotFactory) {
		this.robotFactory = robotFactory;
		initRobots(initRobotSize);
	}

	/**
	 * 初始化机器人
	 * 
	 * @param size
	 * @author zai
	 * 2019-12-21 10:57:41
	 */
	protected void initRobots(int size){
		for (int i = 0; i < size; i++) {
			remainRobots.add(robotFactory.createRobot(null));
		}
	}
	
	@Override
	public P acquireOneRobot() {
		Iterator<P> iterator = remainRobots.iterator();
		if (iterator.hasNext()) {
			P next = iterator.next();
			iterator.remove();
			workingRobots.add(next);
			return next;
		}
		return null;
	}

	@Override
	public List<P> acquireRobots(int size) {
		if (remainRobots.size() == 0) {
			return null;
		}
		Iterator<P> iterator = remainRobots.iterator();
		List<P> list = new ArrayList<>(size);
		int i = size;
		while (iterator.hasNext()) {
			P next = iterator.next();
			list.add(next);
			iterator.remove();
			workingRobots.add(next);
			i--;
			if (i == 0) {
				break;
			}
		}
		return list;
	}

	@Override
	public void returnRobot(P robot) {
		remainRobots.add(robot);
		workingRobots.remove(robot);
	}


	public CopyOnWriteArraySet<P> getRemainRobots() {
		return remainRobots;
	}


	public void setRemainRobots(CopyOnWriteArraySet<P> remainRobots) {
		this.remainRobots = remainRobots;
	}


	public CopyOnWriteArraySet<P> getWorkingRobots() {
		return workingRobots;
	}


	public void setWorkingRobots(CopyOnWriteArraySet<P> workingRobots) {
		this.workingRobots = workingRobots;
	}

}
