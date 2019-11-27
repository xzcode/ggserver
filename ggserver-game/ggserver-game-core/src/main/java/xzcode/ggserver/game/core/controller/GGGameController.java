package xzcode.ggserver.game.core.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import xzcode.ggserver.core.server.GGServer;

/**
 * ggs游戏控制器基类
 * 
 * 
 * @author zai
 * 2019-02-10 14:10:53
 */
public abstract class GGGameController implements IGGGameController{

	public static final Logger logger = LoggerFactory.getLogger(GGGameController.class);
	
	/**
	 * GGServer对象
	 */
	protected GGServer gg;
	
	public GGGameController() {
		this.gg = getGGServer();
	}

}
