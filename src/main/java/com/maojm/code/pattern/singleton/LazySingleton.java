package com.maojm.code.pattern.singleton;

public class LazySingleton {
	private static LazySingleton instance = null;
	private LazySingleton(){};
	public synchronized static LazySingleton getInstance(){
		if(instance == null){
			instance = new LazySingleton();
		}
		return instance;
	}
	
	public static LazySingleton getInstance2(){
		if(instance==null){
			synchronized (LazySingleton.class) {
				if(instance==null){
					instance = new LazySingleton();
				}
			}
		}
		return instance;
	}
}
