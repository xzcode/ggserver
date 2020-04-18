package com.xzcode.ggserver.core.common.session.id;

import java.util.UUID;

import io.netty.channel.Channel;

public class DefaultSessionIdGenerator implements ISessionIdGenerator {

	@Override
	public String generateSessionId(Channel channel) {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

}
