package org.sevenstar.component.luence.file;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import org.apache.lucene.index.IndexWriter;



/**
 * 索引文本文件
 *
 * @author rtm
 *
 */
public class TextFileIndex extends AbstractFileIndex {

	public void index(IndexWriter indexWriter, FileRecordIndex fileRecord) throws IOException {
		Reader textReader = new FileReader(fileRecord.getFile());
		BufferedReader br = new BufferedReader(textReader);
		String line = br.readLine();
		StringBuffer sb = new StringBuffer();
		while( line != null ){
			sb.append(line);
			line = br.readLine();
		}
		br.close();
		textReader.close();
		indexText( indexWriter,fileRecord, sb.toString());
	}
/*
	public List getKeyWord() throws IOException {
		List list = new ArrayList();
		list.add(FileIndexFactory.CONTENT);
		list.add(FileIndexFactory.PATH);
		list.add(FileIndexFactory.NAME);
		return list;
	}*/

	public boolean check(FileRecordIndex file) {
		if(file.getFile().getName().toLowerCase().endsWith("txt") || file.getFile().getName().toLowerCase().endsWith("text") || file.getFile().getName().toLowerCase().endsWith("ini")){
			return true;
		}
		return false;
	}
}
