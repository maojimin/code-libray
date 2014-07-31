package org.sevenstar.persistent.db.cfg.model;

import java.util.ArrayList;
import java.util.List;

import org.sevenstar.persistent.db.cfg.model.FindModel;

public class FindsModel {
	private List findList;

	public List getFindList() {
		if(findList == null){
			findList = new ArrayList();
		}
		return findList;
	}

	public void setFindList(List findList) {
		this.findList = findList;
	}

	public void addFindModel(FindModel findModel){
		getFindList().add(findModel);
	}
}
