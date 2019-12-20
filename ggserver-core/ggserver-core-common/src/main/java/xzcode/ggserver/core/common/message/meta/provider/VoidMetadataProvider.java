package xzcode.ggserver.core.common.message.meta.provider;

import xzcode.ggserver.core.common.session.GGSession;
/**
 * 空的metadata提供者
 * 
 * 
 * @author zai
 * 2019-12-01 20:39:05
 */
public class VoidMetadataProvider implements IMetadataProvider<Void>{

	@Override
	public Void provide(GGSession session) {
		return null;
	}

}
