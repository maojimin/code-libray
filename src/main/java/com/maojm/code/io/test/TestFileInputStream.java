package com.maojm.code.io.test;
import java.io.*;
public class TestFileInputStream
{
	public static void main(String[] args) 
	{
		int b =0;
		FileInputStream in = null;
		try{

			in = new FileInputStream("e:/docs/java/javaTest/IO/TestFileInputStream.java");
		}catch(FileNotFoundException e){
			System.out.println("File not found!");
			System.exit(-1);
		}
		try{
			long num = 0;
			while((b=in.read())!=-1){
				System.out.print((char)b);
				num++;
			}
		
		in.close();
		System.out.println();
		System.out.println("have read: " + num + "bytes!");
	}catch(IOException e){
		System.out.println("IOException !");
	}

	}
}
