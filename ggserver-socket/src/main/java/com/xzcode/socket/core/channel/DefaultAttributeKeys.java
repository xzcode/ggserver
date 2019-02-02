package com.xzcode.socket.core.channel;

import com.xzcode.socket.core.session.imp.SocketSession;

import io.netty.util.AttributeKey;

public interface DefaultAttributeKeys {
	
	AttributeKey<SocketSession> SESSION = AttributeKey.valueOf("SESSION");
	
}
