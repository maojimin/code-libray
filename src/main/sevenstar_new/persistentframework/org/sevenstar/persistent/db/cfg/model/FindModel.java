package org.sevenstar.persistent.db.cfg.model;

import java.util.ArrayList;
import java.util.List;

import org.sevenstar.persistent.db.cfg.model.FindParamModel;

public class FindModel {
	private String name;
	private String className;

	private List paramModelList;

	public void addParamModel(FindParamModel findParamModel){
		getParamModelList().add(findParamModel);
	}

	public List getParamModelList() {
		if(paramModelList == null){
			paramModelList = new ArrayList();
		}
		return paramModelList;
	}



	public void setParamModelList(List paramModelList) {
		this.paramModelList = paramModelList;
	}



	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}
}
