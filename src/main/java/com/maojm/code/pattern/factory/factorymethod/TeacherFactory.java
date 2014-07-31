package com.maojm.code.pattern.factory.factorymethod;

public class TeacherFactory implements IWorkFactory{

	@Override
	public Work getWork() {
		return new TeacherWork();
	}
	
}
