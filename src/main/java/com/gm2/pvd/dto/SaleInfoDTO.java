package com.gm2.pvd.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaleInfoDTO {
	private long id;
	private String user;
	private String date;
	private BigDecimal total;
	private List<ProductInfoDTO> products;
}
