package com.maojm.code.others.test;

public class ManagerTest {

	public static void main(String[] args) {
		Manager boss = new Manager("carl cracker", 8000, 1987, 12, 15);
		boss.setBonus(5000);

		Employee[] staff = new Employee[3];

		staff[0] = boss;
		staff[1] = new Employee("Harry Hacker", 5000, 1989, 10, 1);
		staff[2] = new Employee("Tommy Tester", 4000, 1990, 3, 15);

		for (Employee e : staff) {
			System.out.println("name=" + e.getName() + ",Salary= "
					+ e.getSalary());
		}
		staff[0].setBonus(100);
		System.out.println(boss.getBonus());
	}

}

class Manager extends Employee {
	private double bonus;

	public Manager(String n, double s, int year, int month, int day) {
		super(n, s, year, month, day);
		bonus = 0;
	}

	public double getSalary() {
		double baseSalary = super.getSalary();
		return baseSalary + bonus;
	}

	public double getBonus() {
		return bonus;
	}

	public void setBonus(double b) {
		bonus = b;
	}

}
