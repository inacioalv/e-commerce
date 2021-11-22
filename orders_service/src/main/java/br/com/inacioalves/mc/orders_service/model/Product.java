package br.com.inacioalves.mc.orders_service.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Product implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	private Long id;
	private String name;
	private double price;
	private String description;
	private String category;
	private int availabity;
	@ManyToOne
	@JoinColumn(name="cart", nullable=false)
	@JsonIgnore
	private Cart cart;
	
	
	public Product(Long id, String name, double price, String description, String category, int availabity) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.description = description;
		this.category = category;
		this.availabity = availabity;
	}
	
	

}
