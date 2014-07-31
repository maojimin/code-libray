package com.maojm.code.pattern.decorator;

public class ManDecoratorA extends Decorator{
	public void eat(){
		System.out.println("call ManDecoratorA...");
		super.eat();
		repeatEat();
	}
	public void repeatEat(){
		System.out.println("eat again.");
	}
}
