package xzcode.ggserver.game.common.room.listener;


public interface IAfterAddPlayerListener<R, P> {
	
	void onAdd(R room, P player);
	
}
