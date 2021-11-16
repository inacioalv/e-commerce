package br.com.inacioalves.mc.orders_service.model;


import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Cart implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "cartId")
	private String id;
	private double subTotal;
	@OneToMany(mappedBy="cart")
	private List<Product> products;
	
	

}
