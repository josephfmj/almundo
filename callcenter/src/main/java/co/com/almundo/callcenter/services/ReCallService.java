/**
 * Joseph Rubio - Copyright (c) 2018
 * https://github.com/josephfmj/almundo
 * Date: 19/08/2018
 */
package co.com.almundo.callcenter.services;

import java.util.Iterator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import co.com.almundo.callcenter.models.CallRequest;

/**
 * This is a ReCall service to returno to the dispacher flow, the calls that have been added to the queue 
 * 
 * @author <a href="josephfmj@gmail.com">Joseph Rubio</a>
 *
 */
public class ReCallService{
	
	/**
	 * The class Logger
	 */
	private static final Logger LOGGER=LogManager.getLogger(ReCallService.class);
	
	/**
	 * This method launches a thread (to asynchronous process) that call the dispatchCall from Dispatcher object
	 * 
	 * @param dispatcher The Dispatcher object
	 * @param call the call to process
	 * @throws InterruptedException throws if it is interrupted 
	 */
	public synchronized void reCall(Dispatcher dispatcher, CallRequest call) throws InterruptedException {
		
		LOGGER.info("Send call with msg: "+call.getMsg()+" to recall");
		new Thread(() -> {
			try {
				
				LOGGER.info("The call with msg: "+call.getMsg()+" to recalled");
				dispatcher.getCallQueue().removeCall(call);
				dispatcher.dispatchCall(call);
				
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
		})
		.start();
		
	}
	
	/**
	 * This method validates if there are items in the queue, and send to process calls
	 * @param dispatcher
	 */
	public synchronized void processRecall(Dispatcher dispatcher) throws InterruptedException {
		
		LOGGER.info("validate for recalls");
		for (Iterator<CallRequest> iterator = dispatcher.getCallQueue().getCalls().iterator(); iterator.hasNext();) {
			this.reCall(dispatcher, iterator.next());
		}
		
	}
}
