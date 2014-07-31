package com.maojm.code.pattern.bridge;

public class ShapeUnixImp implements IShape {

	@Override
	public void drawLine(Point start, Point end) {
		 System.out.println("ShapeImpUnix.drawLine startPoint=" + start + ",endPoint=" + end);
	}

}
