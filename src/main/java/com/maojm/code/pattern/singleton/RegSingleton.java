package com.maojm.code.pattern.singleton;

import java.util.HashMap;
import java.util.Map;

public class RegSingleton {
	private static Map reg_map = new HashMap();
	static{
		RegSingleton regSingleton = new RegSingleton();
		reg_map.put(regSingleton.getClass().getName(), regSingleton);
	}
	
	protected RegSingleton(){};
	
	public static RegSingleton getInstance(String name){
		if(name==null){
			name = "singleton.RegSingleton";
		}
		if(reg_map.get(name)==null){
			try {
				reg_map.put(name, Class.forName(name).newInstance());
			} catch (Exception e){
				System.err.println("Error Happened.");
			}
		}
		
		return (RegSingleton)reg_map.get(name);
	}
	
	public void sayHello(){
		System.out.println("hello, said by RegSingleton.");
	}
	
}
