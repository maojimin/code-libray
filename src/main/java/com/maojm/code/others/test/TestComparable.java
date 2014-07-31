package com.maojm.code.others.test;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class TestComparable {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		List<Name> l1 = new LinkedList<Name>();
		l1.add(new Name("Joon", "abc"));
		l1.add(new Name("jim", "dbc"));
		l1.add(new Name("yuon", "qbc"));
		l1.add(new Name("ppon", "kbc"));
		l1.add(new Name("poon", "nbc"));
		Collections.sort(l1);
		System.out.println(l1);

	}


}

class Name implements Comparable<Name> {
	private String firstName, lastName;

	public Name(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}

	@Override
	public String toString() {

		return this.firstName + " " + this.lastName;
	}

	public int compareTo(Name o) {
		int lastRet = this.lastName.compareTo(o.lastName);

		return lastRet != 0 ? lastRet : this.firstName.compareTo(o.firstName);
	}

}
