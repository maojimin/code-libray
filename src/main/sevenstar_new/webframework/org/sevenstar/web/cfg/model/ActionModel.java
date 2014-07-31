 package org.sevenstar.web.cfg.model;
 
 public class ActionModel {
   /*private UrlModel urlModel;
 
   public void setUrlModel(UrlModel urlModel){
     this.urlModel = urlModel;
   }
 
   public UrlModel getUrlModel() {
     if (this.urlModel == null) {
       this.urlModel = new UrlModel();
     }
     return this.urlModel;
   }*/
	private UrlModel urlModel;

	public void setUrlModel(UrlModel urlModel) {
		this.urlModel = urlModel;
	}

	public UrlModel getUrlModel() {
		if (urlModel == null) {
			urlModel = new UrlModel();
		}
		return urlModel;
	}
 }

