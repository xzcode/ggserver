package xzcode.ggserver.core.common.handler.pack.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import xzcode.ggserver.core.common.config.GGConfig;
import xzcode.ggserver.core.common.handler.pack.IReceivePackHandler;
import xzcode.ggserver.core.common.message.Pack;
import xzcode.ggserver.core.common.message.request.task.RequestMessageTask;

public class DefaultReceivePackHandler implements IReceivePackHandler {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DefaultReceivePackHandler.class);

	private GGConfig config;
	
	public DefaultReceivePackHandler(GGConfig config) {
		super();
		this.config = config;
	}

	@Override
	public void handle(Pack pack) {
		pack.getChannel().eventLoop().submit(new RequestMessageTask(pack, config));
	}


}
