package com.maojm.code.others.test;

import java.util.*;

class formant_lx {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		String name = in.nextLine();
		int age = in.nextInt();
		String message = String.format("hello,%s. next year, you'll be %d.",
				name, (age + 1));
		System.out.println(message);
	}
}
