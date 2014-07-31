package com.maojm.code.concurrent.thread;

public class TestJoin {
	public static void main(String[] args) {
		MyThread2 t1 = new MyThread2("t1");
		t1.start();
		try {
			t1.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(int i=0;i<10;i++){
			System.out.println("I am main thread!");
		}
			
	}
}

class MyThread2 extends Thread {
	public MyThread2(String s) {
		super(s);
	}

	@Override
	public void run() {
		for (int i = 0; i < 10; i++) {
			System.out.println("I am " + getName() + " Thread!");
		}
		try {
			sleep(1000);
		} catch (InterruptedException e) {
			return;
		}
	}
}
