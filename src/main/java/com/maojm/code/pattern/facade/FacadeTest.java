package com.maojm.code.pattern.facade;

import junit.framework.TestCase;

public class FacadeTest extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testFacade(){
		Facade facade = new Facade();
		facade.methodA();
		facade.methodB();
		facade.methodC();
	}

}
