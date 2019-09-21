package xzcode.ggserver.game.support.interfaces.finish;

/**
 * 任务检测与可自动完成容器接口
 * 
 * @author zai
 * 2019-01-28 16:57:40
 */
public interface ICheckAutoFinishHolder extends ICheckFinishHolder{
	
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
	void runAutoFinishAction();
	
}
