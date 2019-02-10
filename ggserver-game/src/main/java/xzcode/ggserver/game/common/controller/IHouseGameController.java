package xzcode.ggserver.game.common.controller;

public interface IHouseGameController<H> {
	
	/**
	 * 获取大厅
	 * 
	 * @param houseId
	 * @return
	 * @author zai
	 * 2019-01-24 17:25:53
	 */
	public abstract H getHouse(Object houseId);

}
