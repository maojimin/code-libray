package com.maojm.code.io.test;
import java.io.*;
public class  TestTransForm1
{
	public static void main(String[] args) 
	{
		try{
		OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream("e:/javadocs/javatest/io/dbk.txt"));
		osw.write("sssssssssssssss");
		System.out.println(osw.getEncoding());
		osw.close();

		osw = new OutputStreamWriter(new FileOutputStream("e:/javadocs/javatest/io/dbk.txt",false),"iso8859_1");
		osw.write("fsdfdf");
		osw.close();

		}catch(IOException e){
			System.out.println("IOException!");
		}

	}
}
