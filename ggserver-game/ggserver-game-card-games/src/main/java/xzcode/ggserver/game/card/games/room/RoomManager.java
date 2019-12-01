package xzcode.ggserver.game.card.games.room;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import xzcode.ggserver.core.server.GGServer;

public class RoomManager<R> {
	/**
	 * ggserver对象
	 */
	protected GGServer server;
	
	/**
	 * 房间集合
	 */
	protected Map<String, R> rooms = new ConcurrentHashMap<>(2000);
	

}
