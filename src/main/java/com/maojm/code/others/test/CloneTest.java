package com.maojm.code.others.test;

public class CloneTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
        try{
		Employee orignal = new Employee("john Q.Public",50000);
        orignal.setHireDay(2000, 1, 1);
        Employee copy = orignal.clone();
        copy.raiseSalary(20);
        copy.setHireDay(1998, 8, 8);
        System.out.println("orignal="+ orignal);
        System.out.println("copy="+ copy);
        }catch(CloneNotSupportedException e){
        	e.printStackTrace();
        }
	}

}
