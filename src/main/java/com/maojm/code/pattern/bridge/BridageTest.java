package com.maojm.code.pattern.bridge;

import junit.framework.TestCase;

public class BridageTest extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testBridage(){
	    Shape shape = new Square();
	    shape.drawShape();
	    Shape shape2 = new Triangle();
	    shape2.drawShape();
	}

}
