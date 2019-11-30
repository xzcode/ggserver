package xzcode.ggserver.game.card.games.algo.poker.ddz;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ThreadLocalRandom;

import xzcode.ggserver.game.card.games.algo.poker.BasicPokerAlgoUtil;




/**
 * 斗地主算法工具类
 * 
 * @author zai
 * 2019-05-25 17:09:07
 */
public class AlgoDzzUtil extends BasicPokerAlgoUtil{
	
	/**
	 * 大王牌值
	 */
	public static int DA_WANG = 532;
	
	/**
	 * 小王牌值
	 */
	public static int XIAO_WANG = 531;
	
	public static final List<Integer> CARD_VAL_LIST = Arrays.asList(
			103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 122,		//方块1 到 K
			203, 204, 205, 206, 207, 208, 209, 210, 211, 212, 213, 214, 222,		//梅花1 到 K
			303, 304, 305, 306, 307, 308, 309, 310, 311, 312, 313, 314, 322,		//红桃1 到 K
			403, 404, 405, 406, 407, 408, 409, 410, 411, 412, 413, 414, 422,		//黑桃1 到 K
			531, // 小王
			532  // 大王
		);

	public static List<Integer> getCardValList() {
		return CARD_VAL_LIST;
	}
	
	
	
	/**
	 * 是否A
	 * 
	 * @param value
	 * @return
	 * @author zai
	 * 2019-07-02 14:20:17
	 */
	public boolean isAce(int value) {
		return value%100==14;
		 //return value == 114 || value == 214 || value == 314 || value == 414;
	}
	
	/**
	 * 是否2
	 * 
	 * @param value
	 * @return
	 * @author zai
	 * 2019-07-02 14:20:24
	 */
	public boolean isTwo(int value) {
		return value%100==22;
		//return value == 122 || value == 222 || value == 322 || value == 422;
	}
	
