package xzcode.ggserver.game.card.games.algo.mj.classic;

import java.util.List;

public class AlgoMjOperation<M extends AlgoMj, P extends AlgoMjPlayer> {
	/**
	 * 麻将组合
	 */
	private List<? extends AlgoMj> mjs;
	
	/**
	 *目标麻将
	 */
	private M targetMj;

	/**
	 * 归属用户
	 */
	private P player;
	
	/**
	 * 操作类型
	 */
	private int type;
	
	/**
	 * 获取目标麻将牌值
	 * 
	 * @return
	 * @author zai
	 * 2019-05-27 11:38:38
	 */
	public int getTargetMjValue() {
		return targetMj.getValue();
	}

	public List<? extends AlgoMj> getMjs() {
		return mjs;
	}

	public void setMjs(List<? extends AlgoMj> mjs) {
		this.mjs = mjs;
	}

	public M getTargetMj() {
		return targetMj;
	}

	public void setTargetMj(M targetMj) {
		this.targetMj = targetMj;
	}

	public P getPlayer() {
		return player;
	}

	public void setPlayer(P player) {
		this.player = player;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	} 
	
	
	

}
