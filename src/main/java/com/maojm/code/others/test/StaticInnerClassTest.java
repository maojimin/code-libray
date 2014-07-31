package com.maojm.code.others.test;

public class StaticInnerClassTest {
public static void main(String[] args){
	double[] d = new double[20];
	for (int i=0;i<d.length;i++){
		d[i]=100*Math.random();
	}
	ArrayAlg.Pair p =  ArrayAlg.maxmin(d);
	System.out.println("min= "+p.getMin());
	System.out.println("max= "+p.getMax());
	
}
}
class ArrayAlg
{
	public static class Pair
	{
	   private double max=0,min=0;
	   public Pair(double max,double min){
		  this.max=max;
		  this.min=min;
	   }
	   public double getMax(){
		   return max;
	   }
	   public double getMin()
	   {
		   return min;
	   }
		
	}
	public static Pair maxmin(double... d){
		double min=Double.MAX_VALUE,max=Double.MIN_VALUE;
		for (double v : d){
	         if (max<v)max=v;
	         if(min>v)min=v;
		}
		return new Pair(max,min);
	}
	
	
}

	

