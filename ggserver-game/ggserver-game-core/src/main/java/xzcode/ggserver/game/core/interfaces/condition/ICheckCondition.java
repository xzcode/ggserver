package xzcode.ggserver.game.core.interfaces.condition;

/**
 * 默认条件检查接口
 * @param <T>
 * 
 * @author zai
 * 2019-11-30 12:38:14
 */
public interface ICheckCondition<T> {
	
	/**
	 * 进行检查操作
	 * @param t
	 * @return
	 * 
	 * @author zai
	 * 2019-11-30 12:38:24
	 */
	boolean check(T t);

}
