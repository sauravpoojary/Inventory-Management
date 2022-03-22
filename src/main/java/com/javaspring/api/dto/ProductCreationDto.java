package com.javaspring.api.dto;


import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductCreationDto {
	

//	private String id;
	
	
	private String productSku;
	
	@NotNull(message="Instock should not be null")
	private Integer inStockQty;
	

}
