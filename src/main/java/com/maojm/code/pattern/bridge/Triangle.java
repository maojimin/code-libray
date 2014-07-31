package com.maojm.code.pattern.bridge;

public class Triangle extends Shape {

	@Override
	public void drawShape() {
		System.out.println("Triangle.drawShape 绘制一个三角形...");
	    initShape("win");
	    ishape.drawLine(new Point(0,0), new Point(10,0));
	    ishape.drawLine(new Point(0,0), new Point(5,10));
	    ishape.drawLine(new Point(5,10), new Point(10,0));
	}

}
