package co.com.almundo.callcenter.components;

import co.com.almundo.callcenter.models.CallRequest;
import co.com.almundo.callcenter.models.constants.EmployeeRol;

public class Supervisor extends Employee{

	public Supervisor(EmployeeRol rol, String name, int priorityAnswer, boolean available){
		super.rol=rol;
		super.name=name;
		super.priorityAnswer=priorityAnswer;
		super.available=available;
	}
	
	@Override
	public CallRequest answerCall(CallRequest call) throws InterruptedException {
		
		LOGGER.info("This a Supervisor");
		return super.answerCall(call);
	}

}
