package br.com.inacioalves.mc.orders_service.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Cart implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="cartid")
	private String id;
	private double subTotal;
	private String currency;
	@ManyToMany(mappedBy = "cart")
	private List<OrderCart> orderCart;
	@OneToMany(mappedBy="cart")
	private List<Product> product;

}
