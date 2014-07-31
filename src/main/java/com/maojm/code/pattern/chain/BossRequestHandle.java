package com.maojm.code.pattern.chain;


public class BossRequestHandle implements RequestHandle {

	@Override
	public void handleRequest(LeaveRequest request) {
		if(request.getLeaveDay()<=LEAVE_SEVEN_DAY){
			pass();
		}else{
			System.out.println("总经理未批准");
		}
	}
	
	@Override
	public boolean pass() {
		System.out.println("总经理批准");
		return true;
	}
}
