package com.maojm.code.pattern.bridge;

public class ShapeImpFactory {
	public static IShape getShapeImp(String type)throws Exception{
		if("win".equals(type)){
			return new ShapeWinImp();
		}else if("unix".equals(type)){
			return new ShapeUnixImp();
		}else{
			throw new Exception("this type is not implemention.");
		}
	}
}
