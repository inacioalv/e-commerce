package br.com.inacioalves.mc.orders_service.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {
	
	@Id
    @Column(name = "user_id")
	private Long id;
	private String first_name;
	private String last_name;
	private String email;
	private String nickname;
	@Embedded
	private Phone phone;
	
	@OneToMany (mappedBy = "user")
	@JsonIgnore
	private List<OrderCart> order;

	public User(String first_name, String last_name, String email, String nickname, Phone phone,
			List<OrderCart> order) {
		super();
		this.first_name = first_name;
		this.last_name = last_name;
		this.email = email;
		this.nickname = nickname;
		this.phone = phone;
		this.order = order;
	}
	
	public List<OrderCart> getOrder() {
		List<OrderCart> vendas = new ArrayList<OrderCart>();
		for(OrderCart o: order){
			o.setCart(null);
			vendas.add(o);}
		return vendas;
	}
	
	

}
