package br.com.inacioalves.mc.user.model;


import java.io.Serializable;

public class fieldMessage implements  Serializable {

	private static final long serialVersionUID = 1L;
	
	private String message;
	private String field;
	
	public fieldMessage() {}
	
	public fieldMessage(String message, String field) {
		super();
		this.message = message;
		this.field = field;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}
	
	
	
	

}