package com.maojm.code.pattern.builder;

public class PersonDirector {
	public Person constructPerson(PersonBuilder pb){
		pb.buildBody();
		pb.buildHead();
		pb.buildFoot();
		return pb.buildPerson();
	}
}
