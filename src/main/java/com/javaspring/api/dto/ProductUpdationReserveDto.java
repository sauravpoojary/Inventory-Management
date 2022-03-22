package com.javaspring.api.dto;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductUpdationReserveDto {
	
	
	private String productSku;
	
	@NotNull
	private Integer reservedQty;

}