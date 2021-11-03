package br.com.inacioalves.mc.user.exeption;

import java.io.Serializable;

public class objectNotFoundException extends RuntimeException implements Serializable   {

	
	private static final long serialVersionUID = 1L;
	
	public objectNotFoundException(String msg) {
		super(msg);
	}
	
	public objectNotFoundException(String msg, Throwable causa) {
		super(msg,causa);
	}

}
