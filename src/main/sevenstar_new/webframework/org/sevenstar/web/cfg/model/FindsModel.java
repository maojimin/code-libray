 package org.sevenstar.web.cfg.model;
 
 import java.util.ArrayList;
 import java.util.List;
 
 public class FindsModel {
   private List findList;
 
   public List getFindList() {
     if (this.findList == null) {
       this.findList = new ArrayList();
     }
     return this.findList;
   }
 
   public void setFindList(List findList) {
     this.findList = findList;
   }
 
   public void addFindModel(FindModel findModel) {
     getFindList().add(findModel);
   }
 }

