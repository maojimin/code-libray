package com.maojm.code.pattern.factory.factorymethod;

public class TeacherWork implements Work {

	@Override
	public void doWork() {
		System.out.println("老师批作业！");
	}

}
