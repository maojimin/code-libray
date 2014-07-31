package com.maojm.code.pattern.flyweight;

import java.util.HashMap;
import java.util.Map;

public class FlyweightFactory {
	private static Map<String, FlyWeight> flyweights = new HashMap<String, FlyWeight>();
	private FlyweightFactory(){};
	public static FlyWeight getFlyweight(String fwname){
		FlyWeight fw = flyweights.get(fwname);
		if(fw==null){
			fw = new FlyWeightImpl(fwname);
			flyweights.put(fwname, fw);
		}
		return fw;
	}


}
