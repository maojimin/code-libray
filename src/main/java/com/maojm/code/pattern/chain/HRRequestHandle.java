package com.maojm.code.pattern.chain;


public class HRRequestHandle implements RequestHandle {
	private RequestHandle rh;
	public HRRequestHandle(RequestHandle rh){
		this.rh = rh;
	}
	@Override
	public void handleRequest(LeaveRequest request) {
		if(request.getLeaveDay()<=LEAVE_THREE_DAY){
			pass();
		}else{
			if(pass()){
				rh.handleRequest(request);	
			}
		}
	}
	/* (non-Javadoc)
	 * @see chain.RequestHandle#pass()
	 */
	@Override
	public boolean pass() {
		System.out.println("人事批准");
		return true;
	}
}
