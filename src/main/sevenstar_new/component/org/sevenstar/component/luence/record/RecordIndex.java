package org.sevenstar.component.luence.record;

import java.io.Serializable;
import java.util.ArrayList;
import org.sevenstar.component.luence.file.FileRecordIndex;

public class RecordIndex implements Serializable {
	
  private String isDeleted;
  private String operate;
  private String subject;
  private String egov_no;
  private String egov_dept;
  private String content;
  private String description;
  private String securityValue;
  private String url;
  private String id;
  private String table;
  private String type;
  private String createDate;
  private String modifyDate;
  private String userId;
  private ArrayList fileRecordList;

  public void setCreate(){
    this.operate = "create";
  }

  public boolean isCreate()
  {
     return "create".equals(this.operate);
  }

  public void setUpdate()
  {
    this.operate = "update";
  }

  public boolean isUpdate()
  {
     return "update".equals(this.operate);
  }

  public void setDelete()
  {
     this.operate = "delete";
  }

  public boolean isDelete()
  {
    return "delete".equals(this.operate);
  }

  public String getEgov_no()
  {
    return this.egov_no;
  }

  public void setEgov_no(String egov_no) {
     this.egov_no = egov_no;
  }

  public String getEgov_dept() {
     return this.egov_dept;
  }

  public void setEgov_dept(String egov_dept) {
     this.egov_dept = egov_dept;
  }

  public String getSubject()
  {
     return this.subject;
  }

  public String getContent()
  {
     return this.content;
  }

  public String getDescription()
  {
     return this.description;
  }

  public String getSecurityValue()
  {
     return this.securityValue;
  }

  public String getUrl()
  {
     return this.url;
  }

  public String getId()
  {
    return this.id;
  }

  public String getTable()
  {
    return this.table;
  }

  public String getType()
  {
    return this.type;
  }

  public String getCreateDate()
  {
    return this.createDate;
  }

  public String getModifyDate()
  {
    return this.modifyDate;
  }

  public ArrayList getFileRecordList()
  {
     return this.fileRecordList;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public void setCreateDate(String createDate) {
     this.createDate = createDate;
  }

  public void setDescription(String description) {
     this.description = description;
  }

  public void setFileRecordList(ArrayList fileRecordList) {
     this.fileRecordList = fileRecordList;
  }

  public void addFileRecord(FileRecordIndex fileRecordIndex) {
    if (this.fileRecordList == null) {
       this.fileRecordList = new ArrayList();
    }
     this.fileRecordList.add(fileRecordIndex);
  }

  public void setId(String id) {
     this.id = id;
  }

  public void setModifyDate(String modifyDate) {
    this.modifyDate = modifyDate;
  }

  public void setSecurityValue(String securityValue) {
     this.securityValue = securityValue;
  }

  public void setSubject(String subject) {
    this.subject = subject;
  }

  public void setTable(String table) {
    this.table = table;
  }

  public void setType(String type) {
    this.type = type;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getIsDeleted()
  {
    return this.isDeleted;
  }

  public void setIsDeleted(String isDeleted) {
    this.isDeleted = isDeleted;
  }

  public String getUserId() {
    return this.userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }
}
