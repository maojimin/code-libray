package org.sevenstar.persistent.db.model;

import java.util.ArrayList;
import java.util.List;

public class CacheModel
{
  private String type;
  private String readonly = "false";
  private String serialize = "false";
  private String cachesize;
  private int flushinterval;
  private String flushonexecute;
  private boolean selectAll = false;
  private String simpleClassName;

  public String getSimpleClassName()
  {
    return this.simpleClassName;
  }

  public void setSimpleClassName(String simpleClassName) {
    this.simpleClassName = simpleClassName;
  }

  public boolean isSelectAll() {
    return this.selectAll;
  }

  public void setSelectAll(boolean selectAll) {
    this.selectAll = selectAll;
  }

  public String getType() {
    return this.type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getCachesize() {
    if ((this.cachesize == null) || ("".equals(this.cachesize)))
    {
      this.cachesize = "1000";
    }
    return this.cachesize;
  }

  public void setCachesize(String cachesize) {
    this.cachesize = cachesize;
  }

  public int getFlushinterval() {
    if (this.flushinterval == 0)
    {
      this.flushinterval = 10;
    }
    return this.flushinterval;
  }

  public void setFlushinterval(int flushinterval) {
    this.flushinterval = flushinterval;
  }

  public String getFlushonexecute() {
    if ((this.flushonexecute == null) || ("".equals(this.flushonexecute)))
    {
      this.flushonexecute = 
        (getSimpleClassName() + "_base_insert," + 
        getSimpleClassName() + "_base_update," + 
        getSimpleClassName() + "_base_delete");
    }
    return this.flushonexecute;
  }

  public void setFlushonexecute(String flushonexecute) {
    this.flushonexecute = flushonexecute;
  }

  public List getflushonexecuteList() {
    String ss = getFlushonexecute();
    List list = new ArrayList();
    if ((ss == null) || ("".equals(ss.trim()))) {
      return list;
    }
    if (ss.indexOf(",") == -1) {
      list.add(ss);
      return list;
    }
    String[] sss = ss.split(",");
    for (int i = 0; i < sss.length; i++) {
      list.add(sss[i]);
    }
    return list;
  }

  public void setReadonly(String readonly)
  {
    if ((readonly == null) || ("".equals(readonly.trim()))) {
      return;
    }
    this.readonly = readonly;
  }

  public String getReadonly()
  {
    return this.readonly;
  }

  public String getSerialize() {
    return this.serialize;
  }

  public void setSerialize(String serialize) {
    if ((serialize == null) || ("".equals(serialize.trim()))) {
      return;
    }
    this.serialize = serialize;
  }
}