package com.maojm.code.pattern.factory.simplefactory;

public class Strawberry implements Fruit {

	@Override
	public void grow() {
		log("Strawberry is growing...");
	}

	@Override
	public void harvest() {
		log("Strawberry has been harested.");
	}

	@Override
	public void plant() {
		log("Strawberry has been planted.");
	}
	
	public void log(String msg){
		System.out.println(msg);
	}

}
