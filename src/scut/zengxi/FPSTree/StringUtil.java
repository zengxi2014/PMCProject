package scut.zengxi.FPSTree;

import java.util.LinkedList;
import java.util.List;

/**
 * 处理字符串序列的工具类
 * @author zengxi
 *
 */
public class StringUtil {
	/**
	 * 将list<String> 转化为String
	 * @param list
	 * @return String 字符串
	 */
	public static String list2Str(List<String> list){
		String res="";
		for (int i = 0; i < list.size(); i++) {
			res+=list.get(i);
		}
		return res;
	}

	/**
	 * 构建next数组，KMP算法的辅助函数
	 * @param str
	 * @return next数组
	 */
	public static int[] next(String str) {
		char[] t=str.toCharArray();
		int[] next = new int[t.length];
		next[0] = -1;
		int i = 0;
		int j = -1;
		while (i < t.length - 1) {
			if (j == -1 || t[i] == t[j]) {
				i++;
				j++;
				if (t[i] != t[j]) {
					next[i] = j;
				} else {
					next[i] = next[j];
				}
			} else {
				j = next[j];
			}
		}
		return next;
	}

	/**
	 * 判断一个频繁极大序列集里是否包含一个频繁序列，并将极大频繁序列的子序列删去
	 * @param sequence  频繁序列
	 * @param mftss     频繁极大序列集
	 * @return 包含返回true，不包含返回false
	 */
/*	public static boolean isContain(List<String> sequence,List<List<String>> mftss){
		//将极大频繁序列的子序列删去
		for (int i = 0; i < mftss.size(); i++) {
			if(KMP_Index(list2Str(sequence),list2Str(mftss.get(i)))){
				mftss.remove(mftss.get(i));
				i--;
			}
		}
		//判断一个频繁极大序列集里是否包含一个频繁序列
		boolean flag=false;
		for (int i = 0; i < mftss.size(); i++) {
			if(KMP_Index(list2Str(mftss.get(i)),list2Str(sequence))){
				flag=true;
				break;
			}
		}
		return flag;
	}*/
	/**
	 * KMP匹配字符串
	 * @param
	 * @param
	 * @return   若匹配成功，返回下标，否则返回-1
	 */
	public static boolean KMP_Index(String str, String pattern) {
		int[] next = next(pattern);
		char[] t = pattern.toCharArray();
		char[] s = str.toCharArray();
		int i = 0;
		int j = 0;
		while (i <= s.length - 1 && j <= t.length - 1) {
			if (j == -1 || s[i] == t[j]) {
				i++;
				j++;
			} else {
				j = next[j];
			}
		}
		if (j < t.length) {
			return false;
		} else
			return true; // 返回模式串在主串中的头下标
	}
}
