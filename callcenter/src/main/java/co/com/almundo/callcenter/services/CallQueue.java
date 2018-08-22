/**
 * Joseph Rubio - Copyright (c) 2018
 * https://github.com/josephfmj/almundo
 * Date: 19/08/2018
 */
package co.com.almundo.callcenter.services;

import java.util.ArrayList;
import java.util.List;

import co.com.almundo.callcenter.models.CallRequest;

/**
 * The Call Queue Class
 * @author <a href="josephfmj@gmail.com">Joseph Rubio</a>
 *
 */
public class CallQueue {
	
	private List<CallRequest> calls;
	
	public CallQueue() {
		this.calls= new ArrayList<>();
	}
	
	/**
	 * Add Call to Queue
	 * @param call the Call
	 */
	public void addCall(CallRequest call) {
		this.calls.add(call);
	}
	
	/**
	 * Remove Call to Queue
	 * @param call the Call
	 */
	public void removeCall(CallRequest call) {
		this.calls.remove(call);
	}
	
	//Geter and Setter
	public List<CallRequest> getCalls() {
		return calls;
	}

	public void setCalls(List<CallRequest> calls) {
		this.calls = calls;
	}
	
	

}
