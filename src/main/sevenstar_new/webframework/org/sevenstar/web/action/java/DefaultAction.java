 package org.sevenstar.web.action.java;
 
 import java.io.IOException;
import java.util.HashMap;
 import java.util.Map;

import javax.servlet.ServletOutputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sevenstar.web.action.Action;
import org.sevenstar.web.cfg.SwebConfigure;
import org.sevenstar.web.cfg.model.RuleModel;
import org.sevenstar.web.context.WebContext;
 
 public class DefaultAction implements Action {
	 private Log LOG = LogFactory.getLog(DefaultAction.class);//旧
	 private String nextUrl;
	 private String msg;
	 private Map errorMap;
	 
	 public void addError(String key, String message) {
	     getErrorMap().put(key, message);
	 }
 
	/**
	 * 直接输出（//旧）
	 * @param result
	 */
	public void write(String result) {
		if (result == null || "".equals(result)) {
			return;
		}
		RuleModel ruleModel = SwebConfigure.getUrlModel(WebContext.getUrl());
		ServletOutputStream sos = null;
		try {
			sos = WebContext.getResponse().getOutputStream();
			if (ruleModel.getEncode() != null && !"".equals(ruleModel.getEncode())) {
				sos.write(result.getBytes(ruleModel.getEncode()));
			} else {
				sos.write(result.getBytes(SwebConfigure.getSwebModel().getEncode()));
			}
		} catch (IOException e) {
			LOG.error(e);
		} finally {
			if (sos != null) {
				try {
					sos.close();
				} catch (IOException e) {
					LOG.error(e);
				}
			}
		}
	}

   public Object execute() {
     return "success";
   }
 
   public String getNextUrl() {
     return this.nextUrl;
   }
 
   public void setNextUrl(String nextUrl) {
     this.nextUrl = nextUrl;
   }
 
   public String getMsg() {
     return this.msg;
   }
 
   public void setMsg(String msg) {
     this.msg = msg;
   }
 
   public Map getErrorMap() {
     if (this.errorMap == null) {
       this.errorMap = new HashMap();
     }
     return this.errorMap;
   }
 
   public void setErrorMap(Map errorMap) {
     this.errorMap = errorMap;
   }
 }

/* Location:           D:\Program Files\JD-GUI\test\SevenStarWeb15.jar
 * Qualified Name:     org.sevenstar.web.action.java.DefaultAction
 * JD-Core Version:    0.6.0
 */