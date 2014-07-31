package com.maojm.code.pattern.chain;


public class PMRequestHandle implements RequestHandle {
	
	private RequestHandle rh;
	
	public PMRequestHandle(RequestHandle rh){
		this.rh = rh;
	}
	
	
	@Override
	public void handleRequest(LeaveRequest request) {
		if(request.getLeaveDay()<=LEAVE_ONE_DAY){
			pass();
		}else{
			if(pass()){
				rh.handleRequest(request);
			}
		}
	}
	
	@Override
	public boolean pass() {
		System.out.println("部门领导批准");
		return true;
	}

}
