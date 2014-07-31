package com.maojm.code.collection;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class TestSet {
	public static void main(String[] args){
		Set<String> s1 = new HashSet<String>();
		Set<String> s2 = new HashSet<String>();
		s1.add("a");
		s1.add("b");
		s1.add("c");
		s2.add("b");
		s2.add("d");
		s2.add("a");
		Set<String> sn = new HashSet<String>(s1);
		Set<String> su = new HashSet<String>(s1);
		sn.retainAll(s2);
		su.addAll(s2);
		System.out.println(sn);
		System.out.println(su);
		
		Iterator<String> it = s2.iterator();
		while(it.hasNext()){
			String s = it.next();
			System.out.println(s);
		}
		
		
	}
}
