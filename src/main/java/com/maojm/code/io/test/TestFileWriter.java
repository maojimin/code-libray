package com.maojm.code.io.test;

import java.io.*;
public class  TestFileWriter
{
	public static void main(String[] args) 
	{
		try{
			FileReader in = new FileReader("e:/JavaDocs/javaTest/test2.java");
			FileWriter out = new FileWriter("e:/JavaDocs/javaTest/test3.java");
			int b=0;
			while ((b=in.read())!=-1)
			{
				out.write(b);
			}		
		in.close();
		out.close();

		}catch(Exception e){
			System.out.println("error");
		}
	}
}
