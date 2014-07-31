package com.maojm.code.others.test;

import java.util.HashMap;
import java.util.Map;

public class TestArgsWords {

	public static void main(String[]args){
		Map<String,Integer> m = new HashMap<String, Integer>();
		int onece = 1;
		for(int i=0;i<args.length;i++){
			if(!m.containsKey(args[i])){
				m.put(args[i], onece);
			}else{
				int freq = m.get(args[i]);
				m.put(args[i], freq+1);
			}
		}
		
		System.out.println(m);
	}
}
