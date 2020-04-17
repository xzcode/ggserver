package com.xzcode.ggcloud.session.group.server.session;

import xzcode.ggserver.core.common.config.GGConfig;
import xzcode.ggserver.core.common.session.factory.DefaultChannelSessionFactory;

public class ServiceServerSessionFactory extends DefaultChannelSessionFactory {

	public ServiceServerSessionFactory(GGConfig config) {
		super(config);
	}

}
