package com.maojm.code.io.test;
import java.io.*;
public class TestFileOutputStream
{
	public static void main(String[] args) 
	{
		int b =0;
		FileInputStream in = null;
		FileOutputStream out = null;
		try
		{
			in = new FileInputStream("e:/docs/java/javaTest/Test.java");
			out = new FileOutputStream("e:/docs/java/javaTest/test2.java");
			while ((b=in.read())!=-1)
			{
				out.write(b);
			}
			in.close();
			out.close();

		}
		catch (FileNotFoundException e)
		{
			System.out.println("file not find!");
			System.exit(-1);
		}catch(IOException e){
			System.out.println("ioexception!");
			System.exit(-1);
		}
	}
}
