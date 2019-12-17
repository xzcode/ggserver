package xzcode.ggserver.docs.core;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import xzcode.ggserver.docs.core.annotation.DocsModel;
import xzcode.ggserver.docs.core.annotation.DocsModelProperty;
import xzcode.ggserver.docs.core.annotation.DocsNamespace;

@DocsNamespace(name="login", description = "登录模块")
@DocsModel(desc = "登录请求", actionId = LoginReq.ACTION_ID)
public class LoginReq {
	
	public static final String ACTION_ID = "login.req";

	@DocsModelProperty(value = "toekn(登录凭据)")
	@NotBlank(message = "token不能为空")
	@Size(max = 64, message = "token不能超过64个字")
	private String token;
	
	@DocsModelProperty(value = "游戏id")
	@NotNull(message = "游戏id不能为空")
	private Long gameId;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Long getGameId() {
		return gameId;
	}

	public void setGameId(Long gameId) {
		this.gameId = gameId;
	}
	
	
	

}
