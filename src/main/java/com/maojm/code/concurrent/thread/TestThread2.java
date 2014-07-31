package com.maojm.code.concurrent.thread;

public class TestThread2 {
	public static void main(String[] args){
		Runner2 r = new Runner2();
		r.start();
		for(int i=0;i<100;i++){
			System.out.println("main thread: " + i);
		}
		
	}
	
}

class Runner2 extends Thread{
	
	public void run(){
		for(int i=0;i<100;i++)
			System.out.println("runner1: " + i);
	}
}
