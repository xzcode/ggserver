package com.xzcode.ggserver.game.monitor.common.data;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.xzcode.ggserver.game.monitor.common.data.model.game.GameData;

public class GameDataManager {
	
	/**
	 * 服务组集合
	 */
	private Map<String, GameData> gameDatas = new ConcurrentHashMap<>();
	
}
