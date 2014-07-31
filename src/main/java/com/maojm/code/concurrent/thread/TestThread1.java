package com.maojm.code.concurrent.thread;

public class TestThread1{
	public static void main(String[] args){
		Runner1 r = new Runner1();
		Thread t = new Thread(r);
		t.start();
		for(int i=0;i<100;i++){
			System.out.println("main thread: " + i);
		}
		
	}
	
}

class Runner1 implements Runnable{
	
	public void run(){
		for(int i=0;i<100;i++)
			System.out.println("runner1: " + i);
	}
	
}