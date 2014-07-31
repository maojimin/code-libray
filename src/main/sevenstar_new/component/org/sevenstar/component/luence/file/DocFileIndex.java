package org.sevenstar.component.luence.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.apache.lucene.index.IndexWriter;
import org.apache.poi.hwpf.extractor.WordExtractor;

/**
 * 索引word文档
 * 
 * @author rtm
 * 
 */
public class DocFileIndex extends AbstractFileIndex {

	public void index(IndexWriter indexWriter, FileRecordIndex fileRecord)
			throws IOException {
		try {
			/*
			 * Document doc = new Document(fileRecord.getFilePath());
			 */
			FileInputStream in = new FileInputStream(new File(fileRecord
					.getFilePath()));
			try {
				WordExtractor extractor = new WordExtractor(in);
				indexText(indexWriter, fileRecord, extractor.getText());
			} finally {
				if (in != null) {
					try {
						in.close();
					} catch (Throwable e) {
						//
					}
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public boolean check(FileRecordIndex file) {
		if (file.getFile().getName().toLowerCase().endsWith("doc")) {
			return true;
		}
		return false;
	}

	public static void main(String[] args) throws IOException {
		FileInputStream in = new FileInputStream(new File(
				"C:/Documents and Settings/zxf/桌面/附件/$AttFile$_1256608417156.doc"));
		WordExtractor extractor = new WordExtractor(in);
		System.out.println(extractor.getText());
		in.close();
	}

}
