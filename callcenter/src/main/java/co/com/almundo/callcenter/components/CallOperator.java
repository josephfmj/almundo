/**
 * Joseph Rubio - Copyright (c) 2018
 * https://github.com/josephfmj/almundo
 * Date: 19/08/2018
 */
package co.com.almundo.callcenter.components;

import co.com.almundo.callcenter.models.CallRequest;
import co.com.almundo.callcenter.models.constants.EmployeeRol;

/**
 * This is the CallOperator component class
 * @author <a href="josephfmj@gmail.com">Joseph Rubio</a>
 *
 */
public class CallOperator extends Employee {
	
	
	public CallOperator(EmployeeRol rol, String name, int priorityAnswer,boolean available){
		super.rol=rol;
		super.name=name;
		super.priorityAnswer=priorityAnswer;
		super.available=available;
	}
	
	/* (non-Javadoc)
	 * @see co.com.almundo.callcenter.components.Employee#answerCall(co.com.almundo.callcenter.models.CallRequest)
	 */
	@Override
	public CallRequest answerCall(CallRequest call) throws InterruptedException {
		
		LOGGER.info("This a CallOperator");
		return super.answerCall(call);
	}
}
