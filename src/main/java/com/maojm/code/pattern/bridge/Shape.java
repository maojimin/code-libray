package com.maojm.code.pattern.bridge;

public abstract class Shape {
	protected IShape ishape;
	protected void initShape(String type){
		try {
			ishape = ShapeImpFactory.getShapeImp(type);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public abstract void drawShape();
}
