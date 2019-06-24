package xzcode.ggserver.game.common.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import xzcode.ggserver.core.GGServer;

/**
 * ggs游戏控制器基类
 * 
 * 
 * @author zai
 * 2019-02-10 14:10:53
 */
public abstract class GGServerGameController implements IGGServerGameController{

	public static final Logger logger = LoggerFactory.getLogger(GGServerGameController.class);
	
	/**
	 * GGServer对象
	 */
	protected GGServer gg;
	
	public GGServerGameController() {
		this.gg = getGGServer();
	}

}
