package com.javaspring.api.entity;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "products4")
public class Product {

	@Id
    private String id;
 	
	@Indexed(unique=true)
	private String productSku;
	
	private Integer inStockQty;
	
	private Integer reservedQty;
	private Integer demandQty;
	private Integer availableQty;
}
