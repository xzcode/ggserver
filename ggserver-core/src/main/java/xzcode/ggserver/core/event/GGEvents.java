package xzcode.ggserver.core.event;

/**
 * socket 事件标识
 * 
 * 
 * @author zai
 * 2017-08-03
 */
public interface GGEvents {
	
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
		String READER_IDLE = "ggevents.idle.READER_IDLE";
	    /**
	     * 写空闲
	     */
		String WRITER_IDLE = "ggevents.idle.READER_IDLE";
	    /**
	     * 读与写空闲
	     */
		String ALL_IDLE = "ggevents.idle.ALL_IDLE";

	}
	
	
	/**
	 * 连接状态
	 * 
	 * 
	 * @author zai
	 * 2017-09-25
	 */
	interface ConnectionState {
		
		/**
	     * channel 激活
	     */
		String ACTIVE = "ggevents.conn.ACTIVE";
	    /**
	     * channel 关闭
	     */
		String CLOSE = "ggevents.conn.CLOSE";

	}

}
