package com.xzcode.socket.core.event;

public interface IEventInvoker {
	
	public String getEventTag();

	void invoke() throws Exception;

}