 package org.sevenstar.web.cfg.model;
 
 import java.util.ArrayList;
 import java.util.List;
 
 public class ResultTypeModel {
   private String name;
   private String className;
   private List resultTypeParamModelList;
 
   public List getResultTypeParamModelList() {
    if (this.resultTypeParamModelList == null) {
       this.resultTypeParamModelList = new ArrayList();
     }
     return this.resultTypeParamModelList;
   }
 
   public void setResultTypeParamModelList(List resultTypeParamModelList) {
     this.resultTypeParamModelList = resultTypeParamModelList;
   }
 
   public void addResultTypeParamModel(ResultTypeParamModel model) {
     getResultTypeParamModelList().add(model);
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

