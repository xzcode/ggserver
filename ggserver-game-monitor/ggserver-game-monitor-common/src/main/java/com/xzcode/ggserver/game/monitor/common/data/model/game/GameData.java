package com.xzcode.ggserver.game.monitor.common.data.model.game;

import java.util.List;

import com.xzcode.ggserver.game.monitor.common.data.model.house.HouseData;

/**
 * 游戏数据
 *
 * @author zai
 * 2020-04-23 10:07:10
 */
public class GameData {
	
	/**
	 * 游戏名称
	 */
	protected String gameName;
	
	/**
	 * 游戏编号
	 */
	protected String gameNo;
	
	/**
	 * 子大厅信息
	 */
	protected List<HouseData> houseDatas;

	public String getGameName() {
		return gameName;
	}

	public void setGameName(String gameName) {
		this.gameName = gameName;
	}

	public String getGameNo() {
		return gameNo;
	}

	public void setGameNo(String gameNo) {
		this.gameNo = gameNo;
	}

	public List<HouseData> getHouseDatas() {
		return houseDatas;
	}

	public void setHouseDatas(List<HouseData> houseDatas) {
		this.houseDatas = houseDatas;
	}
	
	

}
