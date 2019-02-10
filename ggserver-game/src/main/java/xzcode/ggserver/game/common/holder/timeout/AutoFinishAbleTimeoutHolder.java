package xzcode.ggserver.game.common.holder.timeout;

import xzcode.ggserver.game.common.interfaces.finish.IAutoFinishAble;
import xzcode.ggserver.game.common.interfaces.finish.OnAutoFinishAction;
import xzcode.ggserver.game.common.interfaces.timeout.TimeoutAction;

/**
 * 可自动完成超时容器
 * 
 * @author zai
 * 2019-01-28 16:42:14
 */
public class AutoFinishAbleTimeoutHolder extends FinishAbleTimeoutHolder implements IAutoFinishAble{

	protected OnAutoFinishAction onAutoFinishAction;
	
	protected Object lockObj;
	
	public void setLockObj(Object lockObj) {
		this.lockObj = lockObj;
	}
	
	public Object getLockObj() {
		if (lockObj == null) {
			return this;
		}
		return lockObj;
	}
	
	@Override
	public void onAutoFinish(OnAutoFinishAction action) {
		this.onAutoFinishAction = action;
	}

	@Override
	public void runAutoFinish() {
		if (onAutoFinishAction != null) {
			onAutoFinishAction.action(this);
		}
	}

	@Override
	public TimeoutAction getTimeoutAction() {
		if (super.getTimeoutAction() != null) {
			return super.getTimeoutAction();
		}
		return ()-> {
			if (this.isFinished()) {
				return;
			}
			synchronized(getLockObj()) {
				if (this.isFinished()) {
					return;
				}
				//执行自动完成操作
				this.runAutoFinish();
			}
		};
	}


	

	
	
	
	
	
	
	
}
