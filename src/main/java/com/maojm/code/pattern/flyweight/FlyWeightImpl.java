package com.maojm.code.pattern.flyweight;

public class FlyWeightImpl implements FlyWeight {

	private String fwname;
	public FlyWeightImpl(String name){
		fwname = name;
	}

	@Override
	public void action() {
		System.out.println("concrete FlyWeight:"+fwname +" is called.");
	}

}
