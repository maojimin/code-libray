package com.maojm.code.pattern.singleton;

import junit.framework.TestCase;

import com.maojm.code.pattern.factory.simplefactory.BadFruitException;
import com.maojm.code.pattern.factory.simplefactory.Fruit;
import com.maojm.code.pattern.factory.simplefactory.FruitGardener;

public class SimplefactoryTest extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testSimpleFactory(){
		try{
			Fruit fruit = FruitGardener.factory("apple");
			fruit.grow();
			fruit = FruitGardener.factory("grape");
			fruit.grow();
			fruit = FruitGardener.factory("strawberry");
			fruit.grow();
			fruit = FruitGardener.factory("strawberry11");
			fruit.grow();
		}catch(BadFruitException e){
			System.out.println(e.getMessage());
		}
	}

}
