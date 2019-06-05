package xzcode.ggserver.game.common.algo.mj.classic;

import java.util.ArrayList;
import java.util.List;


public class AlgoMjTableUtil extends AlgoMjUtil{
	
	public static List<int[]> generateKe() {
		List<int[]> list = new ArrayList<>();
		int[] arr;
		for (int i : ALL_VALUE_LIST) {
			arr = new int[] { i, i, i };
			list.add(arr);
		}
		return list;
	}
	
	public static List<int[]> generateShun() {
		List<int[]> list = new ArrayList<>();
		int[] arr;
		int loopTimes = WAN_TONG_TIAO_VALUE_LIST.size() - 2;
		for (int i = 0; i < loopTimes; i++) {
			Integer val = WAN_TONG_TIAO_VALUE_LIST.get(i);
			arr = new int[] { val, val + 1, val + 2 };
			list.add(arr);
		}
		return list;
	}
	public static List<int[]> generateJiang() {
		List<int[]> list = new ArrayList<>();
		int[] arr;
		for (int i : ALL_VALUE_LIST) {
			arr = new int[] { i, i };
			list.add(arr);
		}
		return list;
	}

	public static void main(String[] args) {
		List<int[]> generateKe = generateKe();
		List<int[]> generateShun = generateShun();
		List<int[]> generateJiang = generateJiang();
		System.out.println(generateKe.size());
		System.out.println(generateShun.size());
		System.out.println(generateJiang.size());
	}
	
}
