package com.maojm.code.io.test;
import java.io.*;
public class TestDataStream{

	public static void main(String[] args){
		try{
			//���ڴ�����ֽ�����
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			DataOutputStream dos = new DataOutputStream(bos);
			dos.writeDouble(Math.random());
			dos.writeBoolean(true);

			ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
			System.out.println(bis.available());  //���ؿɴӴ���������ȡ��������ʣ���ֽ���
																						//��ӡ 9
			DataInputStream dis = new DataInputStream(bis);
			System.out.println(dis.readDouble());
			System.out.println(bis.available());  //��ӡ1
			System.out.println(dis.readBoolean());
			dis.close();
			dos.close();

		}catch(Exception e){
			System.out.println("Exception!");
		}


	}

}