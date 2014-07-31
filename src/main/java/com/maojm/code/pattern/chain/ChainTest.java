/**
 * 
 * yutian.com.cn Inc.
 * Copyright (c) 浙江宇天科技股份有限公司 2011-2012 All Rights Reserved.
 */

package com.maojm.code.pattern.chain;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author <a href="mailto:maojimin@yutian.com.cn">毛积敏</a>
 * 上午09:52:05 - 2012-11-15
 * @version 1.0
 */
public class ChainTest {
	public static void main(String[] args) {
		/*RequestHandle boss = new BossRequestHandle();
		RequestHandle hr = new HRRequestHandle(boss);
		RequestHandle pm = new PMRequestHandle(hr);
		
		System.out.println("=========case1===========");
		LeaveRequest request = new LeaveRequest(1);
		pm.handleRequest(request);
		System.out.println();
		
		System.out.println("=========case2===========");
		request = new LeaveRequest(2);
		pm.handleRequest(request);
		System.out.println();
		
		System.out.println("=========case3===========");
		request = new LeaveRequest(5);
		pm.handleRequest(request);
		System.out.println("===========");*/
		List<Long> ids = new ArrayList<Long>();
		ids.add(1L);
		ids.add(2L);
		System.out.println(ids.toString());
	}
}
