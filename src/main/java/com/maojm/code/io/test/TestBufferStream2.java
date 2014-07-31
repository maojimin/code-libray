package com.maojm.code.io.test;
import java.io.*;
public class  TestBufferStream2
{
	public static void main(String[] args) 
	{
		try{
			BufferedReader bf = new BufferedReader(new FileReader("e:/javadocs/javatest/io/dbk.txt"));
			BufferedWriter bw = new BufferedWriter(new FileWriter("e:/javadocs/javatest/io/dbk2.txt"));
			for (int i=0; i<100;i++ )
			{
				String s = String.valueOf(Math.random());
				bw.write(s);
				bw.newLine();
			}
			bw.flush();
			String s1 = null;
			while((s1=bf.readLine())!=null){
				System.out.println(s1);
			}
			bw.close();
			bw.close();
		}catch(FileNotFoundException e){
			System.out.println("File not find!");
		}catch(IOException e){
			System.out.println("IOException!");
		}

	}
}
