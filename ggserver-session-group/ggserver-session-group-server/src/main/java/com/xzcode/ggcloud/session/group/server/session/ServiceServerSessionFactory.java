package com.xzcode.ggcloud.session.group.server.session;

import com.xzcode.ggserver.core.common.config.GGConfig;
import com.xzcode.ggserver.core.common.session.factory.DefaultChannelSessionFactory;

public class ServiceServerSessionFactory extends DefaultChannelSessionFactory {

	public ServiceServerSessionFactory(GGConfig config) {
		super(config);
	}

}
