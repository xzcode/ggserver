package xzcode.ggserver.core.event;

import xzcode.ggserver.core.config.IGGConfigSupport;
import xzcode.ggserver.core.session.IGGSessonSupport;

public interface IEventInvokeSupport extends IGGSessonSupport,IGGConfigSupport{
	/**
	 * 动态添加事件监听
	 * 
	 * @param eventTag
	 * @param runnable
	 * @author zai
	 * 2019-01-02 20:02:37
	 */
	default <T> void onEvent(String eventTag, Runnable runnable) {
		EventRunnableInvoker invoker = new EventRunnableInvoker();
		invoker.setEventTag(eventTag);
		invoker.addRunnable(runnable);
		getConfig().getEventInvokerManager().put(invoker);
	}
	
	/**
	 * 发送自定义事件
	 * 
	 * @param eventTag
	 * @param message
	 * @author zai
	 * 2019-06-23 18:15:48
	 */
	default void emitEvent(String eventTag, Object message) {
		getConfig().getTaskExecutor().submit(new GGEventTask(getSession(), eventTag, message, getConfig()));
	}
	
	/**
	 * 发送自定义事件
	 * 
	 * @param eventTag
	 * @author zai
	 * 2019-06-23 18:16:17
	 */
	default void emitEvent(String eventTag) {
		getConfig().getTaskExecutor().submit(new GGEventTask(getSession(), eventTag, null, getConfig()));
	}
}
