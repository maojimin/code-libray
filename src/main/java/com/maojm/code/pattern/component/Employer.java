package com.maojm.code.pattern.component;

import java.util.List;

public abstract class Employer {
	private String name;
	public abstract void add(Employer employer);
	public abstract void delete(Employer employer);
	protected List<Employer> employers;
	public String toString(){
		return "employer.name:" + name;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Employer> getEmployers() {
		return employers;
	}
	
}
