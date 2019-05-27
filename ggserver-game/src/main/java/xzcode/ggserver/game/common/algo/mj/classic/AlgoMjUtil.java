package xzcode.ggserver.game.common.algo.mj.classic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * 基本麻将规则工具
 * 
 * @author zai 2018-12-27 18:06:30
 */
public class AlgoMjUtil {
	
	/**
	 * 万开始值
	 */
	private static final int WAN_START = 11;
	/**
	 * 万结束值
	 */
	private static final int WAN_END = 19;
	
	/**
	 * 条开始值
	 */
	private static final int TIAO_START = 31;
	/**
	 * 条结束值
	 */
	private static final int TIAO_END = 39;
	
	/**
	 * 筒开始值
	 */
	private static final int TONG_START = 51;
	/**
	 * 筒结束值
	 */
	private static final int TONG_END = 59;
	
	/**
	 * 字开始值
	 */
	private static final int ZI_START = 71;
	/**
	 * 字结束值
	 */
	private static final int ZI_END = 74;
	
	/**
	 * 箭开始值
	 */
	private static final int JIAN_START = 91;
	/**
	 * 箭结束值
	 */
	private static final int JIAN_END = 93;
	
	/**
	 * 花牌开始值
	 */
	private static final int HUA_START = 111;
	/**
	 * 花牌结束值
	 */
	private static final int HUA_END = 118;

	/**
	 * 万牌麻将值(一到九万)
	 */
	private static final List<Integer> WAN_VALUE_LIST = Arrays.asList(11, 12, 13, 14, 15, 16, 17, 18, 19);

	/**
	 * 条牌麻将值(一到九条)
	 */
	private static final List<Integer> TIAO_VALUE_LIST = Arrays.asList(31, 32, 33, 34, 35, 36, 37, 38, 39);

	/**
	 * 筒牌麻将值(一到九筒)
	 */
	private static final List<Integer> TONG_VALUE_LIST = Arrays.asList(51, 52, 53, 54, 55, 56, 57, 58, 59);

	/**
	 * 字牌麻将值(东南西北)
	 */
	private static final List<Integer> ZI_VALUE_LIST = Arrays.asList(71, 72, 73, 74);
	
	/**
	 * 箭牌麻将值(中发白)
	 */
	private static final List<Integer> JIAN_VALUE_LIST = Arrays.asList(91, 92, 93);

	/**
	 * 花牌麻将值(春夏秋冬 梅兰竹菊)
	 */
	private static final List<Integer> HUA_VALUE_LIST = Arrays.asList(111, 112, 113, 114, 115, 116, 117, 118);
	
	/**
	 * 所有值 LIST集合
	 */
	private static final List<Integer> ALL_VALUE_LIST = createAllValueList();
	
	
	/**
	 * 创建所有值list集合
	 * 
	 * @return
	 * @author zai
	 * 2019-05-27 11:49:32
	 */
	private static List<Integer> createAllValueList() {
		List<Integer> list = new ArrayList<>();
		list.addAll(WAN_VALUE_LIST);
		list.addAll(TIAO_VALUE_LIST);
		list.addAll(TONG_VALUE_LIST);
		list.addAll(ZI_VALUE_LIST);
		list.addAll(JIAN_VALUE_LIST);
		return list;
	}
	

	/**
	 * 带有花牌的麻将集合
	 */
	//private static final List<Mj> FULL_SORTED_MJ_LIST = createFullSortedMjList(true);

	/**
	 * 无花牌的麻将集合
	 */
	//private static final List<Mj> DEFAULT_SORTED_MJ_LIST = createFullSortedMjList(false);

	/**
	 * 判断数值是否是万
	 * 
	 * @param value
	 * @return
	 * @author zai 2018-12-27 19:20:15
	 */
	public static boolean isWan(int value) {
		return WAN_START < value && value <= WAN_END;
	}

	/**
	 * 判断数值是否是条
	 * 
	 * @param value
	 * @return
	 * @author zai 2018-12-27 19:20:15
	 */
	public static boolean isTiao(int value) {

		return TIAO_START <= value && value <= TIAO_END;
	}

