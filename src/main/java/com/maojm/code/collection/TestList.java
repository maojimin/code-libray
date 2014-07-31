package com.maojm.code.collection;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class TestList {
	public static void main(String[] args){
		List<String> l1 = new LinkedList<String>();
		List<Object> l2 = new LinkedList<Object>();
		for(int i=0;i<9;i++){
			l1.add("a"+i);
		}
		l2.add(l1);
		//Collections.fill(l2, l1);
		Collections.shuffle(l1);
		System.out.println(l1);
		Collections.reverse(l1);
		System.out.println(l1);
		Collections.sort(l1);
		System.out.println(l1);
		System.out.println(l2);
		
		System.out.println(Collections.binarySearch(l1, "a8"));
		
		
	}
}
