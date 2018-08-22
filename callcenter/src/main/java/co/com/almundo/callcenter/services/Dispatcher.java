/**
 * Joseph Rubio - Copyright (c) 2018
 * https://github.com/josephfmj/almundo
 * Date: 19/08/2018
 */
package co.com.almundo.callcenter.services;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import co.com.almundo.callcenter.components.Employee;
import co.com.almundo.callcenter.models.CallRequest;
import co.com.almundo.callcenter.models.constants.CallState;


/**
 * This is Dispatcher service to process incomming calls
 * 
 * @author <a href="josephfmj@gmail.com">Joseph Rubio</a>
 *
 */
public class Dispatcher {

	/**
	 * The Class Logger
	 */
	private static final Logger LOGGER = LogManager.getLogger(Dispatcher.class);

	/**
	 * The List of Employees
	 */
	private List<Employee> employees;
	
	/**
	 * The calls that were answered queue 
	 */
	private CallQueue answeredCalls;
	
	/**
	 * The not attended Calls Queue
	 */
	private CallQueue callQueue;

	/**
	 * The constructor
	 * @param employees
	 * @param callQueues
	 */
	public Dispatcher(List<Employee> employees, List<CallQueue> callQueues) {

		this.employees = employees;
		this.answeredCalls = callQueues.get(0);
		this.callQueue = callQueues.get(1);
	}

	/**
	 * Manager the calls, attend or send to queue
	 * @param call the call
	 * @throws InterruptedException
	 */
	public synchronized void dispatchCall(CallRequest call) throws InterruptedException {

		LOGGER.info("the call with msg: " + call.getMsg() + " are recived");
		
		Optional<Employee> emOptional = this.getAvailableEmployee();
		new Thread(() -> {
			if (emOptional.isPresent()) {

				emOptional.map(x -> this.callAnswer(x, call));
				
				LOGGER.info("the call with msg: " + call.getMsg() + " are attend");
				
			} else{
				
				call.setState(CallState.RECALL);
				call.setRecallAttempt();
				this.callQueue.addCall(call);
				
				LOGGER.info("the call with msg: " + call.getMsg() + " was added to the queue");
			}
		}).start();

	}

	//Service Methods
	
	/**
	 * Return the all aviable employess
	 * @return
	 */
	public synchronized List<Employee> getAviableEmployes() {

		return this.employees.stream().filter(x -> x.isAvailable()).collect(Collectors.toList());
	}

	/**
	 * Return the aviable employes, whit minior priority (select the call operator, supervisor or director, depends to availability)
	 * @return the Employees
	 */
	public Optional<Employee> getAvailableEmployee() {

		final Comparator<Employee> comparator = (x, y) -> Integer.compare(x.getPriorityAnswer(), y.getPriorityAnswer());

		return this.employees.stream().filter(x -> x.isAvailable()).min(comparator).map(this::setNotAvailableEmploye);
	}

	/**
	 * Auxiliar Method to disable availability of employee
	 * @param employee
	 * @return
	 */
	private Employee setNotAvailableEmploye(Employee employee) {
		employee.setAvailable(false);
		return employee;
	}

	/**
	 * Send the call for employee attend and process
	 * @param employee the Employee who attends
	 * @param call the Call
	 * @return the Employee
	 */
	private Employee callAnswer(Employee employee, CallRequest call) {
		this.employees.remove(employee);
		LOGGER.info("employes.size:  " + this.employees.size());

		try {
			employee.answerCall(call);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		if (call.getState().equals(CallState.RECALL)) {

			LOGGER.info("the call with msg: " + call.getMsg() + " are recalled");
			this.callQueue.removeCall(call);
		}
		this.answeredCalls.addCall(call);
		employee.setAvailable(true);
		this.employees.add(employee);
		LOGGER.info("employes.size:  " + this.employees.size() + "______");
		return employee;
	}

	//getters and Setters
	
	public List<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}

	public CallQueue getAnsweredCalls() {
		return answeredCalls;
	}

	public void setAnsweredCalls(CallQueue answeredCalls) {
		this.answeredCalls = answeredCalls;
	}

	public synchronized CallQueue getCallQueue() {
		return callQueue;
	}

	public void setCallQueue(CallQueue callQueue) {
		this.callQueue = callQueue;
	}

}
