package xzcode.ggserver.game.common.interfaces.finish;

/**
 * 可完成接口
 * 
 * @author zai
 * 2019-01-28 16:59:45
 */
public interface IFinishAble {
	
	/**
	 * 完成时行为设置
	 * 
	 * @param action
	 * @author zai
	 * 2019-01-28 16:59:55
	 */
	void onFinish(OnFinishAction action);
	
	/**
	 * 完成检测行为设置
	 * 
	 * @param check
	 * @return
	 * @author zai
	 * 2019-01-28 17:00:05
	 */
	void onCheckFinished(CheckFinishedAction check);
	
	/**
	 * 获取完成状态
	 * 
	 * @return
	 * @author zai
	 * 2019-01-28 17:00:39
	 */
	boolean isFinished();
	
	/**
	 * 获取完成状态
	 * 
	 * @author zai
	 * 2019-01-28 17:00:39
	 */
	void setFinished(boolean finished);
	
	/**
	 * 执行完成行为
	 * 
	 * @author zai
	 * 2019-01-28 17:00:58
	 */
	void runFinished();
	
}
