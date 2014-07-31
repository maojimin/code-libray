package com.maojm.code.pattern.chain;


public interface RequestHandle {
	public void handleRequest(LeaveRequest request);
	public boolean pass();
	public static final int LEAVE_ONE_DAY = 1;
	public static final int LEAVE_THREE_DAY = 3;
	public static final int LEAVE_SEVEN_DAY = 7;
}
