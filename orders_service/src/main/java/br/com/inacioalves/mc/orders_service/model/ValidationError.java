package br.com.inacioalves.mc.orders_service.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class ValidationError extends StandarError implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<fieldMessage> error = new ArrayList<fieldMessage>();
	 
	public ValidationError(){}
	
	public ValidationError(Integer status,String msg){
		super(status,msg);
	}

	public List<fieldMessage> getError() {
		return error;
	}

	public void addError(String field,String message) {
		error.add(new fieldMessage(field,message));
	}
}