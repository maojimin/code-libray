package com.maojm.code.pattern.component;

import junit.framework.TestCase;

public class ComponentTest extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testComponent(){
		ProjectManager manager = new ProjectManager("项目经理");
		manager.add(new Programmer("程序员"));
		manager.add(new ProjectAssistant("项目助理"));
		manager.add(new Programmer("程序员2号"));
		for(Employer emp : manager.getEmployers()){
			System.out.println(emp.getName());
		}
		System.out.println("--------------------------------");
		manager.delete(new Programmer("程序员2号"));
		for(Employer emp : manager.getEmployers()){
			System.out.println(emp.getName());
		}
	}

}
