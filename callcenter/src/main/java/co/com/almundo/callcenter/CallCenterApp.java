/**
 * Joseph Rubio - Copyright (c) 2018
 * https://github.com/josephfmj/almundo
 * Date: 19/08/2018
 */
package co.com.almundo.callcenter;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import co.com.almundo.callcenter.components.Employee;
import co.com.almundo.callcenter.models.CallRequest;
import co.com.almundo.callcenter.services.CallQueue;
import co.com.almundo.callcenter.services.Dispatcher;
import co.com.almundo.callcenter.services.ReCallService;

/**
 * This is the callcenter class, to recive, manage and process calls
 * 
 * @author <a href="josephfmj@gmail.com">Joseph Rubio</a>
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
	 * The ReCall Service
	 */
	private ReCallService reCallService;
	
	/**
	 * The List of the Call Queues to pass to the Dispatcher
	 */
	private List<CallQueue> callAuxQueues;
	
	/**
	 * The List of employees
	 */
	private List<Employee> employees;
	
	/**
	 * The flag to run the validateForCallInQueue method
	 */
	private boolean stopValidateCall;
	
	
    
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
						Thread.sleep(2000);
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

    //Getters and Setters
    
	public Dispatcher getDispatcher() {
		return dispatcher;
	}

	public void setDispatcher(Dispatcher dispatcher) {
		this.dispatcher = dispatcher;
	}

	public List<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}

	public boolean isStopValidateCall() {
		return stopValidateCall;
	}

	public void setStopValidateCall(boolean stopValidateCall) {
		this.stopValidateCall = stopValidateCall;
	}

	public ReCallService getReCallService() {
		return reCallService;
	}

	public void setReCallService(ReCallService reCallService) {
		this.reCallService = reCallService;
	}

	public List<CallQueue> getCallAuxQueues() {
		return callAuxQueues;
	}

	public void setCallAuxQueues(List<CallQueue> callAuxQueues) {
		this.callAuxQueues = callAuxQueues;
	}
    
    
}
