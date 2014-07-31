package com.maojm.code.concurrent.thread;

import java.util.Date;

public class TestInterrupt {
	public static void main(String[] args){
		MyThread th = new MyThread();
		th.start();
		try{
			Thread.sleep(10000);
		}catch(InterruptedException e){
		}
		th.shutDown();
	}

}

class MyThread extends Thread{

	public boolean flag = true;
	public void run() {
		while(flag){
			System.out.println(new Date());
				try {
					sleep(1000);
				} catch (InterruptedException e) {
					return;
				}
		}
		
	}
	public void shutDown(){
		flag = false;
	}
	
}