package com.gm2.pvd.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductDTO {
	
	private long id;
	
	@NotBlank(message = "Campo description obrigatório")
	private String description;
	
	@NotBlank(message = "Campo price obrigatório")
	private BigDecimal price;
	
	@NotBlank(message = "Campo quantity obrigatório")
	private int quantity;

}
