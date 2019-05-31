package xzcode.ggserver.game.common.algo;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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
	public static void sort(int[] arr) {
		Arrays.sort(arr);
	}
	
	/**
	 * 从大到小排序int[]
	 * 
	 * @param arr
	 * @author zai
	 * 2019-05-28 15:30:12
	 */
	public static void rSort(int[] arr) {
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
	public static int[] distinct(int[] arr) {
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
	public static int distinctAndCount(int[] arr) {
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
	 * @param arr
	 * @param minElementSize 指定最少元素个数，少于指定个数的元素将会忽略
	 * @return
	 * @author zai
	 * 2019-05-28 19:02:50
	 */
	public static Map<Integer, List<Integer>> getSameElemenets(int[] arr, Integer minElementSize) {
		Map<Integer, List<Integer>> map = new HashMap<>(arr.length);
		List<Integer> list = null;
		for (int i = 0; i < arr.length; i++) {
			list = map.get(arr[i]);
			if (list == null) {
				list = new ArrayList<>();
			}
			list.add(arr[i]);
		}
		if (minElementSize != null) {
			 Iterator<Entry<Integer, List<Integer>>> it = map.entrySet().iterator();
			 while (it.hasNext()) {
				 if (it.next().getValue().size() < minElementSize) {
					 it.remove();
				 }
			}
		}
		return map;
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
	public static List<List<Integer>> getStraightCombo(int[] srcArr, int target, int comboLen) {
		List<List<Integer>> list = new ArrayList<>(srcArr.length);
		List<Integer> temp = null;
		for (int i = 0; i < srcArr.length; i++) {
			
		}
		return list;
	}
	
	public static List<List<Integer>> getStraightCombo(int target, int comboLen) {
		List<List<Integer>> list = new ArrayList<>(comboLen);
		return list;
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
    public static int combination(int n, int m) {
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
    
    public static long combination(long n, long m) {
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
    
    
    public static BigInteger combinationBig(int n, int m) {
        if (m > n) {
        	return BIG_INTEGER_ZERO; // 如果总数小于取出的数，直接返回0        	
        }
        
        BigInteger mm = new BigInteger(String.valueOf(m));
        BigInteger nn = new BigInteger(String.valueOf(n));

        BigInteger k = BIG_INTEGER_ONE;
        BigInteger j = BIG_INTEGER_ONE;
        // 该种算法约掉了分母的(m-n)!,这样分子相乘的个数就是有n个了
        for (int i = m; i >= 1; i--) {
            k = k.multiply(mm);
            j = j.multiply(nn);
            mm = mm.subtract(BIG_INTEGER_ONE);
            nn = nn.subtract(BIG_INTEGER_ONE);
        }
        return j.divide(k);
    }

	
	public static void printArr(int[] arr) {
		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i]);
			if (i < arr.length - 1) {
				System.out.print(" ,");
			}
		}
		System.out.println();
	}
	
    
    public static void main(String[] args) throws Exception {
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
		
		startMs = System.currentTimeMillis();
		for (int[] arr : list) {
			Arrays.sort(arr);
		}
		endMs = System.currentTimeMillis() - startMs;
		System.out.println("Arrays.sort time:"+endMs + " ms");
		
		
		startMs = System.currentTimeMillis();
		for (int[] arr : list) {
			sort(arr);
		}
		endMs = System.currentTimeMillis() - startMs;
		System.out.println("sortIntArrayAsc time:"+ endMs + " ms");
		
		
		
		startMs = System.currentTimeMillis();
		for (int[] arr : list) {
			rSort(arr);
		}
		endMs = System.currentTimeMillis() - startMs;
		System.out.println("sortIntArrayDesc time:"+ endMs + " ms");
		
		
		startMs = System.currentTimeMillis();
		for (int[] arr : list) {
			
			distinct(arr);
			//printArr(distinct(arr));
		}
		endMs = System.currentTimeMillis() - startMs;
		System.out.println("distinct time:"+ endMs + " ms");
		
		startMs = System.currentTimeMillis();
		for (int[] arr : list) {
			
			distinctAndCount(arr);
		}
		endMs = System.currentTimeMillis() - startMs;
		System.out.println("distinctAndCount time:"+ endMs + " ms");
		
		
		startMs = System.currentTimeMillis();
		for (int[] arr : list) {
			
			getSameElemenets(arr, null);
		}
		endMs = System.currentTimeMillis() - startMs;
		System.out.println("getSameElemenets time:"+ endMs + " ms");
		
		
		startMs = System.currentTimeMillis();
		for (int[] arr : list) {
			
			getStraightCombo(arr, 3, 1);
		}
		endMs = System.currentTimeMillis() - startMs;
		System.out.println("getStraightCombo time:"+ endMs + " ms");
		
		
		
		startMs = System.currentTimeMillis();
		for (int[] arr : list) {
			combination(10, 2);
		}
		endMs = System.currentTimeMillis() - startMs;
		System.out.println("combination :"+ endMs + " ms");
		
		
		startMs = System.currentTimeMillis();
		long ccclong = combination(13L, 3L);
		endMs = System.currentTimeMillis() - startMs;
		System.out.println("combination long :"+ ccclong + "");
		
		
		startMs = System.currentTimeMillis();
		BigInteger combinationBig = combinationBig(54, 13);
		endMs = System.currentTimeMillis() - startMs;
		System.out.println("combinationBig :"+ combinationBig + " ");
		
		System.out.println(combination(13, 5) * combination(8, 5) * 1);
		
		int[] aa = new int[] {2,3,5,4,1};
		rSort(aa);
		for (int i : aa) {
			System.out.print(i + " ,");
		}
		
	}
	
	
	

}
