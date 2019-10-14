package xzcode.ggserver.core.common.message.send.future;


import io.netty.channel.ChannelFuture;

/**
 * 发送future
 * 
 * @author zai
 * 2019-10-14 14:13:00
 */
public class GGSendFuture {
	
	private ChannelFuture channelFuture;
	
	private boolean completed;
	
	private Runnable completeAction;
	
	
	public GGSendFuture() {
	}

	public GGSendFuture(ChannelFuture channelFuture) {
		setScheduledFuture(channelFuture);
	}
	

	public void setScheduledFuture(ChannelFuture channelFuture) {
		this.channelFuture = channelFuture;
		channelFuture.addListener((e) -> {
			if (this.completeAction != null) {
				this.completeAction.run();
			}
		});
	}
	

	public ChannelFuture getChannelFuture() {
		return channelFuture;
	}


	public Runnable getCompleteAction() {
		return completeAction;
	}
	
	public void setCompleteAction(Runnable completeAction) {
		this.completeAction = completeAction;
	}
	public boolean isCompleted() {
		return completed;
	}


}
