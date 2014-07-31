
package com.maojm.code.others.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class B_read {
	public static void main(String[] args) throws IOException {
		char ch;
		String strCh;
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		do {
			ch = (char) br.read();
			System.out.println(ch);
			strCh = String.valueOf(ch);
		} while (!"q".equals(strCh) && !"Q".equals(strCh));
	}

}
