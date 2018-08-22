package co.com.almundo.callcenter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import co.com.almundo.callcenter.components.CallOperator;
import co.com.almundo.callcenter.components.Director;
import co.com.almundo.callcenter.components.Employee;
import co.com.almundo.callcenter.components.Supervisor;
import co.com.almundo.callcenter.models.CallRequest;
import co.com.almundo.callcenter.models.constants.CallState;
import co.com.almundo.callcenter.models.constants.EmployeeRol;
import co.com.almundo.callcenter.services.CallQueue;
import co.com.almundo.callcenter.services.Dispatcher;
import co.com.almundo.callcenter.services.ReCallService;

/**
 * Unit test for simple CallCenterApp.
 */
public class CallCenterAppTest{
	
	/**
	 * The Test Logger Class
	 */
	private static final Logger LOGGER=LogManager.getLogger(CallCenterAppTest.class);
	
	private CallCenterApp callCenterApp;
	private Dispatcher dispatcher;
	private ReCallService reCallService;
	private List<CallQueue> callAuxQueues;
	private List<Employee> employees;
	
	@BeforeEach
	public void initTest() {
		
		//instantiate the CallCenterApp
		this.callCenterApp=new CallCenterApp();
		
		//create Employees		
		this.employees.add(new CallOperator(EmployeeRol.CALL_OPERATOR, "Operator 1", 1, true));
    	this.employees.add(new CallOperator(EmployeeRol.CALL_OPERATOR, "Operator 2", 1, true));
    	this.employees.add(new Supervisor(EmployeeRol.SUPERVISOR, "Supervisor 1", 2, true));
    	this.employees.add(new Director(EmployeeRol.DIRECTOR, "Director 1", 3, true));
    	
    	//Create Auxilar Queues for Calls
    	this.callAuxQueues=new ArrayList<>();
    	this.callAuxQueues.add(new CallQueue());
    	this.callAuxQueues.add(new CallQueue());
    	
	}
	
	/**
	 * This test case checks if one o more call is added to the call queue
	 * if the employees is not available
	 */
	@Test
	public void addCallsInQueueTest() {
		this.dispatcher= new Dispatcher(this.employees, this.callAuxQueues);
		this.callCenterApp.setDispatcher(this.dispatcher);
		this.callCenterApp.stopValidateForCallInQueue();
		
		int queueCallSize=0;
		
		int additional_calls=2;
		int max_call=this.employees.size()+additional_calls;
				;
    	for(int i=0;i<max_call;i++){
    		
    		CallRequest call= new CallRequest();
    		call.setMsg("message number: "+i);
    		call.setState(CallState.INCOMMING);
    		LOGGER.info("Run call thread n: "+i);
    		new Thread(() -> {
    			try {
					callCenterApp.inCommingCall(call);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			})
			.start();
    		
    	}
    	
    	queueCallSize=this.callCenterApp.getDispatcher().getCallQueue().getCalls().size();
    	assertEquals(additional_calls, queueCallSize);
	}
    
}
