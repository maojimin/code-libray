 package org.sevenstar.web.cfg.model;
 
 import java.util.ArrayList;
 import java.util.List;
 
 public class InterceptorsModel {
   private List interceptorsList;
 
   public void addInterceptors(InterceptorModel interceptorModel) {
     getInterceptorsList().add(interceptorModel);
   }
 
   public List getInterceptorsList() {
     if (this.interceptorsList == null) {
       this.interceptorsList = new ArrayList();
     }
     return this.interceptorsList;
   }
 
   public void setInterceptorsList(List interceptorsList) {
     this.interceptorsList = interceptorsList;
   }
 }
