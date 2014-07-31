package com.maojm.code.others.test;

public class EnumOperate {
	public enum myColor{red,green,blue};
	public static void main(String[] args){
		myColor mc = myColor.red;
		switch(mc){
			case red:
				System.out.println("red");
				break;
			case green:
				System.out.println("green");
				break;
			case blue:
				System.out.println("blue");
				break;
		}
	}
}
