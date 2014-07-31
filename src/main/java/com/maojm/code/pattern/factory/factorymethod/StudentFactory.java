package com.maojm.code.pattern.factory.factorymethod;

public class StudentFactory implements IWorkFactory {

	@Override
	public Work getWork() {
		return new StudentWork();
	}

}
