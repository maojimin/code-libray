package org.sevenstar.component.luence.record;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.sevenstar.component.luence.LuceneHelper;

public final class RecordManagerFactory {
	private Log log = LogFactory.getLog(RecordManagerFactory.class);

	private static List managerList;
	static {
		managerList = new ArrayList();
		/**
		 * 如果是用table,id来更新,这里加上处理的manager
		 */
		// managerList.add(new ContractManager());
	}

	public static void doIndex(RecordIndex recordIndex) throws IOException {
		if (recordIndex.isCreate()) {
			createIndex(recordIndex);
		}
		if (recordIndex.isUpdate()) {
			LuceneHelper.delete(recordIndex.getTable() + "-"
					+ recordIndex.getId());
			createIndex(recordIndex);
		}
		if (recordIndex.isDelete()) {
			LuceneHelper.delete(recordIndex.getTable() + "-"
					+ recordIndex.getId());
		}
	}

	private static void createIndex(RecordIndex recordIndex) throws IOException {

		DefaultRecordManager manager = new DefaultRecordManager(recordIndex);
		manager.createIndex();
	}

	public static void main(String[] args) throws IOException {
		LuceneHelper.indexDir = "d:/luence/index";

		RecordIndex recordIndex = new RecordIndex();
		recordIndex.setSubject("测试 word1");
		recordIndex.setContent("测试 word1");
		recordIndex.setTable("test");
		recordIndex.setType("收文");
		recordIndex.setId("1");
	//	recordIndex.setSecurityValue("酒乃英雄胆");
		
		recordIndex.setCreate();

		RecordIndex recordIndex2 = new RecordIndex();
		recordIndex2.setSubject("测试 excel2");
		recordIndex2.setContent("测试 excel2");
		recordIndex2.setTable("test");
		recordIndex2.setType("发文");
		recordIndex2.setId("2");
		recordIndex2.setCreate();

		RecordManagerFactory.doIndex(recordIndex);
		RecordManagerFactory.doIndex(recordIndex2);

		List typeList = new ArrayList();
		typeList.add(LuceneHelper.SUBJECT);
		typeList.add(LuceneHelper.CONTENT);
		//typeList.add(LuceneHelper.SECURITYVALUE);
	 	//typeList.add(LuceneHelper.TYPE);

//		List result = LuceneHelper.search("测试word", 1, "收文", LuceneHelper.TYPE);
		List result = LuceneHelper.search("测试",typeList, 1,"",LuceneHelper.TYPE);
		System.out.println("result:" + result.size());
		for (int i = 0; i < result.size(); i++) {
			RecordIndex ri = (RecordIndex) result.get(i);
			System.out.println(ri.getContent());
		}

	}

	/*
	 * public static void createIndex(String tableName, String id) throws
	 * IOException { IndexWriter indexWriter = LuceneHelper.getIndexWriter();
	 * try { for (int i = 0; i < managerList.size(); i++) {
	 * AbstractRecordManager manager = (AbstractRecordManager) managerList
	 * .get(i); manager.createIndex(indexWriter, tableName, id); } } finally {
	 * LuceneHelper.closeIndexWriter(indexWriter); } }
	 */
}
