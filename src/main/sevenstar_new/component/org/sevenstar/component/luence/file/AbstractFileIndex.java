package org.sevenstar.component.luence.file;

import java.io.IOException;


import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;

import org.sevenstar.component.luence.LuceneHelper;



public abstract class AbstractFileIndex implements IFileIndex {

	public abstract void index(IndexWriter indexWriter, FileRecordIndex file)
			throws IOException;

	public abstract boolean check(FileRecordIndex file);

	protected void indexText(IndexWriter indexWriter,
			FileRecordIndex fileRecord, String str) throws IOException {
		Document document = new Document();
 		document.add(new Field(LuceneHelper.CONTENT, str, Field.Store.YES,
        Field.Index.ANALYZED));
		if (fileRecord.getUrl() != null) {
			document.add(new Field(LuceneHelper.URL, fileRecord.getUrl(), Field.Store.YES,
			        Field.Index.NOT_ANALYZED));
 		} else {
			if (fileRecord.getRecordIndex().getUrl() != null) {
				document.add(new Field(LuceneHelper.URL, fileRecord
						.getRecordIndex().getUrl(), Field.Store.YES,
				        Field.Index.NOT_ANALYZED));
			}
		}
		if (fileRecord.getRecordIndex().getId() != null) {
			document.add(new Field(LuceneHelper.ID, fileRecord.getRecordIndex().getId(), Field.Store.YES,
			        Field.Index.NOT_ANALYZED));
		}
		if (fileRecord.getRecordIndex().getSubject() != null) {
			//如果不是文档的附件，而是新闻等其他的附件
			document.add(new Field(LuceneHelper.SUBJECT, fileRecord.getRecordIndex().getSubject(), Field.Store.YES,
			        Field.Index.ANALYZED));
 		}
		if (fileRecord.getRecordIndex().getTable() != null) {
			document.add(new Field(LuceneHelper.TABLE, fileRecord
					.getRecordIndex().getTable(), Field.Store.YES,
			        Field.Index.NOT_ANALYZED));
		}
		if (fileRecord.getRecordIndex().getTable() != null
				&& fileRecord.getRecordIndex().getId() != null) {
			document.add(new Field(LuceneHelper.TABLE_ID,  fileRecord
					.getRecordIndex().getTable()
					+ "-" + fileRecord.getRecordIndex().getId(), Field.Store.YES,
			        Field.Index.NOT_ANALYZED));
		}
		if (fileRecord.getType() != null) {
			document.add(new Field(LuceneHelper.TYPE, fileRecord.getType(), Field.Store.YES,
			        Field.Index.ANALYZED));
 		}

		if (fileRecord.getRecordIndex().getModifyDate() != null) {
			document.add(new Field(LuceneHelper.MODIFYDATE, fileRecord.getRecordIndex().getModifyDate(), Field.Store.YES,
			        Field.Index.NOT_ANALYZED));
		}
		if (fileRecord.getRecordIndex().getCreateDate() != null)
		{
			document.add(new Field(LuceneHelper.CREATEDATE, fileRecord
					.getRecordIndex().getCreateDate(), Field.Store.YES,
					Field.Index.NOT_ANALYZED));
		}
		if (fileRecord.getRecordIndex().getUserId() != null)
		{
			document.add(new Field(LuceneHelper.USERID, fileRecord
					.getRecordIndex().getUserId(), Field.Store.YES,
					Field.Index.NOT_ANALYZED));
		}
		
		if(fileRecord.getRecordIndex().getEgov_no() != null){
			document.add(new Field(LuceneHelper.EGOV_NO, fileRecord.getRecordIndex()
					.getEgov_no(), Field.Store.YES,
					Field.Index.NOT_ANALYZED));
		}
		
		if(fileRecord.getRecordIndex().getEgov_dept() != null){
			document.add(new Field(LuceneHelper.EGOV_DEPT, fileRecord.getRecordIndex()
					.getEgov_dept(), Field.Store.YES,
					Field.Index.ANALYZED));
		}
		indexWriter.addDocument(document);
	//	indexWriter.optimize();
	}

}
