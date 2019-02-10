package xzcode.ggserver.game.common.interfaces.finish;

/**
 * 可自动完成接口
 * 
 * @author zai
 * 2019-01-28 16:57:40
 */
public interface IAutoFinishAble extends IFinishAble{
	
	/**
	 * 自动完成行为设置
	 * 
	 * @param action
	 * @author zai
	 * 2019-01-28 16:57:48
	 */
	void onAutoFinish(OnAutoFinishAction action);
	
	/**
	 * 执行自动完成
	 * 
	 * @author zai
	 * 2019-01-28 16:58:07
	 */
	void runAutoFinish();
	
}
