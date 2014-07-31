package com.maojm.code.pattern.decorator;

public class ManDecoratorB extends Decorator{
	public void eat(){
		System.out.println("===================");
		System.out.println("call ManDecoratorB...");
		super.eat();
	}
}
