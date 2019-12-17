package xzcode.ggserver.docs.core;

import xzcode.ggserver.docs.core.annotation.DocsModel;
import xzcode.ggserver.docs.core.annotation.DocsModelProperty;
import xzcode.ggserver.docs.core.annotation.DocsNamespace;

@DocsNamespace(name="login", description = "登录模块")
@DocsModel(desc = "登录响应", actionId = LoginResp.ACTION_ID)
public class LoginResp{
	
	public static final String ACTION_ID = "login.resp";
	
	@DocsModelProperty(value = "是否成功标识")
	private Boolean success;
	
	@DocsModelProperty(value = "消息")
	private String message;
	
	@DocsModelProperty(value = "错误码")
	private Integer code;
	
	public static LoginResp create(Boolean success, String message) {
		return new LoginResp(success, message, null);
	}
	

	public LoginResp() {
		super();
	}

	public LoginResp(Boolean success, String message, Integer code) {
		super();
		this.success = success;
		this.message = message;
		this.code = code;
	}

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}
	
	
	
	
}
