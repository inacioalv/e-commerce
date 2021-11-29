package br.com.inacioalves.mc.orders_service.model.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.inacioalves.mc.orders_service.enums.Status;
import br.com.inacioalves.mc.orders_service.model.Cart;
import br.com.inacioalves.mc.orders_service.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderCartDto {
	
	private Long id;
	@JsonFormat( pattern = "dd-MM-yyyy HH:mm")
	private LocalDateTime orderedDate;
	private Status status;
	private List<Cart> cart;
//	@JsonIgnore
	private User user;
	


}
