package br.com.inacioalves.mc.product_catalog.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long id;
	@NotBlank(message = "Nome é obrigatório")
	private String name;
	@NotNull
	private double price;
	@NotBlank(message = "Descrição é obrigatório")
	private String description;
	@NotBlank(message = "Categoria é obrigatório")
	private String category;
	@NotNull
	private int quantity;
	private boolean availabity;

}
