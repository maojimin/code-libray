package org.sevenstar.persistent.db.cfg.model;

public class SdbModel {
  private String database;
  private String pagesize;
  private SqlmapModel sqlmapModel;
  private FindsModel findsModel;
  private RulesModel rulesModel;

  public RulesModel getRulesModel()
  {
    if (this.rulesModel == null) {
       this.rulesModel = new RulesModel();
    }
     return this.rulesModel;
  }

  public void setRulesModel(RulesModel rulesModel) {
     this.rulesModel = rulesModel;
  }

  public FindsModel getFindsModel() {
     if (this.findsModel == null) {
       this.findsModel = new FindsModel();
    }
     return this.findsModel;
  }

  public void setFindsModel(FindsModel findsModel) {
     this.findsModel = findsModel;
  }

  public SqlmapModel getSqlmapModel() {
     return this.sqlmapModel;
  }

  public void setSqlmapModel(SqlmapModel sqlmapModel) {
     this.sqlmapModel = sqlmapModel;
  }

  public String getDatabase() {
     return this.database;
  }

  public void setDatabase(String database) {
     this.database = database;
  }

  public String getPagesize() {
     return this.pagesize;
  }

  public void setPagesize(String pagesize) {
     this.pagesize = pagesize;
  }
}
