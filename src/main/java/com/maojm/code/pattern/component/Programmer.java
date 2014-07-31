package com.maojm.code.pattern.component;

public class Programmer extends Employer{

	public Programmer(String name){
		setName(name);
		employers = null;
	}
	
	@Override
	public void add(Employer employer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Employer employer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Employer){
			return this.getName().equals(((Employer)obj).getName());
		}else{
			return false;
		}
		
	}
	
	

}
