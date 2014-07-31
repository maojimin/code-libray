package org.sevenstar.web.cfg.model;

import java.util.ArrayList;
import java.util.List;

public class RuleModel {
  private String pattern;
  private String resultType;
  private String type;
  private String parse;
  private String inteceptors;
  private String find;
  private String resultLocation;
  private String resource;
  private String encode;
  private List ruleExcludeRuleModelList;

  /*public String toString()
  {
     return "pattern[" + this.pattern + "];resultType[" + this.resultType + "];type[" + 
       this.type + "];parse[" + this.parse + "];inteceptors[" + this.inteceptors + 
      "];resultLocation[" + this.resultLocation + "]";
  }*/
	public String toString() {
		return "pattern[" + pattern + "];resultType[" + resultType + "];type["
				+ type + "];parse[" + parse + "];inteceptors[" + inteceptors
				+ "];resultLocation[" + resultLocation + "]";
	}
  public String getResource()
  {
     return this.resource;
  }

  public void setResource(String resource)
  {
     this.resource = resource;
  }

  public String getPattern()
  {
     return this.pattern;
  }

  public void setPattern(String pattern) {
     this.pattern = pattern;
  }

  public String getType() {
    return this.type;
  }

  public void setType(String type) {
     this.type = type;
  }

  public String getResultType() {
     return this.resultType;
  }

  public void setResultType(String resultType) {
     this.resultType = resultType;
  }

  public String getParse() {
     return this.parse;
  }

  public void setParse(String parse) {
     this.parse = parse;
  }

  public String getInteceptors() {
     return this.inteceptors;
  }

  public void setInteceptors(String inteceptors) {
     this.inteceptors = inteceptors;
  }

  public String getFind() {
    return this.find;
  }

  public void setFind(String find) {
     this.find = find;
  }

  public String getResultLocation() {
     return this.resultLocation;
  }

  public void setResultLocation(String resultLocation) {
     this.resultLocation = resultLocation;
  }

  public void addRuleExcludeRuleModel(RuleExcludeRuleModel ruleExcludeRuleModel) {
     getRuleExcludeRuleModelList().add(ruleExcludeRuleModel);
  }

  public List getRuleExcludeRuleModelList() {
     if (this.ruleExcludeRuleModelList == null) {
       this.ruleExcludeRuleModelList = new ArrayList();
    }
     return this.ruleExcludeRuleModelList;
  }

  public void setRuleExcludeRuleModelList(List ruleExcludeRuleModelList) {
     this.ruleExcludeRuleModelList = ruleExcludeRuleModelList;
  }

  public String getEncode()
  {
     return this.encode;
  }

  public void setEncode(String encode)
  {
     this.encode = encode;
  }
}
