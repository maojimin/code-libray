package com.maojm.code.io.test;

import java.io.*;
public class  TestBufferStream1
{
	public static void main(String[] args)
	{
		try{
			FileInputStream fis = new FileInputStream("e:/JavaDocs/javaTest/IO/dbk.txt");
			BufferedInputStream bfs = new BufferedInputStream(fis);
			int c = 0;
			System.out.println(bfs.read());
			System.out.println(bfs.read());
			bfs.mark(8);
			for (int i=0;i<10 && (c=bfs.read())!=-1 ;i++ )
			{
				System.out.print((char)c);
			}
			bfs.reset();
			for (int i=0;i<10 && (c=bfs.read())!=-1 ;i++ )
			{
				System.out.print((char)c);
			}
		}catch(FileNotFoundException e){
			System.out.println("file not find!");

		}catch(IOException e){
			System.out.println("IOException!");
		}


	}
}
