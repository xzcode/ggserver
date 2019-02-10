package xzcode.ggserver.game.common.poker.player;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import xzcode.ggserver.game.common.player.Player;
import xzcode.ggserver.game.common.poker.PokerCard;

/**
     扑克玩家基类
 * 
 * @author zai
 * 2019-01-21 20:21:20
 */
public abstract class PokerCardPlayer<C extends PokerCard> extends Player{
	
	/**
	 * 手牌
	 */
	protected List<C> handcards = new ArrayList<>(maxHandcarNum());
	
	/**
	 * 获取最大手牌数量
	 * 
	 * @return
	 * @author zai
	 * 2019-01-22 19:49:37
	 */
	public abstract int maxHandcarNum();
	
	/**
	 * 添加单张手牌
	 * 
	 * @param card
	 * @return
	 * @author zai
	 * 2019-01-22 19:45:46
	 */
	public void addHandcard(C card) {
		this.handcards.add(card);
	};
	
	/**
	 * 清楚手牌
	 * 
	 * @author zai
	 * 2019-01-22 19:50:30
	 */
	public void clearHandcards() {
		this.handcards.clear();
	}
	
	
	/**
	 * 移除指定手牌对象
	 * 
	 * @param card
	 * @author zai
	 * 2019-01-22 19:51:51
	 */
	public boolean removeHandCard(C card) {
		return this.handcards.remove(card);
	}
	
	/**
	 * 移除指定值的手牌
	 * 
	 * @param card
	 * @author zai
	 * 2019-01-22 19:51:51
	 */
	public void removeHandCard(int cardVal) {
		Iterator<C> it = this.handcards.iterator();
		while (it.hasNext()) {
			if (it.next().getValue() == cardVal) {
				it.remove();
			}
		}
	}
	
	/**
	 * 把手牌从小到大排序
	 * 
	 * @author zai
	 * 2019-01-22 20:01:21
	 */
	public void sortHandCardsAsc() {
		this.handcards.sort((a, b) -> {
			return a.getValue() - b.getValue();
		});
	}
	
	/**
	 * 把手牌从大到小排序
	 * 
	 * @author zai
	 * 2019-01-22 20:01:44
	 */
	public void sortHandCardsDesc() {
		this.handcards.sort((a, b) -> {
			return b.getValue() - a.getValue();
		});
	}
	
	/**
	 * 手牌转值list
	 * 
	 * @return
	 * @author zai
	 * 2019-01-25 17:42:08
	 */
	public List<Integer> handcardsToValList() {
		if (handcards == null) {
			return null;
		}
		return handcards.stream().map((card) -> {
			return card.getValue();
		}).collect(Collectors.toList());
	}
	
	/**
	 * 手牌转值数组
	 * 
	 * @param list
	 * @return
	 * @author zai
	 * 2019-01-25 17:40:17
	 */
	public int[] handcardsToValArr() {
		if (handcards == null) {
			return null;
		}
		int[] arr = new int[handcards.size()];
		for (int i = 0; i < handcards.size(); i++) {
			arr[i] = handcards.get(i).getValue();
		}
		return arr;
	}
	
	
	
	
	/**
	 * 获取手牌
	 * 
	 * @return
	 * @author zai
	 * 2019-01-22 19:46:17
	 */
	public List<C> getHandcards() {
		return handcards;
	}
	

}
