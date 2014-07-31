package com.maojm.code.pattern.factory.simplefactory;

public class FruitGardener {
	public static Fruit factory(String fruitName)throws BadFruitException{
		if(fruitName!=null)
			fruitName = fruitName.toLowerCase();
		if ("apple".equals(fruitName)){
			return new Apple();
		}else if ("grape".equals(fruitName)){
			return new Grape();
		}else if("strawberry".equals(fruitName)){
			return new Strawberry();
		}else{
			throw new BadFruitException("no fruit named by " + fruitName);
		}
	}
}
