package com.maojm.code.pattern.factory.factorymethod;

public class StudentWork implements Work {

	@Override
	public void doWork() {
		System.out.println("学生做作业！");
	}

}
