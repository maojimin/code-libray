package com.maojm.code.pattern.factory.simplefactory;

public class Apple implements Fruit {

	public int treeAge;
	
	@Override
	public void grow() {
		log("apple is growing...");
	}

	@Override
	public void harvest() {
		log("apple has been harvest.");
	}

	@Override
	public void plant() {
		log("Apple has been planted.");
	}
	
	public void log(String msg){
		System.out.println(msg);
	}

	public int getTreeAge() {
		return treeAge;
	}

	public void setTreeAge(int treeAge) {
		this.treeAge = treeAge;
	}

}
