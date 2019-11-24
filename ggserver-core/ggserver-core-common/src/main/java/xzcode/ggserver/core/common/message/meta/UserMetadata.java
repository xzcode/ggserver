package xzcode.ggserver.core.common.message.meta;

public class UserMetadata {
	
	private String userId;
	
	

	public UserMetadata() {
	}

	public UserMetadata(String userId) {
		super();
		this.userId = userId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	
}
