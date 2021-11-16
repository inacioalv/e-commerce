package br.com.inacioalves.mc.orders_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StandarError {
	
	private Integer status;
	private String msg;

}