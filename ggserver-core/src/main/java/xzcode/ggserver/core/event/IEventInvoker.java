package xzcode.ggserver.core.event;

public interface IEventInvoker {
	
	public String getEventTag();

	void invoke() throws Exception;

}