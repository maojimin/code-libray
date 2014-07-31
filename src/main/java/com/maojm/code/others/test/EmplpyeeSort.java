package com.maojm.code.others.test;

import java.util.*;

public class EmplpyeeSort {
	public static void main(String[] args) {
		Employee[] staff = new Employee[3];

		staff[0] = new Employee("carl cracker", 8000);
		staff[1] = new Employee("Harry Hacker", 5000);
		staff[2] = new Employee("Tommy Tester", 4000);
		Arrays.sort(staff);
		for (Employee e : staff) {
			System.out.println("name = " + e.getName() + "  salary = "
					+ e.getSalary());
		}
	}
}
