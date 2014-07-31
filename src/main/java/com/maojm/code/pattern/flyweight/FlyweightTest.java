package com.maojm.code.pattern.flyweight;

import junit.framework.TestCase;

public class FlyweightTest extends TestCase{

	public void testFlyweight(){
		FlyWeight fw1;
		FlyWeight fw2;
		FlyWeight fw3;
		FlyWeight fw4;
		FlyWeight fw5;
		fw1 = FlyweightFactory.getFlyweight("google");
		fw2 = FlyweightFactory.getFlyweight("google");
		System.out.println(fw1==fw2);
		fw1.action();
		fw2.action();
		fw3 = FlyweightFactory.getFlyweight("baidu");
		fw3.action();
		fw4 = FlyweightFactory.getFlyweight("360");
		fw4.action();
		fw5 = FlyweightFactory.getFlyweight("mtime");
		fw5.action();
	}
}
