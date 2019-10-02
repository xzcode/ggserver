package xzcode.ggserver.core.handler.pack.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import xzcode.ggserver.core.config.GGConfig;
import xzcode.ggserver.core.handler.pack.IGGSendPackHandler;
import xzcode.ggserver.core.message.PackModel;
import xzcode.ggserver.core.session.GGSession;

public class DefaultSendPackHandler implements IGGSendPackHandler {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DefaultSendPackHandler.class);

	private GGConfig config;
	
	
	public DefaultSendPackHandler(GGConfig config) {
		this.config = config;
	}
	
	@Override
	public void handle(PackModel packModel, GGSession session) throws Exception {
		
	}



}
