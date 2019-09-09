package xzcode.ggserver.game.common.message;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class GGGameMessage {
	
	protected abstract String getActionId();
	
}
