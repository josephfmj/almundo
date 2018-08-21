package co.com.almundo.callcenter.models;

import co.com.almundo.callcenter.models.constants.CallState;

public class CallRequest {
	
	private String msg;
	private CallState state;
	private int recallAttempts;
	private String attendedBy;
	
	
	public String getMsg() {
		return msg;
	}
	
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	public CallState getState() {
		return state;
	}
	
	public void setState(CallState state) {
		this.state = state;
	}
	
	public int getRecallAttempts() {
		return recallAttempts;
	}
	
	public void setRecallAttempt() {
		this.recallAttempts++;
	}

	public String getAttendedBy() {
		return attendedBy;
	}

	public void setAttendedBy(String attendedBy) {
		this.attendedBy = attendedBy;
	}
	
	

}
