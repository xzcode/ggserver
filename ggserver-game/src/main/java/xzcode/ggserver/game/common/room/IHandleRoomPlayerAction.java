package xzcode.ggserver.game.common.room;


public interface IHandleRoomPlayerAction<R, P> {
	
	void handle(R room, P player);
	
}
