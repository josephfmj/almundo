package co.com.almundo.callcenter.components;

import co.com.almundo.callcenter.models.CallRequest;
import co.com.almundo.callcenter.models.constants.EmployeeRol;

public class CallOperator extends Employee {
	
	
	public CallOperator(EmployeeRol rol, String name, int priorityAnswer,boolean available){
		super.rol=rol;
		super.name=name;
		super.priorityAnswer=priorityAnswer;
		super.available=available;
	}
	
	@Override
	public CallRequest answerCall(CallRequest call) throws InterruptedException {
		
		LOGGER.info("This a CallOperator");
		return super.answerCall(call);
	}
}
