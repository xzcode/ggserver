package xzcode.ggserver.game.card.games.player;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import xzcode.ggserver.game.card.games.card.Card;

/**
     牌类玩家接口
 * 
 * @author zai
 * 2019-01-21 20:21:20
 */
public interface ICardPlayer<C extends Card>{
	
	
	/**
	 * 获取最大手牌数量
	 * 
	 * @return
	 * @author zai
	 * 2019-05-27 11:30:09
	 */
	int maxHandcarNum();
	
	/**
	 * 获取手牌
	 * 
	 * @return
	 * @author zai
	 * 2019-05-27 11:28:12
	 */
	List<C> getHandcards();
	
	
	/**
	 * 添加单张手牌
	 * 
	 * @param card
	 * @return
	 * @author zai
	 * 2019-01-22 19:45:46
	 */
	default void addHandcard(C card) {
		this.getHandcards().add(card);
	};
	
	/**
	 * 清楚手牌
	 * 
	 * @author zai
	 * 2019-01-22 19:50:30
	 */
	default void clearHandcards() {
		this.getHandcards().clear();
	}
	
	
	/**
	 * 移除指定手牌对象
	 * 
	 * @param card
	 * @author zai
	 * 2019-01-22 19:51:51
	 */
	default boolean removeHandCard(C card) {
		return this.getHandcards().remove(card);
	}
	
	/**
	 * 移除指定值的手牌
	 * 
	 * @param card
	 * @author zai
	 * 2019-01-22 19:51:51
	 */
	default void removeHandCard(int cardVal) {
		Iterator<C> it = this.getHandcards().iterator();
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
	default void sortHandCardsAsc() {
		this.getHandcards().sort((a, b) -> {
			return a.getValue() - b.getValue();
		});
	}
	
	/**
	 * 把手牌从大到小排序
	 * 
	 * @author zai
	 * 2019-01-22 20:01:44
	 */
	default void sortHandCardsDesc() {
		this.getHandcards().sort((a, b) -> {
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
	default List<Integer> handcardsToValList() {
		if (this.getHandcards() == null) {
			return null;
		}
		return this.getHandcards().stream().map((card) -> {
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
	default int[] handcardsToValArr() {
		List<C> handcards = this.getHandcards();
		if (handcards == null) {
			return null;
		}
		int[] arr = new int[handcards.size()];
		for (int i = 0; i < handcards.size(); i++) {
			arr[i] = handcards.get(i).getValue();
		}
		return arr;
	}
	

}
