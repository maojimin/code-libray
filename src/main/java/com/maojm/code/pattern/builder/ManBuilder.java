package com.maojm.code.pattern.builder;

public class ManBuilder implements PersonBuilder {

	Person person = null;
	
	public ManBuilder(){
		person = new Person();
	}
	
	@Override
	public void buildHead() {
		person.setHead("man's head");
	}

	@Override
	public void buildBody() {
		person.setBody("man's body");
	}

	@Override
	public void buildFoot() {
		person.setFoot("man's foot");
	}

	@Override
	public Person buildPerson() {
		return person;
	}

}
