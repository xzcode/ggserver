package xzcode.ggserver.core.message;

/**
 * 消息发送模型
 * 
 * @author zai
 * 2019-03-12 19:20:01
 */
public class PackModel {
	

	/* 消息标识 */
	private byte[] action;

	/* 消息体 */
	private byte[] message;


	public PackModel(byte[] action, byte[] message) {
		this.action = action;
		this.message = message;
	}
	public PackModel(byte[] action, byte[] message, int sendType) {
		this.action = action;
		this.message = message;
	}


	public static PackModel create(byte[] action, byte[] message, int sendType) {
		return new PackModel(action, message);
	}
	public static PackModel create(byte[] action, byte[] message) {
		return new PackModel(action, message);
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
