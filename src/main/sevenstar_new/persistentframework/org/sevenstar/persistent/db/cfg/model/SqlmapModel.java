package org.sevenstar.persistent.db.cfg.model;

public class SqlmapModel{
  private String packages;
  private String patterns;

  public String getPackages(){
     return this.packages;
  }

  public void setPackages(String packages) {
     this.packages = packages;
  }

  public String getPatterns() {
    return this.patterns;
  }

  public void setPatterns(String patterns) {
   this.patterns = patterns;
  }
}
