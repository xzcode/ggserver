package xzcode.ggserver.core.common.message;

/**
 * 消息发送模型
 * 
 * @author zai
 * 2019-03-12 19:20:01
 */
public class Pack {
	

	/* 消息标识 */
	private byte[] action;

	/* 消息体 */
	private byte[] message;


	public Pack(byte[] action, byte[] message) {
		this.action = action;
		this.message = message;
	}


	public static Pack create(byte[] action, byte[] message) {
		return new Pack(action, message);
	}


	public byte[] getAction() {
		return action;
	}

	public void setAction(byte[] sendTag) {
		this.action = sendTag;
	}

	public byte[] getMessage() {
		return message;
	}

	public void setMessage(byte[] message) {
		this.message = message;
	}

}
