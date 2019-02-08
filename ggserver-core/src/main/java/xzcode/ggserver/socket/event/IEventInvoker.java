package xzcode.ggserver.socket.event;

public interface IEventInvoker {
	
	public String getEventTag();

	void invoke() throws Exception;

}