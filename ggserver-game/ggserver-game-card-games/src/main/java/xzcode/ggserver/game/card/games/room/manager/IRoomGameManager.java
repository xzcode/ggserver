package xzcode.ggserver.game.card.games.room.manager;

import java.util.Map;

import xzcode.ggserver.core.common.executor.support.IExecutorSupport;
import xzcode.ggserver.core.common.future.IGGFuture;
import xzcode.ggserver.game.card.games.house.House;
import xzcode.ggserver.game.card.games.player.RoomPlayer;
import xzcode.ggserver.game.card.games.room.Room;

public interface IRoomGameManager
<
P extends RoomPlayer<P, R, H>, 
R extends Room<P, R, H>, 
H extends House<P, R, H>
> 
extends IExecutorSupport
{

	P createPlayer();

	R createRoom();


	IGGFuture submitTask(Runnable runnable);

	void removeRoom(String roomNo);

	void removeRoom(R room);

	void addRoom(R room);

	void addHouse(H house);

	void removeHouse(H house);

	void removeHouse(String houseNO);

	void addPlayer(P player);

	void removePlayer(P player);

	void removePlayer(String playerNo);

	H getHouse(String houseNo);

	Map<String, H> getHouses();

	Map<String, P> getPlayers();

	P getPlayer(String playerNo);
	
	void updateHouses();


}