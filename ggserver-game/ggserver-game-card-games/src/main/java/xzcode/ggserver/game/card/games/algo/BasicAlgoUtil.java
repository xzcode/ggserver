package xzcode.ggserver.game.card.games.algo;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.commons.lang3.ArrayUtils;


/**
 * 基本算法工具
 * 
 * @author zai 2018-12-27 18:06:30
 */
public class BasicAlgoUtil {
	
	
	public static final BigInteger BIG_INTEGER_ONE = new BigInteger("1");
	public static final BigInteger BIG_INTEGER_ZERO = new BigInteger("0");
	
	/**
	 * 从小到大排序int[]
	 * 
	 * @param arr
	 * @author zai
	 * 2019-05-28 15:29:59
	 */
	public  void sort(int[] arr) {
		Arrays.sort(arr);
	}
	
	
	/**
	 * 从大到小排序int[]
	 * 
	 * @param arr
	 * @author zai
	 * 2019-05-28 15:30:12
	 */
	public void rSort(int[] arr) {
		Arrays.sort(arr);
		ArrayUtils.reverse(arr);
	}
	
	/**
	 * 去重
	 * 
	 * @param arr
	 * @return
	 * @author zai
	 * 2019-05-28 16:40:01
	 */
	public int[] distinct(int[] arr) {
		int count = 0;
		int[] arr1 = new int[arr.length];
		boolean flag = false;
		for (int i = 0; i < arr.length; i++) {
			flag = true;
			for (int o : arr1) {
				if (o == arr[i]) {
					flag = false;
					break;
				}
			}
			if (flag) {
				arr1[count] = arr[i];
				count++;				
			}
		}
		return Arrays.copyOf(arr1, count);
	}
	/**
	 * 去重并获取元素个数
	 * 
	 * @param arr
	 * @return
	 * @author zai
	 * 2019-05-28 18:20:52
	 */
	public int distinctAndCount(int[] arr) {
		int count = 0;
		int[] arr1 = new int[arr.length];
		boolean flag = false;
		for (int i = 0; i < arr.length; i++) {
			flag = true;
			for (int o : arr1) {
				if (o == arr[i]) {
					flag = false;
					break;
				}
			}
			if (flag) {
				arr1[count] = arr[i];
				count++;				
			}
		}
		return count;
	}
	
	/**
	 * 获取相同元素的集合
	 * 
	 * @param srcList
	 * @param minElementSize 指定最少元素个数，少于指定个数的元素将会忽略
	 * @return
	 * @author zai
	 * 2019-05-28 19:02:50
	 */
	public Map<Integer, List<Integer>> getSameElemenets(List<Integer> srcList, Integer minElementSize) {
		Map<Integer, List<Integer>> map = new LinkedHashMap<>(10);
		List<Integer> tmp = null;
		for (int i = 0; i < srcList.size(); i++) {
			int value = srcList.get(i);
			tmp = map.get(value);
			if (tmp == null) {
				tmp = new ArrayList<>(4);
				map.put(value, tmp);
			}
			tmp.add(value);
			
		}
		if (minElementSize != null) {
			Iterator<Entry<Integer, List<Integer>>> it = map.entrySet().iterator();
			while (it.hasNext()) {
				List<Integer>  next = it.next().getValue();
				if (next.size() < minElementSize) {
					it.remove();
				}
			}
		}
		return map;
	}
	
	
	public List<Integer> intArrToList(int[] srcArr) {
		List<Integer> list = new ArrayList<>(srcArr.length);
		for (int i : srcArr) {
			list.add(i);
		}
		return list;
	}
	
