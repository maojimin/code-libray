 package org.sevenstar.web.cfg.model;
 
 import java.util.ArrayList;
 import java.util.List;
 
 public class ResourceModel {
   private String name;
   private String className;
   private List paramModelList;
 
   public void addParamModel(ResourceParamModel paramModel) {
       if (this.paramModelList == null) {
       this.paramModelList = new ArrayList();
     }
     this.paramModelList.add(paramModel);
   }
 
   public List getParamModelList() {
     if (this.paramModelList == null) {
       this.paramModelList = new ArrayList();
     }
     return this.paramModelList;
   }
 
   public void setParamModelList(List paramModelList) {
     this.paramModelList = paramModelList;
   }
 
   public String getName() {
     return this.name;
   }
 
   public void setName(String name) {
     this.name = name;
   }
 
   public String getClassName() {
     return this.className;
   }
 
   public void setClassName(String className) {
     this.className = className;
   }
 }
