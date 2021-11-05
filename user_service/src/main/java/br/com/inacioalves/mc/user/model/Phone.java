package br.com.inacioalves.mc.user.model;

import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class Phone {
	
	
	private String area_code;
	private int number;
	private boolean verified;

}
