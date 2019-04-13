package xzcode.ggserver.game.common.room.listener;


public interface IAfterRemovePlayerListener<R, P> {
	
	void onRemove(R room, P player);
	
}
