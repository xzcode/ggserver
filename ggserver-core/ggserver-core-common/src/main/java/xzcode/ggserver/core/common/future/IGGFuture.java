package xzcode.ggserver.core.common.future;

import java.util.concurrent.Executor;
import java.util.concurrent.Future;

import javafx.concurrent.Task;
import xzcode.ggserver.core.common.executor.ITaskExecutor;

/**
 * 未来对象
 * @param <V>
 * 
 * @author zai
 * 2019-11-24 17:35:47
 */
public interface IGGFuture extends Future<Object>{


	void addListener(IGGFutureListener<IGGFuture> listener);
	
	void addListener(ITaskExecutor listenerExecutor,IGGFutureListener<IGGFuture> listener);

	boolean cancel();
	
	<T> T get(Class<T> clazz);

}