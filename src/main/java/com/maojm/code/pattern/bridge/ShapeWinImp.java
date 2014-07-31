package com.maojm.code.pattern.bridge;

public class ShapeWinImp implements IShape{

	@Override
	public void drawLine(Point start, Point end) {
		System.out.println("ShapeImpWin.drawLine startPoint=" + start + ",endPoint=" + end);
	}

}
