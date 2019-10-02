package xzcode.ggserver.core.handler.pack.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import xzcode.ggserver.core.channel.DefaultChannelAttributeKeys;
import xzcode.ggserver.core.config.GGConfig;
import xzcode.ggserver.core.handler.codec.impl.DefaultDecodeHandler;
import xzcode.ggserver.core.handler.pack.IGGRequestPackHandler;
import xzcode.ggserver.core.message.PackModel;
import xzcode.ggserver.core.message.receive.RequestMessageTask;
import xzcode.ggserver.core.session.GGSession;

public class DefaultRequestPackHandler implements IGGRequestPackHandler {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DefaultRequestPackHandler.class);

	private GGConfig config;
	
	public DefaultRequestPackHandler(GGConfig config) {
		super();
		this.config = config;
	}

	@Override
	public void handle(PackModel packModel, GGSession session) throws Exception {

		config.getTaskExecutor().submit(new RequestMessageTask(packModel, session.getChannel().attr(DefaultChannelAttributeKeys.SESSION).get(), config));
		
		if(LOGGER.isInfoEnabled()){
        	LOGGER.info("\nReceived binary message  <----,\nchannel:{}\ntag:{}\nbytes-length:{}\ndata:{}", session.getChannel(), packModel.getAction() == null ? "" : new String(packModel.getAction()), packModel.getAction().length + (packModel.getMessage() == null ? 0 : packModel.getMessage().length) + 4, packModel.getMessage() == null ? "" : new String(packModel.getMessage()));
        }
	}


}
