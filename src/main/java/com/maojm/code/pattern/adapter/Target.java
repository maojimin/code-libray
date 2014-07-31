package com.maojm.code.pattern.adapter;

public interface Target {
	/**
	 * 需要适配的类有的方法
	 */
	public void oldMethod();
	/**
	 * 需要适配的类没有的方法
	 */
	public void newMethod();
}
