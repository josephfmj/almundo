package co.com.almundo.callcenter;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
 * Hello world!
 *
 */
public class CallCenterApp {
	
	/**
	 * The Logger Class
	 */
	private static final Logger LOGGER=LogManager.getLogger(CallCenterApp.class);
	
	/**
	 * The Dispatcher service
	 */
	private Dispatcher dispatcher;
	
	/**
	 * 
	 */
	private ReCallService reCallService;
	private List<CallQueue> callAuxQueues;
	private List<Employee> employees;
	private boolean stopValidateCall;;
	
    /**
     * The static method to lauch app
     * @param args
     * @throws InterruptedException
     */
    public static void main( String[] args ) throws InterruptedException{
    	
    	LOGGER.info("Run the CallCenter App");
    	CallCenterApp callCenterApp= new CallCenterApp();
    	callCenterApp.initApp();
    	int max_call=11;
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
    	
    	callCenterApp.startValidateForCallInQueue();
    	callCenterApp.validateForCallInQueue();
    	Thread.sleep(60000);
    	callCenterApp.stopValidateForCallInQueue();
		
    }
    
    /**
     * Initialize the values a build the dependecies objects
     */
    public void initApp() {
    	
    	//Create Employees List
    	this.employees= new ArrayList<>();
    	
    	//Set Operators
    	this.employees.add(new CallOperator(EmployeeRol.CALL_OPERATOR, "Operator 1", 1, true));
    	this.employees.add(new CallOperator(EmployeeRol.CALL_OPERATOR, "Operator 2", 1, true));
    	this.employees.add(new CallOperator(EmployeeRol.CALL_OPERATOR, "Operator 3", 1, true));
    	this.employees.add(new CallOperator(EmployeeRol.CALL_OPERATOR, "Operator 4", 1, true));
    	this.employees.add(new CallOperator(EmployeeRol.CALL_OPERATOR, "Operator 5", 1, true));
    	
    	//Set Supervisors
    	this.employees.add(new Supervisor(EmployeeRol.SUPERVISOR, "Supervisor 1", 2, true));
    	this.employees.add(new Supervisor(EmployeeRol.SUPERVISOR, "Supervisor 2", 2, true));
    	this.employees.add(new Supervisor(EmployeeRol.SUPERVISOR, "Supervisor 3", 2, true));
    	
    	//Set Directors
    	this.employees.add(new Director(EmployeeRol.DIRECTOR, "Director 1", 3, true));
    	this.employees.add(new Director(EmployeeRol.DIRECTOR, "Director 2", 3, true));
    	
    	//Create Auxilar Queues for Calls
    	this.callAuxQueues=new ArrayList<>();
    	this.callAuxQueues.add(new CallQueue());
    	this.callAuxQueues.add(new CallQueue());
    	
    	//Create the recallService
    	this.reCallService= new ReCallService();
    	
    	//Create the Dispatcher
    	this.dispatcher= new Dispatcher(this.employees, this.callAuxQueues);
    }
    
    /**
     * This method recive the incomming calls and process the calls in queue
     * @param call The call object
     * @throws InterruptedException if is Interrupted
     */
    public void inCommingCall(CallRequest call) throws InterruptedException {
    	LOGGER.info("incomming call");
    	this.dispatcher.dispatchCall(call);
    }
    
    /**
     * This is a asyncronus method to process the  calls queue
     * @throws InterruptedException
     */
    public void validateForCallInQueue() throws InterruptedException {
    	
    	LOGGER.info("Validate Calls in Queue");
    	
    	new Thread(() -> {
			while(this.stopValidateCall) {
				
				if(dispatcher.getCallQueue().getCalls().size()>0) {
					
					LOGGER.info("were found call in recall queue");
					try {
						this.reCallService.processRecall(this.dispatcher);
						Thread.sleep(8000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
			}
		})
		.start();
    	
    }
    
    /**
     * Stop the validateForCallInQueue
     */
    public void stopValidateForCallInQueue() {
    	this.stopValidateCall=false;
    }
    
    /**
     * Start the validateForCallInQueue
     */
    public void startValidateForCallInQueue() {
    	this.stopValidateCall=true;
    }
    
}
