package xzcode.ggserver.game.card.games.algo.mj.classic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AlgoMjTableUtil extends AlgoMjUtil {

	/**
	 * 生成刻组合
	 * 
	 * @return
	 * 
	 * @author zai 2019-06-09 14:26:20
	 */
	public static List<int[]> generateKe() {
		List<int[]> list = new ArrayList<>();
		int[] arr;
		for (int i : ALL_VALUE_LIST) {
			arr = new int[] { i, i, i };
			list.add(arr);
		}
		return list;
	}

	/**
	 * 生成顺 组合
	 * 
	 * @return
	 * 
	 * @author zai 2019-06-09 14:26:49
	 */
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

	/**
	 * 生成将牌组合
	 * 
	 * @return
	 * 
	 * @author zai 2019-06-09 14:26:59
	 */
	public static List<int[]> generateJiang() {
		List<int[]> list = new ArrayList<>();
		int[] arr;
		for (int i : ALL_VALUE_LIST) {
			arr = new int[] { i, i };
			list.add(arr);
		}
		return list;
	}

	public static List<List<int[]>> generateHuCombos(int handcardSize) {
		Map<String, Object> huMap = new HashMap<>();
		List<int[]> keList = generateKe();
		List<int[]> shunList = generateShun();
		List<int[]> jiangList = generateJiang();

		List<List<int[]>> list = new ArrayList<>();

		// 计算刻与顺的总和
		int keShunNum = (handcardSize - 2) / 3;

		for (int i = 0, keNum = keShunNum, shunNum = 0; i < jiangList.size(); i++, keNum--, shunNum++) {

			int[] jiang = jiangList.get(i);

			List<int[]> keCombo = null;

			for (int j = 0; j < keList.size(); j++) {
				if (keCombo == null) {
					keCombo = new ArrayList<>();
					keCombo.add(jiang);
				}
				for (int k = j; k < keNum + j && k < keList.size(); k++) {
					keCombo.add(keList.get(k));
				}

				if (keCombo.size() == keShunNum + 1) {
					list.add(keCombo);
					keCombo = null;
					continue;
				}

				if (j >= keList.size() - keNum) {
					break;
				}

			}

			for (int j = 0; j < shunList.size(); j++) {
				List<int[]> shunCombo = new ArrayList<>();
				if (keCombo != null) {
					shunCombo.addAll(keCombo);
				}
				for (int k = j; k < shunNum + j  && k < shunList.size(); k++) {
					shunCombo.add(shunList.get(k));
				}

				if (shunCombo.size() == keShunNum + 1) {
					list.add(shunCombo);
				}

				if (j >= shunList.size() - shunNum) {
					break;
				}

			}

		}

		return list;
	}

	public static void addIntArrToList(List<Integer> dest, int[]... src) {
		for (int[] is : src) {
			for (int i : is) {
				dest.add(i);
			}
		}

	}

	public static void main(String[] args) {
		List<int[]> generateKe = generateKe();
		List<int[]> generateShun = generateShun();
		List<int[]> generateJiang = generateJiang();
		List<List<int[]>> generateHuCombos = generateHuCombos(14);
		System.out.println(generateKe.size());
		System.out.println(generateShun.size());
		System.out.println(generateJiang.size());
		System.out.println(generateHuCombos.size());

		System.out.println();
	}

}