	/**
	 * 判断数值是否是筒
	 * 
	 * @param value
	 * @return
	 * @author zai 2018-12-27 19:20:15
	 */
	public static boolean isTong(int value) {
		return TONG_START <= value && value <= TONG_END;
	}

	/**
	 * 判断数值是否是字
	 * 
	 * @param value
	 * @return
	 * @author zai 2018-12-27 19:20:15
	 */
	public static boolean isZi(int value) {
		return ZI_START <= value && value <= ZI_END;
	}
	
	
	/**
	 * 判断数值是否是箭
	 * 
	 * @param value
	 * @return
	 * @author zai 2018-12-27 19:20:15
	 */
	public static boolean isJian(int value) {
		return JIAN_START <= value && value <= JIAN_END;
	}

	/**
	 * 判断数值是否是花
	 * 
	 * @param value
	 * @return
	 * @author zai 2018-12-27 19:20:15
	 */
	public static boolean isHua(int value) {
		return HUA_START <= value && value <= HUA_END;
	}
	
	/**
	 * 获取麻将类型
	 * 
	 * @param value
	 * @return
	 * @author zai
	 * 2018-12-31 15:01:41
	 */
	public static int getMjType(int value) {
		if (isWan(value)) {
			return AlgoMjType.WAN;
		}
		if (isTong(value)) {
			return AlgoMjType.TONG;
		}
		if (isTiao(value)) {
			return AlgoMjType.TIAO;
		}
		if (isZi(value)) {
			return AlgoMjType.ZI;
		}
		if (isJian(value)) {
			return AlgoMjType.JIAN;
		}
		if (isHua(value)) {
			return AlgoMjType.HUA;
		}
		return 0;
	}

	/**
	 * 创建麻将集合
	 * 
	 * @param hasHuapai
	 * @return
	 * @author zai 2018-12-27 18:53:15
	 */
	private static List<AlgoMj> createFullSortedMjList(boolean hasHuapai) {
		List<Integer> valueList = new ArrayList<>();
		for (int i = 0; i < 4; i++) {
			valueList.addAll(WAN_VALUE_LIST);
			valueList.addAll(TIAO_VALUE_LIST);
			valueList.addAll(TONG_VALUE_LIST);
			valueList.addAll(ZI_VALUE_LIST);
			valueList.addAll(JIAN_VALUE_LIST);
		}
		if (hasHuapai) {
			valueList.addAll(HUA_VALUE_LIST);
		}

		Collections.sort(valueList);
		List<AlgoMj> algoMjs = new ArrayList<>(valueList.size());
		AlgoMj algoMj = null;

		for (Integer value : valueList) {
			algoMj = new AlgoMj();
			algoMjs.add(algoMj);
			algoMj.setValue(value);
			algoMj.setType(getMjType(value));
		}

		return algoMjs;
	}

	/**
	 * 获取新的，洗好的麻将牌组
	 * 
	 * @return
	 * @author zai 2018-12-27 18:10:22
	 */
	public static List<AlgoMj> newShuffledMjList(boolean hasHuapai) {
		List<AlgoMj> algoMjs = new ArrayList<>(144);
		if (hasHuapai) {
			algoMjs.addAll(createFullSortedMjList(true));
		} else {
			algoMjs.addAll(createFullSortedMjList(false));
		}
		Collections.shuffle(algoMjs);
		return algoMjs;
	}

	/**
	 * 新的已洗牌麻将（有花牌）
	 * 
	 * @return
	 * @author zai 2018-12-27 18:58:03
	 */
	public static List<AlgoMj> newShuffledHuaMjList() {
		return newShuffledMjList(true);
	}

	/**
	 * 新的已洗牌麻将（无花牌）
	 * 
	 * @return
	 * @author zai 2018-12-27 18:56:40
	 */
	public static List<AlgoMj> newShuffledMjList() {
		return newShuffledMjList(false);
	}

