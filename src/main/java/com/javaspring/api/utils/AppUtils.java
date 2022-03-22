package com.javaspring.api.utils;



import com.fasterxml.jackson.databind.ObjectMapper;
import com.javaspring.api.dto.ProductCreationDto;
import com.javaspring.api.dto.ProductUpdationDto;
import com.javaspring.api.dto.ProductUpdationInstockDto;
import com.javaspring.api.dto.ProductUpdationReserveDto;
import com.javaspring.api.entity.Product;



public class AppUtils {


	
	
	public static Product dtoToEntity(ProductCreationDto productsCreationRequestDto) {
        ObjectMapper mapper = new ObjectMapper();
        Product product = mapper.convertValue(productsCreationRequestDto, Product.class);

        return product;
    }

    public static Product dtoToEntity(ProductUpdationDto productUpdateRequestDto) {
        ObjectMapper mapper = new ObjectMapper();
        Product product = mapper.convertValue(productUpdateRequestDto, Product.class);
        return product;
    }
    public static Product dtoToEntity(ProductUpdationInstockDto productUpdateRequestDto) {
        ObjectMapper mapper = new ObjectMapper();
        Product product = mapper.convertValue(productUpdateRequestDto, Product.class);
        return product;
    }
    
    public static Product dtoToEntity(ProductUpdationReserveDto productUpdateRequestDto) {
        ObjectMapper mapper = new ObjectMapper();
        Product product = mapper.convertValue(productUpdateRequestDto, Product.class);
        return product;
    }
}
