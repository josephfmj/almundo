package co.com.almundo.callcenter.components;

import co.com.almundo.callcenter.models.CallRequest;
import co.com.almundo.callcenter.models.constants.EmployeeRol;

public class Director extends Employee{

	public Director(EmployeeRol rol, String name, int priorityAnswer, boolean available){
		super.rol=rol;
		super.name=name;
		super.priorityAnswer=priorityAnswer;
		super.available=available;
	}
	
	@Override
	public CallRequest answerCall(CallRequest call) throws InterruptedException {
		
		LOGGER.info("This a Director");
		return super.answerCall(call);
	}

}
