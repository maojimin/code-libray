/*
 * yutian.com Inc.
 * Copyright (c) 2010-2013 All Rights Reserved.
 */
package com.maojm.code.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * @author <a href="mailto:maojimin@yutian.com.cn">毛积敏</a>
 * 2014年4月30日 下午10:34:41
 */
public class MapUtil<K,V> {
	private Iterator<Entry<K, V>> it;
	private int size;
	public MapUtil(Map<K, V> map){
		Set<Entry<K, V>> set = map.entrySet();
		it = set.iterator();
		size = map.size();
	}
	
	public int size(){
		return size;
	}
	
	public boolean hasNext(){
		return it.hasNext();
	}
	
	public Entry<K, V> next(){
		return it.next();
	}
	
	public static void main(String[] args) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("a", "a1");
		map.put("b", "b1");
		map.put("c", "c1");
		MapUtil<String, String> maputil = new MapUtil<String, String>(map);
		System.out.println(maputil.size());
		while(maputil.hasNext()){
			Entry<String, String> entry = maputil.next();
			System.out.println(entry.getKey()+"="+entry.getValue());
		}
		
	}
	
}
