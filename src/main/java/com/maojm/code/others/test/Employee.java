package com.maojm.code.others.test;

import java.util.*;

public class Employee implements Comparable<Employee>, Cloneable {
	private String name;
	private double salary;
	private Date hireDay;

	public Employee clone() throws CloneNotSupportedException {
		// call object.clone();
		Employee cloned = (Employee) super.clone();

		// clone mutable fields;
		cloned.hireDay = (Date) hireDay.clone();
		return cloned;
	}

	public Employee(String n, double s, int year, int month, int day) {
		this.name = n;
		this.salary = s;
		GregorianCalendar calendar = new GregorianCalendar(year, month - 1, day);
		hireDay = calendar.getTime();
	}

	public Employee(String n, double s) {
		name = n;
		salary = s;
	}

	public String toString() {
		return "Employee[name = " + name + " salary = " + salary
				+ " hireDay = " + hireDay +"]";
	}

	public void setBonus(double b) {

	}

	public String getName() {
		return name;
	}

	public double getSalary() {
		return salary;
	}

	public Date getHireDay() {
		return hireDay;
	}

	public void setHireDay(int year, int month, int day) {
		this.hireDay = new GregorianCalendar(year, month - 1, day).getTime();
	}

	public void raiseSalary(double byPercent) {
		double raise = salary * byPercent / 100;
		salary += raise;
	}

	public int compareTo(Employee o) {
		if (salary < o.salary)
			return -1;
		if (salary > o.salary)
			return 1;
		return 0;
	}
}