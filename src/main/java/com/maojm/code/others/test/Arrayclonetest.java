package com.maojm.code.others.test;

public class Arrayclonetest {
	public static void main(String[] args){
		int n=0;
		int[][] x = new int[3][3];
		for(int j=0;j<3;j++){
			for(int i=0;i<3;i++){
			x[j][i]=n;
			n++;
		}
			}
		
		int[][] y=new int[3][3];
		for(int i=0;i<3;i++){
			y[i]=x[i].clone();	
		}
		
		y[1][1]=50;
		for(int j=0;j<3;j++){
			for(int i=0;i<3;i++){
				System.out.print(x[j][i]+"\t");
			}
		}
		
		System.out.println();
		System.out.println("-----------------------------------------------------");

		for(int j=0;j<3;j++){
			for(int i=0;i<3;i++){
				System.out.print(y[j][i]+"\t");
			}
		}
		
				
		}

}
