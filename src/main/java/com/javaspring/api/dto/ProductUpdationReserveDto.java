package com.javaspring.api.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductUpdationReserveDto {
	
	@NotEmpty(message="ProductSku should not be Empty")
	private String productSku;
	
	@NotNull(message="ReservedQty should not be null")
	private Integer reservedQty;

}
