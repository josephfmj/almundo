package co.com.almundo.callcenter.services;

import java.util.ArrayList;
import java.util.List;

import co.com.almundo.callcenter.models.CallRequest;

public class CallQueue {
	
	private List<CallRequest> calls;
	
	public CallQueue() {
		this.calls= new ArrayList<>();
	}
	
	public void addCall(CallRequest call) {
		this.calls.add(call);
	}
	
	public void removeCall(CallRequest call) {
		this.calls.remove(call);
	}
	
	public List<CallRequest> getCalls() {
		return calls;
	}

	public void setCalls(List<CallRequest> calls) {
		this.calls = calls;
	}
	
	

}
