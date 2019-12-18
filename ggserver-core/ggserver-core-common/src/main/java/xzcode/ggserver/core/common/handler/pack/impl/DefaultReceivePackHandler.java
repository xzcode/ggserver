package xzcode.ggserver.core.common.handler.pack.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import xzcode.ggserver.core.common.config.GGConfig;
import xzcode.ggserver.core.common.handler.pack.IReceivePackHandler;
import xzcode.ggserver.core.common.message.Pack;
import xzcode.ggserver.core.common.message.request.task.RequestMessageTask;
import xzcode.ggserver.core.common.session.GGSession;

public class DefaultReceivePackHandler implements IReceivePackHandler {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DefaultReceivePackHandler.class);

	private GGConfig config;
	
	public DefaultReceivePackHandler(GGConfig config) {
		super();
		this.config = config;
	}

	@Override
	public void handle(Pack pack) {

		config.getTaskExecutor().submitTask(new RequestMessageTask(pack, config));
		
		if(LOGGER.isInfoEnabled()){
			GGSession session = pack.getSession();
			if (session != null) {
				LOGGER.info("\nReceived binary message  <----,\nfrom:{}\ntag:{}\nbytes-length:{}\ndata:{}", (session.getHost() + ":" +session.getPort()), pack.getAction() == null ? "" : new String(pack.getAction()), pack.getAction().length + (pack.getMessage() == null ? 0 : pack.getMessage().length) + 4, pack.getMessage() == null ? "" : new String(pack.getMessage()));
			}
        }
	}


}
