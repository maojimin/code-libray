 package org.sevenstar.web.cfg.model;
 
 import java.util.ArrayList;
 import java.util.List;
 
 public class ResultTypesModel {
   private List resultTypesList;
 
   public List getResultTypesList() {
     if (this.resultTypesList == null) {
       this.resultTypesList = new ArrayList();
     }
     return this.resultTypesList;
   }
 
   public void setResultTypesList(List resultTypesList) {
     this.resultTypesList = resultTypesList;
   }
 
   public void addResultTypeModel(ResultTypeModel resultTypeModel) {
     getResultTypesList().add(resultTypeModel);
   }
 }

