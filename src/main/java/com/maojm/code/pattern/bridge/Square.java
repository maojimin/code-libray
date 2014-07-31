package com.maojm.code.pattern.bridge;

public class Square extends Shape {

	@Override
	public void drawShape() {
		System.out.println("Square.drawShape 绘制一个正方形...");
	    initShape("unix");
	    ishape.drawLine(new Point(0,0), new Point(10,0));
	    ishape.drawLine(new Point(0,0), new Point(0,10));
	    ishape.drawLine(new Point(0,10), new Point(10,10));
	    ishape.drawLine(new Point(10,0), new Point(10,10));
	}

}
