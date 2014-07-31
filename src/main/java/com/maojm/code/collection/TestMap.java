package com.maojm.code.collection;

import java.util.HashMap;
import java.util.Map;

public class TestMap {
	public static void main(String[] args) {
		Map<String, Integer> m1 = new HashMap<String, Integer>();
		Map<String, Integer> m2 = new HashMap<String, Integer>();
		m1.put("one", 1);		//auto-boxing
		m1.put("two", 2);
		m1.put("three", 3);
		m2.put("four", 4);
		m2.put("five", 5);
		System.out.println(m1.containsKey("one"));
		System.out.println(m1.containsKey(2));
		if(m2.containsKey("four")){
			int i =(Integer)m2.get("four");
			System.out.println(i);
		}
		Map<String, Integer> m3 = new HashMap<String, Integer>(m1);
		m3.putAll(m2);
		System.out.println(m3);
		
	}

}
