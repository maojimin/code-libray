package com.maojm.code.io.test;
import java.io.*;
public class  TestTransForm2
{
	public static void main(String[] args) 
	{
		try{
		BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
		String s = bf.readLine();
		while(s!=null){
			if(s.equalsIgnoreCase("exit")){
				bf.close();
				break;
			}
			System.out.println(s);
			s = bf.readLine();
		}
		}catch(IOException e){
			System.out.println("IOException!");
		}

	}
}
