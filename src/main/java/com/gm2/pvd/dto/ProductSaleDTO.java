package com.gm2.pvd.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductSaleDTO {
	
	@NotBlank(message = "Campo item da venda é obrigatório")
	private long productid;
	
	@NotBlank(message = "Campo quantidade é obrigatório")
	private int quantity;
}
