package br.com.inacioalves.mc.product_catalog.model;


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