	/**
	 * 判断集合中是否有万
	 * 
	 * @param list
	 * @return
	 * @author zai 2018-12-27 19:21:25
	 */
	public static boolean hasWan(List<AlgoMj> list) {
		for (AlgoMj algoMj : list) {
			if (isWan(algoMj.getValue())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断集合中是否有条
	 * 
	 * @param list
	 * @return
	 * @author zai 2018-12-27 19:21:25
	 */
	public static boolean hasTiao(List<AlgoMj> list) {
		for (AlgoMj algoMj : list) {
			if (isTiao(algoMj.getValue())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断集合中是否有筒
	 * 
	 * @param list
	 * @return
	 * @author zai 2018-12-27 19:21:25
	 */
	public static boolean hasTong(List<AlgoMj> list) {
		for (AlgoMj algoMj : list) {
			if (isTong(algoMj.getValue())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断集合中是否有字
	 * 
	 * @param list
	 * @return
	 * @author zai 2018-12-27 19:21:25
	 */
	public static boolean hasZi(List<AlgoMj> list) {
		for (AlgoMj algoMj : list) {
			if (isZi(algoMj.getValue())) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 判断集合中是否有箭
	 * 
	 * @param list
	 * @return
	 * @author zai 2018-12-27 19:21:25
	 */
	public static boolean hasJian(List<AlgoMj> list) {
		for (AlgoMj algoMj : list) {
			if (isJian(algoMj.getValue())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断集合中是否有花
	 * 
	 * @param list
	 * @return
	 * @author zai 2018-12-27 19:21:25
	 */
	public static boolean hasHua(List<AlgoMj> list) {
		for (AlgoMj algoMj : list) {
			if (isHua(algoMj.getValue())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 顺序排序麻将集合
	 * 
	 * @param list
	 * @author zai 2018-12-28 10:37:44
	 */
	public static void sort(List<? extends AlgoMj> list) {
		Collections.sort(list, (mj1, mj2) -> {
			if (mj1.getValue() > mj2.getValue()) {
				return 1;
			}
			if (mj1.getValue() < mj2.getValue()) {
				return -1;
			}
			return 0;
		});
	}

	/**
	 * 倒序排序麻将集合
	 * 
	 * @param list
	 * @author zai 2018-12-28 10:44:42
	 */
	public static void rSort(List<AlgoMj> list) {
		Collections.sort(list, (mj1, mj2) -> {
			if (mj1.getValue() > mj2.getValue()) {
				return -1;
			}
			if (mj1.getValue() < mj2.getValue()) {
				return 1;
			}
			return 0;
		});
	}
	
	/**
	 * 是否存在坎或顺
	 * 
	 * @param list
	 * @return
	 * @author zai
	 * 2019-05-27 10:39:07
	 */
	public static boolean checkKanShun(List<Integer> list)
    {
    	if(list.size()%3!=0) return false;
    	int k=0;
    	while (k<=list.size()-3) 
    	{
    		int temp=list.get(k);
    		if(list.get(k+2)==temp)
    		{
    			k+=3;
    			continue;
    		}
    		else if (temp<60&&list.contains(temp+1)&&list.contains(temp+2)) 
    		{
    			for(int i=0;i<3;i++)
    			{
    			 list.remove((Integer)(temp+i));
    			}
    			continue;
    		}
    		else {
    				return false;
    			 }
    	}
    	 return true;
    	
    }
	
	/**
	 * 检查是否存在砍或顺
	 * 
	 * @param list
	 * @param lian
	 * @return
	 * @author zai
	 * 2019-05-27 10:37:45
	 */
	public static boolean checkKanShun(List<Integer> list, boolean lian) {
		if (list.size() % 3 != 0)
			return false;
		int k = 0;
		while (k <= list.size() - 3) {
			int temp = list.get(k);
			if (list.get(k + 2) == temp) {
				k += 3;
				continue;
			} else if (temp < 60 && list.contains(temp + 1) && list.contains(temp + 2) && lian) {
				for (int i = 0; i < 3; i++) {
					list.remove((Integer) (temp + i));
				}
				continue;
			} else {
				return false;
			}
		}
		return true;

	}

	public static boolean checkHu(List<Integer> list, Integer check) {
		List<Integer> all = new ArrayList<>(list.size());
		all.addAll(list);
		all.add(check);
		Collections.sort(all);
		if (all.stream().filter(o -> o > 100).collect(Collectors.toList()).size() > 0) {
			return false;
		}
		List<Integer> dis = all.stream().distinct().collect(Collectors.toList());
		for (Integer integer : dis) {
			if (Collections.frequency(all, integer) > 1) {
				List<Integer> temp = new ArrayList<>();
				temp.addAll(all);
				temp.remove(integer);
				temp.remove(integer);
				if (checkKanShun(temp, true)) {
					return true;
				}
			} else
				continue;
		}
		return false;
	}

	/**
	 * 听牌检查
	 * 
	 * @param list
	 * @return 没有则返回null
	 * @author zai
	 * 2019-05-27 10:54:26
	 */
	public static List<Integer> ting(List<Integer> list) {
		List<Integer> result = null;
		for (Integer integer : ALL_VALUE_LIST) {
			if (checkHu(list, integer)) {
				if (result == null) {
					result = new LinkedList<>();
				}
				result.add(integer);
			}
		}
		return result;
	}
	
	
	/**
	 * 是否可胡
	 * 
	 * @param shoupai
	 * @param mopai
	 * @return
	 * @author zai
	 * 2019-01-07 13:33:38
	 */
	public static boolean isCanHu(List<AlgoMj> shoupai, AlgoMj mopai) {
		List<Integer> valList = toValList(shoupai);
		return checkHu(valList, mopai.getValue());
	}
	
	
	/**
	 * 获取可吃类型集合
	 * 
	 * @param list
	 * @param algoMj
	 * @return
	 * @author zai
	 * 2018-12-31 22:57:26
	 */
	public static List<int[]> getChiGroup(List<AlgoMj> list, AlgoMj algoMj) {
		int mjVal = algoMj.getValue();
		if (isZi(mjVal) || isJian(mjVal)) {
			return null;
		}
		if (list.size() < 3) {
			return null;
		}
		
		List<Integer> valList = toDistinctValList(list);
		
		int valL1 = mjVal - 1;
		int valL2 = mjVal - 2;
		int valR1 = mjVal + 1;
		int valR2 = mjVal + 2;
		List<int[]> chiList = new LinkedList<>();
		
		//List<Integer> clist = Arrays.asList(valL1, valL2);
		
		
		if (valList.containsAll(Arrays.asList(valL1, valL2))) {
			chiList.add(new int[]{valL2, valL1, mjVal});
		}
		
		
		if (valList.containsAll(Arrays.asList(valL1, valR1))) {
			chiList.add(new int[]{valL1,mjVal,valR1});
		}
		
		
		
		if (valList.containsAll(Arrays.asList(valR1, valR2))) {
			chiList.add(new int[]{mjVal,valR1,valR2});
		}
		
		
		if (chiList.size() == 0) {
			return null;
		}
		return chiList;
	}
	
	/**
	 * 判断是否可吃
	 * 
	 * @param list
	 * @param algoMj
	 * @return
	 * @author zai
	 * 2018-12-31 22:57:11
	 */
	public static boolean isCanChi(List<AlgoMj> list, AlgoMj algoMj) {
		int mjVal = algoMj.getValue();
		if (isZi(mjVal) || isJian(mjVal)) {
			return false;
		}
		if (list.size() < 3) {
			return false;
		}
		
		List<Integer> valList = toDistinctValList(list);
		
		int valL1 = mjVal - 1;
		int valL2 = mjVal - 2;
		int valR1 = mjVal + 1;
		int valR2 = mjVal + 2;
		List<int[]> chiList = new LinkedList<>();
		
		//List<Integer> clist = Arrays.asList(valL1, valL2);
		
		if (valList.containsAll(Arrays.asList(valL1, valL2))) {
			return true;
		}
		
		if (valList.containsAll(Arrays.asList(valR1, valR2))) {
			return true;
		}
		
		if (valList.containsAll(Arrays.asList(valL1, valR1))) {
			return true;
		}
		return false;
		
	}
	
	
	public static boolean isCanPeng(List<AlgoMj> list, AlgoMj algoMj) {
		if (list == null || list.size() < 3) {
			return false;
		}
		int[] arr = toValArr(list);
		int mjVal = algoMj.getValue();
		for (int i = 0; i < arr.length; i++) {
			if (i + 2 > arr.length) {
				return false;
			}
			if (arr[i] == mjVal && arr[i+1] == mjVal) {
				return true;
			}
			
		}
		return false;
	}
	
	/**
	 * 是否可杠牌
	 * 
	 * @param list
	 * @param algoMj
	 * @return
	 * @author zai
	 * 2018-12-31 23:29:36
	 */
	public static boolean isCanGang(List<AlgoMj> list, AlgoMj algoMj) {
		/*if (list == null || list.size() < 5) {
			return false;
		}*/
		int[] arr = toValArr(list);
		Arrays.sort(arr);
		int mjVal = algoMj.getValue();
		
		for (int i = 0; i < arr.length; i++) {
			if (i + 3 > arr.length) {
				return false;
			}
			if (arr[i] == arr[i+2] && mjVal == arr[i]) {
				return true;
			}
			
		}
		return false;
	}
	
	/**
	 * 是否可以暗杠
	 * 
	 * @param list
	 * @return
	 * @author zai
	 * 2018-12-31 23:17:35
	 */
	public static boolean isCanAnGang(List<AlgoMj> list, AlgoMj targetMj) {
		/*if (list == null || list.size() < 5) {
			return false;
		}*/
		List<AlgoMj> list2 = new ArrayList<>(list);
		list2.add(targetMj);
		int[] arr = toValArr(list2);
		Arrays.sort(arr);
		for (int i = 0; i < arr.length; i++) {
			if (i + 4 > arr.length) {
				return false;
			}
			if (arr[i] == arr[i + 3]) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 获取暗杠组合
	 * 
	 * @param list
	 * @param targetMj
	 * @return
	 * @author zai
	 * 2019-01-12 18:53:04
	 */
	public static List<Integer> getAnGangGroup(List<AlgoMj> list, AlgoMj targetMj) {
		
		List<AlgoMj> list2 = new ArrayList<>(list);
		list2.add(targetMj);
		int[] arr = toValArr(list2);
		Arrays.sort(arr);
		List<Integer> angangGroup = null;
		for (int i = 0; i < arr.length; i++) {
			if (i + 4 > arr.length) {
				return angangGroup;
			}
			if (arr[i] == arr[i + 3]) {
				if (angangGroup == null) {
					angangGroup = new LinkedList<>();
				}
				angangGroup.add(arr[i]);
			}
		}
		return angangGroup;
	}
	
	/**
	 * 检测是否可碰杠
	 * 
	 * @param shoupai
	 * @param operations
	 * @param targetMj
	 * @return
	 * @author zai
	 * 2019-01-16 10:59:28
	 */
	public static boolean isCanPengGang(List<? extends AlgoMj> shoupai, List<? extends AlgoMjOperation<AlgoMj, AlgoMjPlayer>> operations, AlgoMj targetMj) {
		
		List<Integer> valList = toDistinctValList(shoupai);
		if (targetMj != null) {
			valList.add(targetMj.getValue());
		}
		
		for (Integer mjVal : valList) {
			for (AlgoMjOperation<AlgoMj, AlgoMjPlayer> oper : operations) {
				if (oper.getType() == AlgoMjOperType.PENG && mjVal == oper.getTargetMjValue()) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * 获取碰杠可选组合
	 * 
	 * @param shoupai
	 * @param operations
	 * @param targetMj
	 * @return
	 * @author zai
	 * 2019-01-16 11:05:12
	 */
	public static List<Integer> getPengGangGroup(List<AlgoMj> shoupai, List<AlgoMjOperation<AlgoMj, AlgoMjPlayer>> operations, AlgoMj targetMj) {
		List<AlgoMj> list = new ArrayList<>();
		list.addAll(shoupai);
		if (targetMj != null) {
			list.add(targetMj);
		}
		//去重
		List<Integer> valList = toDistinctValList(list);
		
		List<Integer> groups = new LinkedList<>(); 
		for (Integer mjVal : valList) {
			for (AlgoMjOperation<AlgoMj, AlgoMjPlayer> oper : operations) {
				if (oper.getType() == AlgoMjOperType.PENG && mjVal == oper.getTargetMjValue()) {
					if (groups == null) {
						groups = new LinkedList<>();
					}
					groups.add(mjVal);
					break;
				}
			}
		}
		return groups;
	}

	/**
	 * 移除四个相同的牌
	 * 
	 * @param arr
	 * @return
	 * @author zai
	 * 2018-12-31 13:20:18
	 */
	public static int[] remove4n(int[] arr) {
		if (arr.length < 4) {
			return arr;
		}
		
		for (int i = 0; i < arr.length; i++) {
			if (i + 4 > arr.length) {
				return arr;
			}
			int cur1 = arr[i];
			int cur2 = arr[i +1];
			int cur3 = arr[i +2];
			int cur4 = arr[i +3];
			if (cur1 == cur2 && cur2 == cur3 && cur3 == cur4) {
				int[] arrNew = new int[arr.length - 4];
				for (int j = 0, k = 0; j < arr.length; j++) {
					if (j < i || j > i +3) {
						arrNew[k] =  arr[j];						
						k++;
					}
				}
				return remove4n(arrNew);
			}

		}
		return arr;
	}
	
	/**
	 * 移除坎牌和顺牌
	 * 
	 * @param arr
	 * @return
	 * @author zai
	 * 2018-12-31 14:11:07
	 */
	public static int[] remove3n(int[] arr) {
		if (arr.length < 3) {
			return arr;
		}
		// 如果 开始两个牌是不是顺或对，不能胡
		if (!isStartWidthDuiOrShun(arr)) {
			return arr;
		}
		for (int i = 0; i < arr.length; i++) {
			if (i + 3 > arr.length) {
				return arr;
			}
			int cur1 = arr[i];
			int cur2 = arr[i +1];
			int cur3 = arr[i +2];
			
			
			if (
					(cur1 == cur2 - 1 && cur2 == cur3 - 1 ) //判断是不是顺
					||
					(cur1 == cur2 && cur2 == cur3 ) //判断是不是坎
				) {
				int[] arrNew = new int[arr.length - 3];
				for (int j = 0, k = 0; j < arr.length; j++) {
					if (j < i || j > i + 2) {
						arrNew[k] =  arr[j];						
						k++;
					}
				}
				return remove3n(arrNew);
			}
		}
		
		return arr;

	}

	/**
	 * 移除对子
	 * 
	 * @param arr
	 * @return
	 * @author zai
	 * 2018-12-31 14:10:01
	 */
	public static int[] removeDui(int[] arr) {
		for (int i = 0; i < arr.length; i++) {
			
			if (i + 1 < arr.length) {
				
				if (arr[i] == arr[i + 1]) {
					
					if (i + 2 < arr.length) {
						
						if (arr[i + 1] != arr[i + 2]) {
							int[] arrNew = new int[arr.length - 2];
							for (int j = 0, k = 0; j < arr.length; j++) {
								if (arr[i] != arr[j]) {
									arrNew[k] =  arr[j];						
									k++;
								}
							}
							return removeDui(arrNew);
						}else {
							i += 2;
						}
						
					}else {
						return Arrays.copyOfRange(arr, 0, arr.length - 2);
					}
					
				}
			}
			
		}
		return arr;
	}

	// 判断开始两个牌是不是顺或对
	public static boolean isStartWidthDuiOrShun(int[] arr) {
		if (arr[0] == arr[1] || arr[0] == arr[1] - 1) {
			return true;
		}
		return false;
	}

	/**
	 * 排序并转换为int数组
	 * 
	 * @param list
	 * @return
	 * @author zai
	 * 2018-12-31 13:39:24
	 */
	public static int[] sortAndToValArr(List<? extends AlgoMj> list) {
		sort(list);
		return toValArr(list);
	}

	/**
	 * 麻将集合转数组
	 * 
	 * @param list
	 * @return
	 * @author zai 2018-12-30 21:21:54
	 */
	public static int[] toValArr(List<? extends AlgoMj> list) {
		int[] arr = new int[list.size()];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = list.get(i).getValue();
		}
		return arr;
	}
	/**
	 * 转换为去重的值数组
	 * 
	 * @param list
	 * @return
	 * @author zai
	 * 2019-01-12 18:05:24
	 */
	public static int[] toDistinctValArr(List<? extends AlgoMj> list) {
		if (list == null) {
			return null;
		}
		Set<Integer> set = list.stream().map((mj) -> {
			return mj.getValue();
		}).collect(Collectors.toSet());
		List<Integer> valList = new ArrayList<>(set.size());
		valList.addAll(set);
		
		int[] arr = new int[set.size()];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = valList.get(i);
		}
		Arrays.sort(arr);
		return arr;
	}
	
	/**
	 * 转换为去重的值list
	 * 
	 * @param list
	 * @return
	 * @author zai
	 * 2019-01-12 18:11:33
	 */
	public static List<Integer> toDistinctValList(List<? extends AlgoMj> list) {
		if (list == null) {
			return null;
		}
		Set<Integer> set = list.stream().map((mj) -> {
			return mj.getValue();
		}).collect(Collectors.toSet());
		List<Integer> valList = new ArrayList<>(set.size());
		valList.addAll(set);
		Collections.sort(valList);
		return valList;
	}
	
	/**
	 * 数值集合转数组
	 * 
	 * @param list
	 * @return
	 * @author zai
	 * 2019-01-07 13:32:14
	 */
	public static int[] valListToArr(List<Integer> list) {
		int[] arr = new int[list.size()];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = list.get(i).intValue();
		}
		return arr;
	}

	/**
	 * 转换为值list
	 * 
	 * @param list
	 * @return
	 * @author zai
	 * 2019-01-06 15:04:53
	 */
	public static List<Integer> toValList(List<? extends AlgoMj> list) {
		if (list == null) {
			return null;
		}
		return list.stream().map((mj) -> {
			return mj.getValue();
		}).collect(Collectors.toList());
	}
	
	
	/**
	 * 获取吃牌限制
	 * 
	 * @param list
	 * @return
	 * @author zai
	 * 2019-01-14 10:28:58
	 */
	public static List<Integer> getChiLimit(List<int[]> chiGroups, List<Integer> chosenChiOption, Integer targetVal) {
		if (chiGroups == null) {
			return null;
		}
		List<Integer> limitVals = new LinkedList<>();
		limitVals.add(targetVal);
		for (int[] group : chiGroups) {
			List<Integer> asList = Arrays.asList(group[0], group[1], group[2]);
			Collections.sort(asList);
			if (chosenChiOption.containsAll(asList)) {
				if (asList.indexOf(targetVal) == 0) {
					limitVals.add(asList.get(2) + 1);
				}else if (asList.indexOf(targetVal) == 2){
					limitVals.add(asList.get(0) - 1);
				}
			}
		}
		return limitVals;
		
		
	}
	
	/**
	 * 获取全0 list集合
	 * 
	 * @param list
	 * @return
	 * @author zai
	 * 2019-01-06 15:08:27
	 */
	public static List<Integer> toZeroValList(List<AlgoMj> list) {
		return list.stream().map((mj) -> {
			return 0;
		}).collect(Collectors.toList());
	}

}
