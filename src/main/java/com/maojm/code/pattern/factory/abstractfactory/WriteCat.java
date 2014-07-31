package com.maojm.code.pattern.factory.abstractfactory;

public class WriteCat implements ICat {

	@Override
	public void eat() {
		System.out.println("write cat is eating.");
	}

}
