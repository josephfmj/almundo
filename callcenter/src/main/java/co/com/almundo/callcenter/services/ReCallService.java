package co.com.almundo.callcenter.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import co.com.almundo.callcenter.models.CallRequest;

public class ReCallService{
	
	private static final Logger LOGGER=LogManager.getLogger(ReCallService.class);
	
	public void reCall(Dispatcher dispatcher, CallRequest call) throws InterruptedException {
		
		LOGGER.info("Send call with msg: "+call.getMsg()+" to recall");
		new Thread(() -> {
			try {
				dispatcher.dispatchCall(call);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		})
		.start();
		
	}
}
