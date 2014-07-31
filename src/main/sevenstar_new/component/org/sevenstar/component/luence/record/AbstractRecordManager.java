package org.sevenstar.component.luence.record;

import java.io.IOException;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;

import org.sevenstar.component.luence.file.FileIndexFactory;
import org.sevenstar.component.luence.LuceneHelper;

public abstract class AbstractRecordManager {
	protected abstract String getTableName();

	protected abstract String getId();

	private boolean check(String tableName) {
		if (tableName != null && tableName.equals(getTableName())) {
			return true;
		}
		return false;
	}

	public abstract RecordIndex getRecordIndex();

	public void createIndex(IndexWriter writer, String tableName, String id)
			throws IOException {
		if (check(tableName)) {
			if (getId() == null || "".equals(getId())) {
				throw new RuntimeException("id is null");
			}
			createIndex(getRecordIndex());
		}
	}

	public void createIndex(RecordIndex record) throws IOException {
		IndexWriter indexWriter = LuceneHelper.getIndexWriter();
		// 只有subject,content,description,filename,filedescription等有的情况下才写
		// recordindex

		try {
			Document document = new Document();
			if (record.getContent() != null) {
				document.add(new Field(LuceneHelper.CONTENT, record
						.getContent(), Field.Store.YES, Field.Index.ANALYZED));
			}
			if (record.getUrl() != null) {
				document.add(new Field(LuceneHelper.URL, record.getUrl(),
						Field.Store.YES, Field.Index.NOT_ANALYZED));
			}
			if (record.getDescription() != null) {

				document.add(new Field(LuceneHelper.DESCRIPTION, record
						.getDescription(), Field.Store.YES,
						Field.Index.ANALYZED));
			}
			if (record.getId() != null) {
				document.add(new Field(LuceneHelper.ID, record.getId(),
						Field.Store.YES, Field.Index.NOT_ANALYZED));
			}
			if (record.getTable() != null) {

				document.add(new Field(LuceneHelper.TABLE, record.getTable(),
						Field.Store.YES, Field.Index.NOT_ANALYZED));
			}
			if (record.getTable() != null && record.getId() != null) {

				document.add(new Field(LuceneHelper.TABLE_ID, record.getTable()
						+ "-" + record.getId(), Field.Store.YES,
						Field.Index.NOT_ANALYZED));
			}
			if (record.getType() != null) {
				document.add(new Field(LuceneHelper.TYPE, record.getType(),
						Field.Store.YES, Field.Index.ANALYZED));
			}

			if (record.getSecurityValue() != null) {

				document.add(new Field(LuceneHelper.SECURITYVALUE, record
						.getSecurityValue(), Field.Store.YES,
						Field.Index.ANALYZED));
			}
			if (record.getSubject() != null) {

				document.add(new Field(LuceneHelper.SUBJECT, record
						.getSubject(), Field.Store.YES, Field.Index.ANALYZED));
			}
			if (record.getUserId() != null) {

				document.add(new Field(LuceneHelper.USERID, record.getUserId(),
						Field.Store.YES, Field.Index.NOT_ANALYZED));
			}
			if (record.getCreateDate() != null) {

				document.add(new Field(LuceneHelper.CREATEDATE, record
						.getCreateDate(), Field.Store.YES,
						Field.Index.NOT_ANALYZED));
			}
			if (record.getModifyDate() != null) {

				document.add(new Field(LuceneHelper.MODIFYDATE, record
						.getModifyDate(), Field.Store.YES,
						Field.Index.NOT_ANALYZED));
			}
			
			 
			if(record.getEgov_no() != null){
				document.add(new Field(LuceneHelper.EGOV_NO, record
						.getEgov_no(), Field.Store.YES,
						Field.Index.NOT_ANALYZED));
			}
			
			if(record.getEgov_dept() != null){
				document.add(new Field(LuceneHelper.EGOV_DEPT, record
						.getEgov_dept(), Field.Store.YES,
						Field.Index.ANALYZED));
			}


			indexWriter.addDocument(document);
			// indexWriter.optimize();

			if (record.getFileRecordList() != null
					&& record.getFileRecordList().size() > 0) {
				FileIndexFactory.index(indexWriter, record.getFileRecordList());
			}
		} finally {
			LuceneHelper.closeIndexWriter(indexWriter);
		}
	}
}