	public int[] listToIntArr(List<Integer> srcList) {
		if (srcList == null) {
			return null;
		}
		int[] arr = new int[srcList.size()];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = srcList.get(i);
		}
		return arr;
	}
	
	/**
	 * 移动位置
	 * 
	 * @param list
	 * @param offset
	 * @author zai
	 * 2019-06-03 16:30:11
	 * @param <T>
	 */
	@SuppressWarnings("unchecked")
	public <T> void shift(List<T> list, int offset) {
		if (offset == 0) {
			return;
		}
		Object[] array = list.toArray();
		ArrayUtils.shift(array, offset);
		list.clear();
		for (Object object : array) {
			list.add((T) object);
		}
	}
	
	/**
	 * 移动位置
	 * 
	 * @param array
	 * @param offset
	 * @author zai
	 * 2019-06-03 16:30:21
	 */
	public void shift(int[] array, int offset) {
		ArrayUtils.shift(array, offset);
	}
	
	/**
	 * 获取目标值的与原始数组的顺序组合
	 * 
	 * @param srcArr 原始数字
	 * @param target 目标值
	 * @param comboLen 期望产生的组合长度
	 * @return
	 * @author zai
	 * 2019-05-30 16:18:23
	 */
	public List<int[]> getStraightCombo(int[] srcArr, int target, int comboLen) {
		List<int[]> createStraightCombos = createStraightCombos(target, comboLen);
		List<int[]> list = new ArrayList<>(createStraightCombos.size());
		for (int i = 0; i < createStraightCombos.size(); i++) {
			int[] arr = createStraightCombos.get(i);
			boolean flag = true;
			for (int j = 0; j < arr.length; j++) {
				boolean flag2 = false;
				for (int k = 0; k < srcArr.length; k++) {
					if (srcArr[k] == arr[j]) {
						flag2 = true;
						break;
					}
				}
				if (!flag2) {
					flag = false;
					break;
				}
			}
			if (flag) {
				list.add(arr);
			}
		}
		return list;
	}
	
	
	public List<List<Integer>> createStraightComboList(int target, int comboLen) {
		List<List<Integer>> list = new ArrayList<>(comboLen);
		List<Integer> tmp = null;
		int min = target - comboLen + 1;
		for (int i = 0; i < comboLen; i++) {
			tmp = new ArrayList<>(comboLen);
			int len = target + i + 1;
			for (int j = min + i; j < len; j++) {
				tmp.add(j);
			}
			list.add(tmp);
		}
		return list;
	}
	
	/**
	 * 创建顺序组合集合
	 * 
	 * @param target
	 * @param comboLen
	 * @return
	 * @author zai
	 * 2019-06-01 23:33:50
	 */
	public List<int[]> createStraightCombos(int target, int comboLen) {
		List<int[]> list = new ArrayList<>(comboLen);
		int[] tmp = null;
		int min = target - comboLen + 1;
		for (int i = 0; i < comboLen; i++) {
			tmp = new int[comboLen];
			for (int j = min + i, k = 0; k < comboLen; j++,k++) {
				tmp[k] = j;
			}
			list.add(tmp);
		}
		return list;
	}
	
	/**
	 * 转为去重数组
	 * 
	 * @param list
	 * @return
	 * @author zai
	 * 2019-06-02 00:07:43
	 */
	public int[] toDistinctArr(List<int[]> list) {
		int count = 0;
		for (int[] iarr : list) {
			count += iarr.length;
		}
		int[] arr = new int[count];
		count = 0;
		for (int[] iarr : list) {
			for (int i : iarr) {
				arr[count] = i;	
				count++;
			}
		}
		
		return distinct(arr);
	}
	
	
	/**
	 * 计算 C(n,m)个数 
	 * 
	 * @param n
	 * @param m
	 * @return
	 * @author zai
	 * 2019-05-31 16:25:23
	 */
    public int combination(int n, int m) {
        if (m > n)
            return 0; // 如果总数小于取出的数，直接返回0

        int k = 1;
        int j = 1;
        // 该种算法约掉了分母的(m-n)!,这样分子相乘的个数就是有n个了
        for (int i = m; i >= 1; i--) {
            k = k * m;
            j = j * n;
            m--;
            n--;
        }
        return j / k;
    }
    
    /**
     * 计算 C(n,m)个数
     * 
     * @param n
     * @param m
     * @return
     * @author zai
     * 2019-06-01 20:02:08
     */
    public long combination(long n, long m) {
        if (m > n)
            return 0; // 如果总数小于取出的数，直接返回0

        long k = 1;
        long j = 1;
        // 该种算法约掉了分母的(m-n)!,这样分子相乘的个数就是有n个了
        for (long i = m; i >= 1; i--) {
            k = k * m;
            j = j * n;
            m--;
            n--;
        }
        return j / k;
    }
    
    /**
     * 计算 C(n,m)个数(针对超大结果)
     * 
     * @param n 总数
     * @param m 选取个数
     * @return
     * @author zai
     * 2019-06-01 20:09:33
     */
    public BigInteger combinationBig(long n, long m) {
        if (m > n) {
        	return BIG_INTEGER_ZERO; 
        }
        
        BigInteger mm = null;
        BigInteger nn = null;

        long k = 1;
        long j = 1;
        
        //是否大数值标识，false使用long类型计算以提高性能，true超大数值转换为biginteger
        boolean isBig = false;
        
        BigInteger kb = null;
        BigInteger jb = null;
        
        
        for (long i = m; i >= 1; i--) {
        	
        	if (isBig) {
        		kb = kb.multiply(mm);
                jb = jb.multiply(nn);
                
                mm = mm.subtract(BIG_INTEGER_ONE);
                nn = nn.subtract(BIG_INTEGER_ONE);
			}else {
				long tmp = j * n;
				if (tmp < 0) {
					//如果数值已溢出，转换为biginteger计算
					isBig = true;
					kb = new BigInteger(String.valueOf(k));
					jb = new BigInteger(String.valueOf(j));
							
					mm = new BigInteger(String.valueOf(m));
			        nn = new BigInteger(String.valueOf(n));
			        i++;
			        continue;
				}
				
				k = k * m;
	            j = tmp;
	            m--;
	            n--;
			}
        }
        if (isBig) {
        	return jb.divide(kb);			
		}
        return new BigInteger(String.valueOf(j/k));
    }

	/**
	 * 输出数组信息
	 * 
	 * @param arr
	 * @author zzz
	 * 2019-09-22 12:17:08
	 */
	public void printArr(int[] arr) {
		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i]);
			if (i < arr.length - 1) {
				System.out.print(" ,");
			}
		}
		System.out.println();
	}
	
	/**
	 * 数组拼接
	 * 
	 * @param arr
	 * @return
	 * @author zzz
	 * 2019-09-22 12:17:17
	 */
	public String concatArr(int[] arr) {
		StringBuilder sb = new StringBuilder(arr.length + arr.length - 1);
		for (int i = 0; i < arr.length; i++) {
			sb.append(arr[i]);
			if (i < arr.length - 1) {
				sb.append(" ,");
			}
		}
		return sb.toString();
	}
	
    
    public static void main(String[] args) throws Exception {
    	BasicAlgoUtil util = new BasicAlgoUtil();
    	int len = 100 * 10000;
    	//len = 10;
		List<int[]> list = new ArrayList<>(len);
		int arrLen = 17;
		for (int i = 0; i < len; i++) {
			int[] arr = new int[arrLen];
			for (int j = 0; j < arrLen; j++) {
				arr[j] = ThreadLocalRandom.current().nextInt(1, 53);
			}
			list.add(arr);
		}
		long startMs = 0;
		long endMs = 0;
		
		
		int[] srcArr = new int[]{1,1,1,3,4,5,6,7,8,9,9,9};
		List<Integer> srcList = new ArrayList<>();
		for (Integer i : srcArr) {
			srcList.add(i);
		}
		int target = 5;
		int comboLen = 3;
		
		startMs = System.currentTimeMillis();
		for (int[] arr : list) {
			Arrays.sort(arr);
		}
		endMs = System.currentTimeMillis() - startMs;
		System.out.println("Arrays.sort time:"+endMs + " ms");
		
		
		startMs = System.currentTimeMillis();
		for (int[] arr : list) {
			util.sort(arr);
		}
		endMs = System.currentTimeMillis() - startMs;
		System.out.println("sortIntArrayAsc time:"+ endMs + " ms");
		
		
		
		startMs = System.currentTimeMillis();
		for (int[] arr : list) {
			util.rSort(arr);
		}
		endMs = System.currentTimeMillis() - startMs;
		System.out.println("sortIntArrayDesc time:"+ endMs + " ms");
		
		
		startMs = System.currentTimeMillis();
		for (int[] arr : list) {
			
			util.distinct(arr);
			//printArr(distinct(arr));
		}
		endMs = System.currentTimeMillis() - startMs;
		System.out.println("distinct time:"+ endMs + " ms");
		
		startMs = System.currentTimeMillis();
		for (int[] arr : list) {
			
			util.distinctAndCount(arr);
		}
		endMs = System.currentTimeMillis() - startMs;
		System.out.println("distinctAndCount time:"+ endMs + " ms");
		
		
		startMs = System.currentTimeMillis();
		for (int i = 0; i < len; i++) {
			util.getSameElemenets(srcList, 3);
		}
		endMs = System.currentTimeMillis() - startMs;
		System.out.println("getSameElemenets time:"+ endMs + " ms");
		
		
		
		
		List<int[]> createStraightCombos = util.createStraightCombos(target, comboLen);
		String cb3string = "";
		for (int[] is : createStraightCombos) {
			cb3string +="[" + util.concatArr(is) + "],";
		}
		System.out.println("createStraightCombos :"+ cb3string + "");
		startMs = System.currentTimeMillis();
		for (int i = 0; i < len; i++) {
			util.createStraightCombos(5, 4);			
		}
		endMs = System.currentTimeMillis() - startMs;
		System.out.println("createStraightCombos time:"+ endMs + "ms");
		
		
		
		List<int[]> getStraightCombo = util.getStraightCombo(srcArr,target, comboLen);
		String getStraightComboStr = "";
		for (int[] is : getStraightCombo) {
			getStraightComboStr +="[" +util.concatArr(is) + "],";
		}
		System.out.println("getStraightCombo :"+ getStraightComboStr + "");
		startMs = System.currentTimeMillis();
		for (int i = 0; i < len; i++) {
			util.getStraightCombo(srcArr,target, comboLen);			
		}
		endMs = System.currentTimeMillis() - startMs;
		System.out.println("getStraightCombo time:"+ endMs + "ms");
		
		
		
		startMs = System.currentTimeMillis();
		for (int i = 0; i < len; i++) {
			util.combination(54, 13);
		}
		endMs = System.currentTimeMillis() - startMs;
		System.out.println("combination :"+ endMs + " ms");
		
		
		startMs = System.currentTimeMillis();
		for (int i = 0; i < len; i++) {
			util.combination(32L, 13L);
		}
		endMs = System.currentTimeMillis() - startMs;
		System.out.println("combination long :"+ endMs + " ms");
		
		
		startMs = System.currentTimeMillis();
		for (int i = 0; i < len; i++) {
			util.combinationBig(32, 13);			
		}
		endMs = System.currentTimeMillis() - startMs;
		System.out.println("combinationBig :"+ endMs + " ms");
		
		startMs = System.currentTimeMillis();
		for (int i = 0; i < len; i++) {
			util.shift(srcList, 5);			
		}
		endMs = System.currentTimeMillis() - startMs;
		System.out.println("shift :"+ endMs + " ms");
		
		startMs = System.currentTimeMillis();
		for (int i = 0; i < len; i++) {
			util.shift(srcArr, 5);
		}
		endMs = System.currentTimeMillis() - startMs;
		System.out.println("shift int[] :"+ endMs + " ms");
		
		
		System.out.println();
		System.out.println(util.combination(13, 5) * util.combination(8, 5) * 1);
		
		int[] aa = new int[] {2,3,5,4,1};
		util.rSort(aa);
		for (int i : aa) {
			System.out.print(i + " ,");
		}
		
	}
	
	
	

}