	/**
	 * 是否可出牌
	 * 
	 * @param curHandcards
	 * @param chupai
	 * @param lastPlayerChupai
	 * @return
	 * @author zai
	 * 2019-05-28 11:19:52
	 */
	public boolean isCanChupai(int[] curHandcards, int[] chupai, int[] lastPlayerChupai) {
		   //如果没有传入上家出牌，则可出任意牌型
        if (lastPlayerChupai == null)
        {
            int chupaiType = checkNumListType(getNumList(chupai));
            if (chupaiType > AlgoDzzCardType.NONE) return true;
            else return false;
        }
        else
        {
            if (compareCardsType(chupai, lastPlayerChupai) > 0) return true;
            else return false;
        }
	}
	public int checkCardType(List<Integer> cards) {
		int[] arr = new int[cards.size()];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = cards.get(i);
		}
		return checkCardType(arr);
	}
	/**
	 * 检查牌型
	 * 
	 * @param cards
	 * @return
	 * @author zai
	 * 2019-05-28 15:03:24
	 */
	public int checkCardType(int[] cards) {
		
		 if (cards == null) return AlgoDzzCardType.NONE;
         int[] numList = getNumList(cards);
         int[] sortType = new int[1];
         int result = checkNumListType(numList, sortType);
         if (sortType[0] >= 1) sortValueType(cards);
         if (sortType[0] == 2) sortBaseNumList(cards, numList);
         return result;
	}
	
	public List<List<Integer>> checkFollowOptions(List<Integer> cards, List<Integer> followCards) {
		int[] cardsArr = listToIntArr(cards);
		int[] followCardsArr = listToIntArr(followCards);
		
		List<List<Integer>> list = new ArrayList<>();
		List<int[]> checkFollowOptions = checkFollowOptions(cardsArr, followCardsArr);
		if (checkFollowOptions != null) {
			for (int[] arr : checkFollowOptions) {
				List<Integer> ll = new ArrayList<>();
				for (int i = 0; i < arr.length; i++) {
					ll.add(arr[i]);
				}
				list.add(ll);
			}
		}
		return list;
	}
	
	/**
	 * 获取跟牌选项
	 * 
	 * @param cards
	 * @return
	 * @author zai
	 * 2019-07-02 16:16:45
	 */
	public List<int[]> checkFollowOptions(int[] cards, int[] followCards) {
		if (cards == null) return null;
		if(followCards==null)
		{
			List<int[]> result = new ArrayList<int[]>();
			int[] get=getFirstRemind(cards);
			result.add(get);
			return result;
		}
        int[] catchNumList = getNumList(followCards);
        int[] sortType = new int[1];
        int catchType = checkNumListType(catchNumList,  sortType); //内部实现catchNumList排序
        if (catchType == AlgoDzzCardType.NONE || catchType == AlgoDzzCardType.WANG_ZHA) return null;
        int[] numList = getNumList(cards);
        sort(numList);
        List<List<Integer>> getSort = getSortCell(numList);
        List<int[]> findNumTips = null;
        if (cards.length >= followCards.length)   //相同牌型的获取
        {
            switch (catchType)
            {
                case AlgoDzzCardType.DAN_ZHANG:
                case AlgoDzzCardType.DUI_ZI:
                case AlgoDzzCardType.SAN_ZHANG:
                case AlgoDzzCardType.ZHA_DAN:
                    {
                        findNumTips = findSame(getSort, catchNumList);
                    }; break;
                case AlgoDzzCardType.DAN_SHUN_ZI: findNumTips = findStraight(numList, catchNumList, 1); break;
                case AlgoDzzCardType.SHUANG_SHUN_ZI: findNumTips = findStraight(numList, catchNumList, 2); break;
                case AlgoDzzCardType.SAN_SHUN_ZI: findNumTips = findStraight(numList, catchNumList, 3); break;
                case AlgoDzzCardType.SAN_DAI_YI: findNumTips = findSameCarry(getSort, catchNumList, 3, 1); break;
                case AlgoDzzCardType.SAN_DAI_ER: findNumTips = findSameCarry(getSort, catchNumList, 3, 2); break;
                case AlgoDzzCardType.SI_DAI_ER_DAN: findNumTips = findSameCarry(getSort, catchNumList, 4, 1); break;
                case AlgoDzzCardType.SI_DAI_ER_SHUANG: findNumTips = findSameCarry(getSort, catchNumList, 4, 2); break;
                case AlgoDzzCardType.FEI_JI_31: findNumTips = findPlane(getSort, numList, catchNumList, 1); break;
                case AlgoDzzCardType.FEI_JI_32: findNumTips = findPlane(getSort, numList, catchNumList, 2); break;
            }
        }
        if (catchType < AlgoDzzCardType.ZHA_DAN)
        {
            List<int[]> zha = findSame(getSort, new int[] { 0, 0, 0, 0 });
            if (zha != null)
            {
                if (findNumTips == null) findNumTips = new ArrayList<int[]>();
                findNumTips.addAll(zha);
            }
        }
        if (catchType < AlgoDzzCardType.WANG_ZHA)
        {
            if (containCell(numList, XIAO_WANG % 100) && containCell(numList, DA_WANG % 100))
            {
                if (findNumTips == null) findNumTips = new ArrayList<int[]>();
                findNumTips.add(new int[] { DA_WANG % 100, XIAO_WANG % 100 });
            }
        }
        if (findNumTips == null) return null;
        else
        {
            sortValueType(cards);
            List<int[]> result = new ArrayList<int[]>();
            for (int i = 0; i < findNumTips.size(); i++)
            {
                result.add(findCardsBaseNum(cards, findNumTips.get(i)));
            }
            return result;
        }
	}

       public void sort(int[] cards)
       {
           Arrays.sort(cards);
       }
       public void sortValueType(int[] cards)
       {
    	   
         //  Arrays.sort(cards, (x, y) ->
         //    {
         //       int result = x % 100 - y % 100;
         //      if (result == 0) result = x / 100 - y / 100;
         //      return result;
         //  });
      }
       public int[] getNumList(int[] cards)
       {
           int[] result = new int[cards.length];
           for (int i = 0; i < cards.length; i++)
           {
               result[i] = cards[i] % 100;
           }
           return result;
       }
       public void sortSpecial(int[] numList,int index,int lengthNum)
       {
           int[] indexs = new int[1];
           indexs[0] = index;
           sortSpecial(numList, indexs, lengthNum);
       }
       public void sortSpecial(int[] numList, int[] indexs, int lengthNum)
       {
           int[] temp =  Arrays.copyOf(numList,numList.length);
           int index = 0;
           boolean[] find = new boolean[numList.length];
           for (int i = 0; i < indexs.length; i++)
           {
               for (int j = indexs[i]; j < lengthNum + indexs[i]; j++)
               {
                   numList[index] = temp[j];
                   find[j] = true;
                   index++;
               }
           }
           for (int i = 0; i < temp.length; i++)
           {
               if (!find[i])
               {
                   numList[index] = temp[i];
                   index++;
               }
           }
       }
       public void sortBaseNumList(int[] cards,int[] numList)
       {
           int[] temp =  Arrays.copyOf(cards,cards.length);
           boolean[] haveAdd = new boolean[cards.length];
           for (int i = 0; i < numList.length; i++)
           {
               for (int j = 0; j < temp.length; j++)
               {
                   if (!haveAdd[j] && temp[j] % 100 == numList[i])
                   {
                       cards[i] = temp[j];
                       haveAdd[j] = true;
                       break;
                   }
               }
           }
       }
       public boolean containCell(int[]  arr,int value)
       {
           int min = 0;
           int max = arr.length - 1;
           while(min<=max)
           {
               int mid = (max + min) / 2;
               int valu1 = arr[mid];
               if (value == valu1) return true;
               else if (value < valu1) max = mid - 1;
               else min = mid + 1;
           }
           return false;
       }
       public  int[] distinct(int[] arr,boolean haveSort,int minNum)//事先排序
       {
           if (!haveSort) Arrays.sort(arr);
           int[]result = new int[arr.length] ;
           int startCut = 0;
           int nowIndex = 0;
           if (arr.length == 1) return new int[] { arr[0] };
           for (int i = 0; i < arr.length - 1; i++)
           {
               if (arr[i] != arr[i + 1])
               {
                 
                   if (i - startCut + 1 >= minNum)
                   {
                       result[nowIndex] = (arr[i]);
                       nowIndex++;
                   }
                   startCut = i + 1;
                   if (startCut == arr.length - 1 && minNum == 1)
                   {
                       result[nowIndex] = (arr[i + 1]);
                       nowIndex++;
                   }
               }
               else if (i == arr.length - 2)
               {
                   int index = i - startCut + 1;
                   if (index + 1 >= minNum)
                   {
                       result[nowIndex] = (arr[i]);
                       nowIndex++;
                   }
               }
           }
           int[] last = new int[nowIndex];
           for (int i = 0; i < last.length; i++)
           {
               last[i] = result[i];
           }
           return last;
       }
       /**same类型检测（单张，对子，三张，炸弹）
        * 
        * @param numList
        * @return
        */
       public int isSame(int[] numList) //**事先排序
       {
           if (numList.length > 4 || numList[0] != numList[numList.length - 1]) return 0;
           return numList.length;
       }
       /**顺检测
        * 
        * @param numList 检测数组
        * @param singleNum 单元素的数量
        * @return
        */
       public boolean isStraight(int[] numList, int singleNum) //**事先排序
       {
           int minTypeNum = singleNum == 1 ? 5 : 2;
           if (numList.length % singleNum != 0 || numList.length < singleNum * minTypeNum) return false;
           return straight(numList, singleNum, numList.length / singleNum, 0);
       }
       /**判断numList某段数据是否是某顺
        * 
        * @param numList 检测数组
        * @param singleNum 单元素的数量
        * @param typeNum 几连顺
        * @param startIndex 开始检测的下标
        * @return
        */
       public boolean straight(int[] numList, int singleNum, int typeNum, int startIndex) //**事先排序
       {
           int endIndex = startIndex + singleNum * typeNum - 1;
           if (endIndex >= numList.length) return false;
           for (int i = startIndex + singleNum; i <= endIndex; i += singleNum)
           {
               if (numList[i] != numList[i - singleNum] + 1) return false;
               if (singleNum > 1)
               {
                   if (numList[i] != numList[i + singleNum - 1] || numList[i - 1] != numList[i - singleNum]) return false;
               }
           }
           return true;
       }
       public boolean planeDouble(int[] numList) //**事先排序
       {
           if (numList.length >= 10 && numList.length % 5 == 0) //飞机带对
           {
               int typeNum = numList.length / 5;
               boolean findPlane = false;
               int findIndex = 0;
               for (int i = 0; i < numList.length - 1; i += 2)
               {
                   if (!findPlane)
                   {
                       if (straight(numList, 3, typeNum, i))
                       {
                           findPlane = true;
                           findIndex = i;
                           i += typeNum * 3 - 2;
                           continue;
                       }
                       else if (i == numList.length - typeNum * 3) return false;
                   }
                   if (numList[i] != numList[i + 1])
                   {
                       findPlane = false;
                       break;
                   }
               }
               if (findPlane)
               {
                   sortSpecial(numList, findIndex, typeNum * 3);
               }
               return findPlane;
           }
           return false; 
       }
       public boolean planeSingle(int[] numList) //**事先排序
       {
           if (numList.length >= 8 && numList.length % 4 == 0)
           {
               int typeNum = numList.length / 4;
               int[] find = new int[typeNum];
               int[] findIndex = new int[typeNum];
               int index = 0;
               for (int i = 0; i <= numList.length - 3;)
               {
                   if (numList[i] == numList[i + 2]&&index<find.length)
                   {

                       find[index] = numList[i];
                       findIndex[index] = i;
                       index++;
                       i += 3;
                       if (i < numList.length && numList[i] == numList[i - 1]) i++;
                   }
                   else i++;
               }
               if (index == typeNum)
               {
                   if (straight(find, 1, find.length, 0))
                   {
                       sortSpecial(numList, findIndex, 3);
                       return true;
                   }
               }
           }
           return false;
       }
       /** 三带一，三带对，四带一，四带对
        * 
        * @param numList 检测数组
        * @param type 3带或者4带
        * @param carryType 1：带单，2：带对）
        * @return
        */
       public boolean isSameCarry(int[] numList,int type,int carryType) //**事先排序
       {
           int carryTypeNum = type == 3 ? 1 : 2;
           if (numList.length != type + carryTypeNum * carryType) return false;
           if (numList.length == 4 && numList[0] == numList[3]) return false; //三带一排除炸弹的影响
           boolean findType = false;
           int findIndex = 0;
           for (int i = 0; i < numList.length - 1;)
           {
               if (!findType && i <= numList.length - type && numList[i] == numList[i + type - 1])
               {
                   findType = true;
                   findIndex = i;
                   i += type;
                   if (carryType == 2) continue;
                   else break;
               }
               if (carryType == 2)
               {
                   if (numList[i] != numList[i + 1])
                   {
                       return false;
                   }
                   else i += 2;
               }
               else  i++;
           }
           if (findType == false) return false;
           sortSpecial(numList, findIndex, type);
           return true;
       }
       public int checkNumListType(int[] numList) //##内部排序
       {
           int[] sortType = new int[1];
           return checkNumListType(numList,  sortType);
       }
       /** 检测numList返回牌型
        * 
        * @param numList 检测数组
        * @param sortType 返回排序类型,0:无需排序，1：正常排序，2：特殊排序
        * @return
        */
       public int checkNumListType(int[] numList, int[] sortType) //##内部排序
       {
           int result = AlgoDzzCardType.NONE;
           if (numList.length > 2)
           {
               sort(numList);
           }
           for (int i = 0; i <= 3; i++)
           {
               switch (i)
               {
                   case 0:  //返回无需排序(Same牌型) 4个
                       {
                           if (isSame(numList) > 0)
                           {
                               if (numList.length == 1) return AlgoDzzCardType.DAN_ZHANG;
                               else if (numList.length == 2) return AlgoDzzCardType.DUI_ZI;
                               else if (numList.length == 3) return AlgoDzzCardType.SAN_ZHANG;
                               else if (numList.length == 4) return AlgoDzzCardType.ZHA_DAN;
                           }
                       }
                       break;
                   case 1: //返回正常排序（Straight、双王） 4个
                       {
                           if (isStraight(numList, 1)) result = AlgoDzzCardType.DAN_SHUN_ZI;
                           else if (isStraight(numList, 2)) result = AlgoDzzCardType.SHUANG_SHUN_ZI;
                           else if (isStraight(numList, 3)) result = AlgoDzzCardType.SAN_SHUN_ZI;
                           else if (numList.length == 2 && numList[0] >= XIAO_WANG % 100 && numList[1] >= XIAO_WANG % 100) result = AlgoDzzCardType.WANG_ZHA;
                           if (result != AlgoDzzCardType.NONE)
                           {
                               sortType[0] = 1;
                               return result;
                           }
                       }
                       break;
                   case 2: //Carry牌型检测，  （返回特殊排序） 6个
                       {
                           if (planeSingle(numList)) result = AlgoDzzCardType.FEI_JI_31;
                           else if (planeDouble(numList)) result = AlgoDzzCardType.FEI_JI_32;
                           else if (isSameCarry(numList, 3, 1)) result = AlgoDzzCardType.SAN_DAI_YI;
                           else if (isSameCarry(numList, 4, 1)) result = AlgoDzzCardType.SI_DAI_ER_DAN;
                           else if (isSameCarry(numList, 3, 2)) result = AlgoDzzCardType.SAN_DAI_ER;
                           else if (isSameCarry(numList, 4, 2)) result = AlgoDzzCardType.SI_DAI_ER_SHUANG;
                           if (result != AlgoDzzCardType.NONE)
                           {
                               sortType[0] = 2;
                               return result;
                           }
                       }
                       break;
               }
           }
           return AlgoDzzCardType.NONE;
       }
       public int[] getFirstRemind(int[] cards)
       {
           //最小张的优先提示，单张，对子，三条不带。
           //仅仅剩余炸弹和王炸的情况，提示炸弹  simple功能。
           if (cards.length == 0) return null;
           int[] numList = getNumList(cards);
           sort(numList);
           if (numList.length == 2 && numList[0] == 31 && numList[1] == 32) return cards;
           List<List<Integer>> getSort = getSortCell(numList);
           List<Integer> findMin = new ArrayList<Integer>();
           int startIndex = -1;
           for (int i=0;i<getSort.size();i++ )
           {
               if (getSort.get(i).size() == 0) continue;
               if(startIndex==-1||getSort.get(i).get(0)<getSort.get(startIndex).get(0))
               {
                   startIndex = i;
               }
           }
           int[] result = new int[startIndex+1];
           int start = 0;
           for (int i = 0; i < cards.length; i++)
           {
               if (cards[i] % 100 == getSort.get(startIndex).get(0))
               {
                   result[start] = cards[i];
                   start++;
                   if (start == result.length) break;
               }
           }
           return result;
       }
       public int compareCardsType(int[] cardsOne,int[] cardsTwo)
       {
           int[] oneList = getNumList(cardsOne);
           int[] twoList = getNumList(cardsTwo);
           int oneType = checkNumListType(oneList);
           int twoType = checkNumListType(twoList);
           if(oneType==twoType&&cardsOne.length==cardsTwo.length )
           {
               return oneList[0] - twoList[0];
           }
           if(oneType!=twoType)
           {
               if (oneType == AlgoDzzCardType.WANG_ZHA) return 1;
               if (twoType == AlgoDzzCardType.WANG_ZHA) return -1;
               if (oneType == AlgoDzzCardType.ZHA_DAN) return 1;
               if (twoType == AlgoDzzCardType.ZHA_DAN) return -1;
           }
           return -1000;
       }
       /**按元素个数特性获取分类数组。
        * 
        * @param numList
        * @return
        */
       public List<List<Integer>> getSortCell(int[] numList ) //**事先排序
       {
           List<List<Integer>> result = new ArrayList<List<Integer>>();
           for (int i = 0; i < 4; i++)
           {
               result.add(new  ArrayList<Integer>());
           }
           int startIndex = 0;
           if(numList.length==1) result.get(0).add(numList[0]);
           for (int i = 0; i < numList.length-1; i++)
           {
               if(numList[i]!=numList[i+1])
                {
                   int index = i - startIndex ;
                   result.get(index).add(numList[i]);
                   startIndex = i + 1 ;
                   if(startIndex==numList.length-1)
                   {
                       result.get(0).add(numList[startIndex]);
                   }
               }
               else if(i==numList.length-2)
               {
                   int index = i - startIndex+1;
                   result.get(index).add(numList[i]);
               }
           }
           return result;
       }
       public void addCell(int[] result,int cell,int num,int startIndex)
       {
           for (int j = startIndex; j < num; j++)
           {
               result[j] = cell;
           }
       }
       public int[] findCardsBaseNum(int[] cards,int[] numList) //cards事先排序
       {
           boolean[] find = new boolean[cards.length];
           int[] result = new int[numList.length];
           for(int i=0 ;i< numList.length;i++)
           {
               for (int j = 0; j < cards.length; j++)
               {
                   if (find[j]) continue;
                   if (cards[j] % 100 !=numList[i]) continue;
                   result[i] = cards[j];
                   find[j] = true;
                   break;
               }
           }
           return result;
           
       }
       /**寻找单张，对子，三张，炸弹。
        * 
        * @param getSort 分类数组
        * @param catchNum 接牌数据
        * @return
        */
       public List<int[]> findSame(List<List<Integer>> getSort, int[] catchNum) //**事先排序
       {
           List<int[]> result = null;
           for (int i = catchNum.length-1; i < getSort.size(); i++)
           {
               if (getSort.get(i).size() > 0)
               {
                   for (int ii : getSort.get(i))
                   {
                       if (ii > catchNum[0])
                       {
                           if (result == null) result = new ArrayList<int[]>();
                           int[] temp = new int[catchNum.length];
                           addCell(temp, ii, catchNum.length, 0);
                           result.add(temp);
                       }
                   }
               }
           }
           return result;
       }
       /** 寻找顺类型
        * 
        * @param numList 检测数组
        * @param catchNum 接牌数据
        * @param singelNum 单元素数量
        * @return
        */
       public List<int[]> findStraight(int[] numList, int[] catchNum, int singelNum)  //**事先排序
       {
           if (numList.length < catchNum.length) return null;
           int[] dis = distinct(numList,true,singelNum);
           List<int[]> result = null;
           int typeNum = catchNum.length / singelNum;
           for (int i = 0; i <= dis.length-(typeNum); i++)
           {
               if (dis[i] > catchNum[0] && straight(dis, 1, typeNum, i ))
               {
                   if (result == null) result = new ArrayList<int[]>();
                   int[] addCell = new int[catchNum.length];
                   int index = 0;
                   for (int k = i; k < typeNum + i; k++)
                   {
                       for (int j = 0; j < singelNum; j++)
                       {
                           addCell[index] = dis[k];
                           index++;
                       }
                   }
                   result.add(addCell);
               }
           }
           return result;
       }
       public List<int[]> findSameCarry(List<List<Integer>> getSort, int[] catchNum,int type,int carryType)  //事先排序
       {
           List<int[]> result = null;
           for (int i = type - 1; i < getSort.size(); i++)
           {
               if (getSort.get(i).size() == 0) continue;
               for (int ii :getSort.get(i))
               {
                   if (ii <= catchNum[0]) continue;
                   if (result == null) result = new ArrayList<int[]>();
                   int[] cell = new int[catchNum.length];
                   addCell(cell, ii, type, 0); //same添加   
                   int addIndex = type;
                   for (int j = carryType - 1; j < getSort.size(); j++) //carry添加
                   {
                       if (getSort.get(j).size() == 0) continue;
                       if (addIndex == catchNum.length) break; //carry加满
                       for (int jj : getSort.get(j))
                       {
                           if (addIndex == catchNum.length) break;
                           if (ii == jj) continue;//carry不可能和same相等
                           cell[addIndex++] = jj;
                           if (carryType == 2) cell[addIndex++] = jj;
                           else if (j > 0 && addIndex < catchNum.length) cell[addIndex++] = jj;
                       }
                   }
                   if (addIndex == catchNum.length) result.add(cell);
               }
           }
           return result;
       }
       public List<int[]> findPlane(List<List<Integer>> getSort,int[] numList, int[] catchNum,int carryType) //事先排序
       {
           int typeNum = carryType == 0 ? catchNum.length / 3 : carryType == 1 ? catchNum.length / 4 : catchNum.length / 5;
           int singleNum = 3;
           int[] dis = distinct(numList, true, 3);
           List<int[]> result = null;
           for (int i = 0; i <= dis.length - (typeNum); i++)
           {
               if (!(dis[i] > catchNum[0] && straight(dis, 1, typeNum, i))) continue;
               if (result == null) result = new ArrayList<int[]>();
               int[] addCell = new int[catchNum.length];
               int index = 0;
               for (int k = i; k < typeNum + i; k++)
               {
                   for (int j = 0; j < singleNum; j++)
                   {
                       addCell[index] = dis[k];
                       index++;
                   }
               }
               if (carryType < 1) continue;
               //carry添加
               int addIndex = typeNum * singleNum;
               for (int j = carryType - 1; j < getSort.size(); j++) //carry添加
               {
                   if (getSort.get(j).size() == 0) continue;
                   if (addIndex == catchNum.length) break;
                   for (int jj : getSort.get(j))
                   {
                       if (addIndex == catchNum.length) break;
                       if (containCell(addCell, jj) && j == 2) continue; //不重复添加
                       addCell[addIndex++] = jj;
                       if (carryType == 2) addCell[addIndex++] = jj;
                       else if (j > 0)
                       {
                           for (int k = 0; k < j; k++)
                           {
                               if (addIndex == catchNum.length) break;
                               addCell[addIndex++] = jj;
                           }
                       }
                   }
               }
               result.add(addCell);
           }
           return result;
       }

	public static void main(String[] args) {
	//	AlgoDzzUtil util = new AlgoDzzUtil();
		
		//int testTimes = 100 * 10000;
		//List<int[]> testList = new ArrayList<>();
		//ThreadLocalRandom random = ThreadLocalRandom.current();
		
		//long startTime = 0;
		//long useTime = 0;
		
		//int[] cards = new int[] { 114, 105, 205, 305, 108, 208, 306, 214, 101, 206,405, 303, 314, 313, 412, 413, 414 };
		//int[] followCards = new int[] {103, 203, 303, 403, 405, 305};
		//for (int i = 0; i < testTimes; i++) {
			
			/*
			testList.add(new int[] {
				random.nextInt(101, 114),
				random.nextInt(201, 214),
				random.nextInt(301, 314),
				random.nextInt(401, 414),
				random.nextInt(501, 514),
				random.nextInt(501, 514),
				random.nextInt(501, 514),
				random.nextInt(501, 514),
				random.nextInt(501, 514),
				random.nextInt(501, 514),
			});	
			*/	
			
			//testList.add(cards);
		//}
		
		
		//预热
		//int cardType = util.checkCardType(cards);
		//List<int[]> checkFollowSanDaiYi = util.checkFollowSanDaiYi(cards,followCards);
		//List<int[]> checkFollowSanDaiEr = util.checkFollowSanDaiEr(cards,followCards);
		//List<int[]> checkSiDaiErDan = util.checkSiDaiErDan(cards,followCards);
		//List<int[]> checkSiDaiErShuang = util.checkSiDaiErShuang(cards,followCards);
		
		//System.out.println("checkCardType: " + cardType);
		//System.out.println("checkFollowSanDaiYi: ");
		//for (int[] is : checkFollowSanDaiYi) {
		//	util.printArr(is);
		//}
		//System.out.println("checkFollowSanDaiEr: ");
	//	for (int[] is : checkFollowSanDaiEr) {
		//	util.printArr(is);
	//	}
		//System.out.println("checkSiDaiErDan: ");
		//if (checkSiDaiErDan != null) {
		//	for (int[] is : checkSiDaiErDan) {
		//		util.printArr(is);
	//		}
	//	}
		
	//	System.out.println("checkSiDaiErShuang: ");		
	//	if (checkSiDaiErShuang != null) {
	//		for (int[] is : checkSiDaiErShuang) {
	//			util.printArr(is);
	//		}
	//	}
		
		
		//计算时间
	//	startTime = System.currentTimeMillis();
	//	for (int[] is : testList) {
	//		util.checkCardType(is);
	//	}
	//	useTime = System.currentTimeMillis() - startTime;
	//	System.out.println("checkCardType : " + useTime + " ms");
		
		//计算飞机时间
	//	int[] ff=new int[]	{2,2,3,3,3,4,4,4,6,6};
		//		startTime = System.currentTimeMillis();
		//		for(int i=0;i<30000000;i++)
		//		{
		//		   util.FindSame(util.GetSortCell(ff),new int[] {2,2});
				  // System.out.println("Plane gg : " + useTime + " ms");
		//		}
		//		useTime = System.currentTimeMillis() - startTime;
	//			System.out.println("Plane gg : " + useTime + " ms");
		//计算时间
	//	startTime = System.currentTimeMillis();
	//	for (int[] is : testList) {
	//	util.checkFollowSanDaiYi(cards,followCards);
	//	}
	//	useTime = System.currentTimeMillis() - startTime;
	//	System.out.println("checkFollowSanDaiYi : " + useTime + " ms");
		
		
		//计算时间
	//	startTime = System.currentTimeMillis();
	//	for (int[] is : testList) {
	//		util.checkFollowSanDaiEr(cards, followCards);
	//	}
	//	useTime = System.currentTimeMillis() - startTime;
	//	System.out.println("checkFollowSanDaiEr : " + useTime + " ms");
		
		
	}
	
}
