package org.sevenstar.component.luence.record;

import java.io.IOException;


public class DefaultRecordManager extends AbstractRecordManager {

	private  RecordIndex recordIndex;

	public  DefaultRecordManager(RecordIndex recordIndex) {
		this.recordIndex = recordIndex;
	}

	public void createIndex() throws IOException{
		super.createIndex(this.recordIndex);
	}

	protected String getTableName() {
		return recordIndex.getTable();
	}

	protected String getId() {
		return String.valueOf(recordIndex.getId());
	}

	public RecordIndex getRecordIndex() {
	  return recordIndex;
	}

}
