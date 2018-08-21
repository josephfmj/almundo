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

public class Dispatcher {
	
	private static final Logger LOGGER=LogManager.getLogger(Dispatcher.class);
	
	private final int MIN_RECALL_EMPLOYEES=1;
	private List<Employee> employees;
	private CallQueue answeredCalls;
	private CallQueue callQueue;
	private ReCallService reCallService;
	
	public Dispatcher(List<Employee> employees, List<CallQueue> callQueues,ReCallService reCallService ) {
		
		this.employees=employees;
		this.answeredCalls= callQueues.get(0);
		this.callQueue=callQueues.get(1);
		this.reCallService=reCallService;
	}
	
	public synchronized void dispatchCall(CallRequest call) throws InterruptedException{
		
		LOGGER.info("the call with msg: "+call.getMsg()+" are recived");
		Optional<Employee> emOptional=this.getAvailableEmployee();
		new Thread(() -> {
			if(emOptional.isPresent()) {
				
				emOptional
					.map(x->this.callAnswer(x, call));
				
				LOGGER.info("the call with msg: "+call.getMsg()+" are attend");
			}else {
				call.setState(CallState.RECALL);
				call.setRecallAttempt();
				this.callQueue.addCall(call);
				try {
					this.processRecall();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				LOGGER.info("the call with msg: "+call.getMsg()+" was added to the queue");
			}
		})
		.start();
		
		
		/*Optional<Employee> emOptional=this.getAvailableEmployee();
		if(emOptional.isPresent()) {
			
			emOptional
				.map(x->this.callAnswer(x, call));
			
			LOGGER.info("the call with msg: "+call.getMsg()+" are attend");
		}else {
			call.setState(CallState.RECALL);
			call.setRecallAttempt();
			this.callQueue.addCall(call);
			LOGGER.info("the call with msg: "+call.getMsg()+" was added to the queue");
		}
		*/
		
	}
	
	public List<Employee> getAviableEmployes(){
			
		return this.employees.stream()
				.filter(x->x.isAvailable())
				.collect(Collectors.toList());
	}
	
	public Optional<Employee> getAvailableEmployee() {
		
		final Comparator<Employee> comparator = (x, y) -> Integer.compare( x.getPriorityAnswer(), y.getPriorityAnswer());
		
		return this.employees.stream()
				.filter(x->x.isAvailable())
				.min(comparator)
				.map(this::setNotAvailableEmploye);
	}


	private Employee setNotAvailableEmploye(Employee employee) {
		employee.setAvailable(false);
		return employee;
	}
	
	private Employee callAnswer(Employee employee, CallRequest call) {
		this.employees.remove(employee);
		LOGGER.info("employes.size:  "+this.employees.size());
		
		try {
			employee.answerCall(call);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(call.getState().equals(CallState.RECALL)) {
			
			LOGGER.info("the call with msg: "+call.getMsg()+" are recalled");
			this.callQueue.removeCall(call);
		}
		this.answeredCalls.addCall(call);
		employee.setAvailable(true);
		this.employees.add(employee);
		LOGGER.info("employes.size:  "+this.employees.size()+"______");
		return employee;
	}
	
	private void processRecall() throws InterruptedException {
		
		LOGGER.info("validate for recalls, aviables employes: "+getAviableEmployes().size());
		if(getAviableEmployes().size()>=MIN_RECALL_EMPLOYEES && this.callQueue.getCalls().size()>0) {
			
			LOGGER.info("were found call in recall queue");
			
			int callIndex=0;
			for(Employee employee:getAviableEmployes()){
				this.reCallService.reCall(this,this.callQueue.getCalls().get(callIndex));
				callIndex++;
			}
		}
		
	}

}
