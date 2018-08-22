/**
 * Joseph Rubio - Copyright (c) 2018
 * https://github.com/josephfmj/almundo
 * Date: 19/08/2018
 */
package co.com.almundo.callcenter.components;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import co.com.almundo.callcenter.models.CallRequest;
import co.com.almundo.callcenter.models.constants.CallState;
import co.com.almundo.callcenter.models.constants.EmployeeRol;
import co.com.almundo.callcenter.services.Dispatcher;


/**
 * The Employe abstrac class, to define common features in the employees
 * @author <a href="josephfmj@gmail.com">Joseph Rubio</a>
 *
 */
public abstract class Employee {
	
	/**
	 * The Logger
	 */
	protected static final Logger LOGGER=LogManager.getLogger(Dispatcher.class);
	
	/**
	 * Min Call duration
	 */
	private final int MIN_CALL_DURATION=5000;
	
	/**
	 * Max Call duration
	 */
	private final int MAX_CALL_DURATION=10000;
	
	/**
	 * The Employee Rol
	 */
	protected EmployeeRol rol;
	
	/**
	 * The Employee name
	 */
	protected String name;
	
	/**
	 * The availability of the Employee
	 */
	protected boolean available;
	
	/**
	 * The priority of the Employee
	 */
	protected int priorityAnswer;
	
	
	/**
	 * Process the call, change the state (completed call)
	 * @param call the Call to process
	 * @return the Call procesed
	 * @throws InterruptedException
	 */
	public CallRequest answerCall(CallRequest call) throws InterruptedException {
		int callDuration = (int) (Math.random() * MAX_CALL_DURATION) + MIN_CALL_DURATION;
		Thread.sleep(callDuration);
		call.setState(CallState.COMPLETE);
		call.setAttendedBy(this.rol+": "+this.name);
		call.setRecallAttempt();
		return call;
	}

	//Getters and Setters
	
	public EmployeeRol getRol() {
		return rol;
	}

	public void setRol(EmployeeRol rol) {
		this.rol = rol;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public int getPriorityAnswer() {
		return priorityAnswer;
	}

	public void setPriorityAnswer(int priorityAnswer) {
		this.priorityAnswer = priorityAnswer;
	}
	
	
	
}
