package com.maojm.code.pattern.decorator;

import junit.framework.TestCase;

public class DecoratorTest extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testDecorator(){
		Person man = new Man();
		Decorator decoratorA = new ManDecoratorA();
		Decorator decoratorB = new ManDecoratorB();
		decoratorA.setPerson(man);
		decoratorB.setPerson(decoratorA);
		decoratorB.eat();
	}

}
