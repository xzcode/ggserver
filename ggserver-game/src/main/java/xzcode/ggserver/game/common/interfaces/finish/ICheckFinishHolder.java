package xzcode.ggserver.game.common.interfaces.finish;

/**
 * 任务检测完成容器接口
 * 
 * @author zai
 * 2019-01-28 16:59:45
 */
public interface ICheckFinishHolder {
	
	/**
	 * 完成后行为设置
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
	void onCheckFinish(CheckFinishedAction check);
	
	/**
	 * 执行完成状态检查
	 * 
	 * @return
	 * @author zai
	 * 2019-01-28 17:00:39
	 */
	boolean checkFinish();
	
	/**
	 * 主动设置完成状态
	 * 
	 * @author zai
	 * 2019-01-28 17:00:39
	 */
	void setFinished(boolean finished);
	
	/**
	 * 执行完成后的行为
	 * 
	 * @author zai
	 * 2019-01-28 17:00:58
	 */
	void runOnFinishAction();
	
}
