package org.sevenstar.component.luence.record;

import org.apache.lucene.document.Document;

import org.sevenstar.component.luence.LuceneHelper;

public class DocumentRecordIndex extends RecordIndex {
	private Document doc;
    public DocumentRecordIndex(Document doc){
    	this.doc = doc;
    	init();
    }
	protected void init() {
		this.setContent( LuceneHelper.getFieldValue(this.doc,LuceneHelper.CONTENT));
		this.setCreateDate(LuceneHelper.getFieldValue(this.doc,LuceneHelper.CREATEDATE));
		this.setDescription(LuceneHelper.getFieldValue(this.doc,LuceneHelper.DESCRIPTION));
		this.setId(LuceneHelper.getFieldValue(this.doc,LuceneHelper.ID));
		this.setModifyDate(LuceneHelper.getFieldValue(this.doc,LuceneHelper.MODIFYDATE));
		this.setSecurityValue(LuceneHelper.getFieldValue(this.doc,LuceneHelper.SECURITYVALUE));
		this.setSubject(LuceneHelper.getFieldValue(this.doc,LuceneHelper.SUBJECT));
		this.setTable(LuceneHelper.getFieldValue(this.doc,LuceneHelper.TABLE));
		this.setType(LuceneHelper.getFieldValue(this.doc,LuceneHelper.TYPE));
		this.setUrl(LuceneHelper.getFieldValue(this.doc,LuceneHelper.URL));
		this.setUserId(LuceneHelper.getFieldValue(this.doc,LuceneHelper.USERID));
		this.setEgov_no(LuceneHelper.getFieldValue(this.doc,LuceneHelper.EGOV_NO));
		this.setEgov_dept(LuceneHelper.getFieldValue(this.doc,LuceneHelper.EGOV_DEPT));
 	}

}
