/**
 * Joseph Rubio - Copyright (c) 2018
 * https://github.com/josephfmj/almundo
 * Date: 19/08/2018
 */
package co.com.almundo.callcenter.models;

import co.com.almundo.callcenter.models.constants.CallState;

/**
 * The Call POJO
 * 
 * @author <a href="josephfmj@gmail.com">Joseph Rubio</a>
 *
 */
public class CallRequest {
	
	/**
	 * The call message
	 */
	private String msg;
	
	/**
	 * The Call state
	 */
	private CallState state;
	
	/**
	 * The call attemps
	 */
	private int recallAttempts;
	
	/**
	 * The call attenden by employee info
	 */
	private String attendedBy;
	
	
	//Getter and Setters
	
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
