package org.sevenstar.component.luence.file;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.index.IndexWriter;

import org.sevenstar.component.luence.record.RecordIndex;
import org.sevenstar.component.luence.LuceneHelper;

public final class FileIndexFactory {

	public static Log log = LogFactory.getLog(FileIndexFactory.class);

	/*
	 * public final static String CONTENT = "content";
	 * 
	 * public final static String NAME = "name";
	 * 
	 * public final static String PATH = "path";
	 */

	public static List indexDealList;

	static {
		indexDealList = new ArrayList();
		indexDealList.add(new TextFileIndex());
		indexDealList.add(new PdfFileIndex());
		indexDealList.add(new DocFileIndex());
		indexDealList.add(new HtmlFileIndex());
	}

	public static void main(String[] args) {
	
		LuceneHelper.indexDir = "d:/luence/index";
	 
		RecordIndex recordIndex = new RecordIndex();
		recordIndex.setSubject("测试word");
		recordIndex.setContent("word");
		recordIndex.setTable("test");
		recordIndex.setId("1");
		recordIndex.setEgov_dept("浙江鸿程");
		recordIndex.setEgov_no("333");
		recordIndex.setSecurityValue("浙江鸿程");
		//FileRecordIndex file = new FileRecordIndex(recordIndex);
	//	file.setFilePath("D:/luence/data/ApacheTomcat整合教程.doc");
		FileRecordIndex file1 = new FileRecordIndex(recordIndex);
		file1.setFilePath("D:/luence/data/osworkflow_doc_cn_v2.0.pdf");
		List list = new ArrayList();
	//	list.add(file);
		list.add(file1);
		IndexWriter indexWriter = LuceneHelper.getIndexWriter();
		try {
			index(indexWriter, list);
		} finally {
			LuceneHelper.closeIndexWriter(indexWriter);
		}
		List typeList = new ArrayList();
		typeList.add(LuceneHelper.SUBJECT);
		typeList.add(LuceneHelper.CONTENT);
		typeList.add(LuceneHelper.EGOV_NO);
	 	typeList.add(LuceneHelper.EGOV_DEPT);
	 	typeList.add(LuceneHelper.SECURITYVALUE);
		 
		List result = LuceneHelper.search("333",typeList,1,"鸿程",LuceneHelper.SECURITYVALUE);
	
		System.out.println("first:" + result.size());
		 	for (int i = 0; i < result.size(); i++) {
			RecordIndex ri = (RecordIndex) result.get(i);
			System.out.println(ri.getSubject());
		} 
	 
		LuceneHelper.delete("test-1");
		result =  LuceneHelper.search("333",typeList,1);
	    System.out.println("second:"+result.size());
		 

	}

	public final static void index(IndexWriter indexWriter, List fileRecordList) {

		for (int i = 0; i < fileRecordList.size(); i++) {
			FileRecordIndex file = (FileRecordIndex) fileRecordList.get(i);
			index(indexWriter, file);
		}

	}

	public final static void index(IndexWriter indexWriter, FileRecordIndex file) {
		for (int j = 0; j < indexDealList.size(); j++) {
			IFileIndex index = (IFileIndex) indexDealList.get(j);
			if (index.check(file)) {
				try {
					index.index(indexWriter, file);
					break;
				} catch (Exception e) {
					e.printStackTrace();
					throw new RuntimeException(e);
				}
			}
		}
	}
}
