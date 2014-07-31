package org.sevenstar.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class FileHelper {

	public static void changeToDic(File sourceFile, File toDic) {
		copyToDic(sourceFile,toDic);
		sourceFile.delete();
	}

	public static void copyToDic(File sourceFile, File toDic) {
       String fileName = sourceFile.getName();
       copy(sourceFile,new File(toDic.getPath()+File.separator+fileName));
	}

	public static void copy(File sourceFile, File toFile) {
		try {
			if (!toFile.exists()) {
				toFile.createNewFile();
			}
			FileInputStream fis = new FileInputStream(sourceFile);
			FileOutputStream fos = new FileOutputStream(toFile);
			int i = fis.read();
			while (i != -1) {
				fos.write(i);
				i = fis.read();
			}
			fis.close();
			fos.close();
		} catch (Exception e) {
           throw new RuntimeException(e);
		}
	}

	public static void main(String[] args){
		File file = new File("d:/program/");
		System.out.println(File.separator);
	}
}
