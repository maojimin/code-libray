package org.sevenstar.component.luence.file;

import java.io.File;
import java.io.Serializable;

import org.sevenstar.component.luence.record.RecordIndex;



public class FileRecordIndex implements Serializable {


	private RecordIndex recordIndex;

	private File file;

	private String url;

	private String filePath;

	private String type;
	/**创建时间/上传时间*/
	private String createdate;



	public FileRecordIndex(RecordIndex recordIndex) {
		this.recordIndex = recordIndex;
	}

	public FileRecordIndex(RecordIndex recordIndex, File file) {
		this.recordIndex = recordIndex;
		this.file = file;
	}

	public RecordIndex getRecordIndex() {
		if (this.recordIndex == null) {
			throw new RuntimeException("recordIndex is null");
		}
		return this.recordIndex;
	}

	public File getFile() {
		if (this.file == null) {
			if (this.filePath != null) {
				this.file = new File(filePath);
				if (!file.exists()) {
					throw new RuntimeException("file is not exist");
				}
			} else {
				throw new RuntimeException("file is null");
			}
		}
		return this.file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public void setRecordIndex(RecordIndex recordIndex) {
		this.recordIndex = recordIndex;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}



	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}


	public String getCreatedate() {
		return createdate;
	}

	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}
}
