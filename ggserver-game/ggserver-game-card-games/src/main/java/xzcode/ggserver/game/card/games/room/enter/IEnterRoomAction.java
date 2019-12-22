package xzcode.ggserver.game.card.games.room.enter;

/**
 * 进入房间行为接口
 * 
 * @author zai
 * 2019-12-22 14:05:41
 */
public interface IEnterRoomAction<P, R, H> extends Runnable {
	
	/**
	 * 获取大厅编号
	 * 
	 * @return
	 * @author zai
	 * 2019-12-22 16:01:04
	 */
	String getHouseNo();
	
}
