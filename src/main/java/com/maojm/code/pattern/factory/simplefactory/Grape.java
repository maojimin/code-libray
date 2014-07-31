package com.maojm.code.pattern.factory.simplefactory;

public class Grape implements Fruit{

	boolean seedless;
	
	@Override
	public void grow() {
		log("Grape is growing...");
	}

	@Override
	public void harvest() {
		log("Grape has been harvented.");
	}

	@Override
	public void plant() {
		log("Grape has been planted.");
	}
	
	public void log(String msg){
		System.out.println(msg);
	}

	public boolean isSeedless() {
		return seedless;
	}

	public void setSeedless(boolean seedless) {
		this.seedless = seedless;
	}

}
