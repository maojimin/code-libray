package org.sevenstar.component.luence;

import java.util.ArrayList;
import java.util.List;


import org.sevenstar.web.action.java.DefaultAction;
import org.sevenstar.web.annotation.SSAction;

import com.lizh.hygl.system.context.ApplicationContext;
import com.lizh.hygl.system.exception.ApplicationException;
@SSAction(name="luence")
public class LuenceAction extends DefaultAction {
	private LuencePage page;
	private String querystring;

	public String query() {
		List typeList = new ArrayList();
		typeList.add(LuceneHelper.SUBJECT);
		typeList.add(LuceneHelper.CONTENT);
		typeList.add(LuceneHelper.EGOV_NO);
		typeList.add(LuceneHelper.EGOV_DEPT);

		page = LuceneHelper.searchPage(querystring, typeList, page, String
				.valueOf(ApplicationContext.get()
						.getId()), LuceneHelper.SECURITYVALUE);
		if (page.getDataList() == null || page.getDataList().size() == 0) {
			throw new ApplicationException("没有查询到数据");
		}
		return super.getNextUrl();
	}

	public LuencePage getPage() {
		return page;
	}

	public void setPage(LuencePage page) {
		this.page = page;
	}

	public String getQuerystring() {
		return querystring;
	}

	public void setQuerystring(String querystring) {
		this.querystring = querystring;
	}

}
