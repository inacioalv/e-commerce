package br.com.inacioalves.mc.orders_service.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Status {
	
	CONFIRMED("confirmed"),
	PAYMENT_REQUIRED("payment_required"),
	PAYMENT_IN_PROCESS("payment_in_process"),
	PARTIALLY_PAID("partially_paid"),
	PAID("paid"),
	INVALID("invalid "), 
	CANCELLED("cancelled");
	
	private final String description;

}
