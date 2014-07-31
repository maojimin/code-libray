package org.sevenstar.component.luence.file;


import java.io.IOException;

import org.apache.lucene.index.IndexWriter;

public interface IFileIndex {
	/**
	 * 建索引
	 *
	 * @param writer
	 * @param file
	 * @throws IOException
	 */
	public void index(IndexWriter indexWriter,FileRecordIndex file) throws IOException;


	/**
	 * 检查是否索引
	 *
	 * @param file
	 * @return
	 */
	public boolean check(FileRecordIndex file);
}
