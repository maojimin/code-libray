package com.maojm.code.pattern.facade;

public class Facade {
	private ServiceA sa;
	private ServiceB sb;
	private ServiceC sc;
	public Facade(){
		sa = new ServiceAImpl();
		sb = new ServiceBImpl();
		sc = new ServiceCImpl();
	}

	public void methodA(){
		sa.methodA();
		sb.methodB();
	}
	public void methodB(){
		sb.methodB();
		sc.methodC();
	}

	public void methodC(){
		sa.methodA();
		sb.methodB();
		sc.methodC();
	}

}
