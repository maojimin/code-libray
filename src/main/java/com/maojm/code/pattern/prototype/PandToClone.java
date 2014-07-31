package com.maojm.code.pattern.prototype;

public class PandToClone implements Cloneable {
	private String name;
	public PandToClone(String _name){
		name = _name;
	}
	public Object clone(){
		PandToClone obj = new PandToClone(name);
		return (Object)obj;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if (obj instanceof PandToClone){
			if(this.name==null){
				this.name = "";
			}
			if(this.name.equals(((PandToClone)obj).getName())){
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
		
	}
	
}
