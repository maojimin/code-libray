package com.maojm.code.algorithm;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
/**
 * 敏感词过滤类
 * @author <a href="mailto:maojimin@yutian.com.cn">毛积敏</a>
 * @since 2014年4月26日 下午4:57:36
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class KeyWordFilter {
	/** 直接禁止的 */
	private static HashMap keysMap = new HashMap();

	private static int matchType = 1; // 1:最小长度匹配 2：最大长度匹配

	private static Lock lock = new ReentrantLock();

	public static boolean keysMapIsEmpty(){
		return keysMap.isEmpty();
	}
	/**
	 * 初始化敏感词
	 * 
	 * @param keywords
	 * @author tom 下午10:04:28 - 2013-8-30
	 */
	public static void addKeywords(List<String> keywords) {
		try {
			lock.lock();
			keysMap.clear();
			for (int i = 0; i < keywords.size(); i++) {
				String key = keywords.get(i).trim();
				HashMap nowhash = null;
				nowhash = keysMap;
				for (int j = 0; j < key.length(); j++) {
					char word = key.charAt(j);
					Object wordMap = nowhash.get(word);
					if (wordMap != null) {
						nowhash = (HashMap) wordMap;
					} else {
						HashMap<String, String> newWordHash = new HashMap<String, String>();
						newWordHash.put("isEnd", "0");
						nowhash.put(word, newWordHash);
						nowhash = newWordHash;
					}
					if (j == key.length() - 1) {
						nowhash.put("isEnd", "1");
					}
				}
			}
		} finally {
			lock.unlock();
		}
	}

	/**
	 * 检查一个字符串从begin位置起开始是否有keyword符合， 如果有符合的keyword值， 返回值为匹配keyword的长度，否则返回零
	 * flag 1:最小长度匹配 2：最大长度匹配
	 */
	private static int checkKeyWords(String txt, int begin, int flag) {
		HashMap nowhash = null;
		nowhash = keysMap;
		int maxMatchRes = 0;
		int res = 0;
		int l = txt.length();
		char word = 0;
		for (int i = begin; i < l; i++) {
			word = txt.charAt(i);
			Object wordMap = nowhash.get(word);
			if (wordMap != null) {
				res++;
				nowhash = (HashMap) wordMap;
				if (((String) nowhash.get("isEnd")).equals("1")) {
					if (flag == 1) {
						wordMap = null;
						nowhash = null;
						txt = null;
						return res;
					} else {
						maxMatchRes = res;
					}
				}
			} else {
				txt = null;
				nowhash = null;
				return maxMatchRes;
			}
		}
		txt = null;
		nowhash = null;
		return maxMatchRes;
	}

	/**
	 * 返回txt中关键字的列表
	 */
	public static String getTxtKeyWords(String txt) {
		StringBuilder sb = new StringBuilder();
		int l = txt.length();
		for (int i = 0; i < l;) {
			int len = checkKeyWords(txt, i, matchType);
			if (len > 0) {
				String tt = txt.substring(i, i + len);
				if (sb.length() == 0) {
					sb.append(tt);
				} else {
					sb.append("|").append(tt);
				}
				i += len;
			} else {
				i++;
			}
		}
		txt = null;
		return sb.toString();
	}

	/**
	 * 仅判断txt中是否有关键字
	 */
	public static boolean isContentKeyWords(String txt) {
		for (int i = 0; i < txt.length(); i++) {
			int len = checkKeyWords(txt, i, 1);
			if (len > 0) {
				return true;
			}
		}
		txt = null;
		return false;
	}

	public int getMatchType() {
		return matchType;
	}

	public void setMatchType(int matchType) {
		this.matchType = matchType;
	}

	public static void main(String[] args) throws IOException {
//		Date date = new Date();
//		String txt = "長瀨愛插插小泽圆";
//		boolean boo = isContentKeyWords(txt);
//		System.out.println(boo);
//		String set = getTxtKeyWords(txt);
//		System.out.println("包含的敏感词如下：" + set);
//		Date date2 = new Date();
//		float ss = date2.getTime() - date.getTime();
//		System.out.println(ss + "毫秒");
	}
}