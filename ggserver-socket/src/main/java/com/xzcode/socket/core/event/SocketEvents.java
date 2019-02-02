package com.xzcode.socket.core.event;

/**
 * socket 事件标识
 * 
 * 
 * @author zai
 * 2017-08-03
 */
public interface SocketEvents {
	
	/**
	 * 空闲状态 事件
	 * 
	 * 
	 * @author zai
	 * 2017-08-03
	 */
	interface IdleState {
		
		/**
	     * 读空闲
	     */
		String READER_IDLE = "IdleState.READER_IDLE";
	    /**
	     * 写空闲
	     */
		String WRITER_IDLE = "IdleState.READER_IDLE";
	    /**
	     * 读与写空闲
	     */
		String ALL_IDLE = "IdleState.ALL_IDLE";

	}
	
	
	/**
	 * channel状态
	 * 
	 * 
	 * @author zai
	 * 2017-09-25
	 */
	interface ChannelState {
		
		/**
	     * channel 激活
	     */
		String ACTIVE = "ChannelState.ACTIVE";
	    /**
	     * channel 关闭
	     */
		String CLOSE = "ChannelState.CLOSE";

	}

}
