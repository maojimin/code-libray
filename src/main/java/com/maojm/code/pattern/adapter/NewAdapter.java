package com.maojm.code.pattern.adapter;

public class NewAdapter implements Target{
	private Old old;
	public NewAdapter(Old old){
		this.old = old;
	}
	@Override
	public void newMethod() {
		System.out.println("NewAdapter.newMethod()");
	}
	@Override
	public void oldMethod() {
		old.oldMethod();
	}

}
