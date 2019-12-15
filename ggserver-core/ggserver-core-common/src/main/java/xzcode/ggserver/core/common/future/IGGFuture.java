package xzcode.ggserver.core.common.future;

import java.util.concurrent.Future;

/**
 * 未来对象
 * @param <V>
 * 
 * @author zai
 * 2019-11-24 17:35:47
 */
public interface IGGFuture extends Future<Object>{


	void addListener(IGGFutureListener<IGGFuture> listener);

	boolean cancel();

}