package org.sevenstar.persistent.db.cfg.model;

public class RuleModel{
  private String pattern;
  private String find;
  private String packages;

  public String getPackages(){
    return this.packages;
  }

  public void setPackages(String packages) {
     this.packages = packages;
  }

  public String getPattern() {
    return this.pattern;
  }

  public void setPattern(String pattern) {
    this.pattern = pattern;
  }

  public String getFind() {
     return this.find;
  }

  public void setFind(String find) {
    this.find = find;
  }
}
