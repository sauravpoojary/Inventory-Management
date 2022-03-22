package com.javaspring.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductUpdationDto {


//    private String id;
	
	
	private String productSku;
	private int inStockQty;
	private int reservedQty;
	private int demandQty;
}
