package xzcode.ggserver.game.card.games.room;

import java.nio.charset.Charset;

import xzcode.ggserver.core.common.executor.ITaskExecutor;
import xzcode.ggserver.core.common.executor.support.IExecutorSupport;
import xzcode.ggserver.core.common.filter.IFilterManager;
import xzcode.ggserver.core.common.handler.serializer.ISerializer;
import xzcode.ggserver.core.common.session.manager.ISessionManager;
import xzcode.ggserver.game.card.games.house.House;
import xzcode.ggserver.game.card.games.interfaces.IGGServerSupport;
import xzcode.ggserver.game.card.games.player.RoomPlayer;
import xzcode.ggserver.game.card.games.room.support.IRoomSupport;

/**
 * 单线程执行器房间
 * 
 * @param <P>
 * @param <R>
 * @param <H>
 * @author zai
 * 2019-12-22 17:32:54
 */
public abstract class SingleThreadExecutorRoom
<
P extends RoomPlayer<P, R, H>,
R extends Room<P, R, H>, 
H extends House<P, R, H>
>
extends Room<P, R, H>
implements 
IRoomSupport<P, R, H>,
IExecutorSupport,
IGGServerSupport
{	
	
	/**
	 * 单线程执行器
	 */
	protected ITaskExecutor taskExecutor;

	public SingleThreadExecutorRoom(ITaskExecutor singleThreadExecutor) {
		this.taskExecutor = singleThreadExecutor;
	}
	
	/**
	 * 添加操作到待执行队列
	 * 
	 * @param oper
	 * @author zai
	 * 2019-12-22 17:33:57
	 */
	public void addOperaction(IRoomOperaction oper) {
		taskExecutor.submitTask(oper);
	}
	

	@Override
	public ITaskExecutor getTaskExecutor() {
		//此处使用房间内的任务执行器
		return this.taskExecutor;
	}

	@Override
	public Charset getCharset() {
		return getGGServer().getCharset();
	}

	@Override
	public ISerializer getSerializer() {
		return getGGServer().getSerializer();
	}

	@Override
	public ISessionManager getSessionManager() {
		return getGGServer().getSessionManager();
	}

	@Override
	public IFilterManager getFilterManager() {
		return getGGServer().getFilterManager();
	}

	
	
}

