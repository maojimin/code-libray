package org.sevenstar.persistent.db.cfg.model;

import java.util.ArrayList;
import java.util.List;

 
public class RulesModel {
	private List ruleList;
	
	public void addRuleModel(RuleModel rm){
		this.getRuleList().add(rm);
	}

	public List getRuleList() {
		if (ruleList == null) {
			ruleList = new ArrayList();
		}
		return ruleList;
	}

	public void setRuleList(List ruleList) {
		this.ruleList = ruleList;
	}

}
